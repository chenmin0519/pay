package com.zhonghuilv.aitravel.pay.service.controller;

import com.zhonghuilv.aitravel.common.excption.CustomRuntimeException;
import com.zhonghuilv.aitravel.common.excption.ParameterNotValidException;
import com.zhonghuilv.aitravel.pay.intf.PayCallbackDTO;
import com.zhonghuilv.aitravel.pay.intf.PayParams;
import com.zhonghuilv.aitravel.pay.intf.clients.PayCoreClient;
import com.zhonghuilv.aitravel.pay.intf.enums.TradeStatusEnum;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;
import com.zhonghuilv.aitravel.pay.service.mapper.TradePaymentOrderMapper;
import com.zhonghuilv.aitravel.pay.service.mapper.TradePaymentRecordMapper;
import com.zhonghuilv.aitravel.pay.service.service.BuildService;
import com.zhonghuilv.aitravel.pay.service.service.TradePaymentManagerService;
import com.zhonghuilv.aitravel.pay.service.supports.PayPolicy;
import com.zhonghuilv.aitravel.pay.service.util.SealUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@RestController
@RequestMapping("/pay_core")
@Api(value = "PayCoreController", tags = "支付核心模块")
@Slf4j
public class PayCoreController implements PayCoreClient {

    @Autowired
    BuildService buildService;

    Logger logger = LoggerFactory.getLogger(PayCoreController.class);

    /**
     * 支付订单mapper
     */
    @Autowired
    TradePaymentOrderMapper tradePaymentOrderMapper;
    /**
     * 支付记录mapper
     */
    @Autowired
    TradePaymentRecordMapper tradePaymentRecordMapper;

    @Autowired
    TradePaymentManagerService tradePaymentManagerService;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    @GetMapping("/load_order_by_order_no")
    @ApiOperation("通过订单号查找订单（处理成功h后status为SUCCESS）")
    public TradePaymentOrder loadOrderByOrderNo(@RequestParam("orderNo") @ApiParam("订单号") String orderNo) {
        return loadByMerchantNoAndOrder(orderNo);
    }

    private TradePaymentOrder loadByMerchantNoAndOrder(String orderNo) {

        TradePaymentOrder tradePaymentOrder = new TradePaymentOrder();
        tradePaymentOrder.setMerchantOrderNo(orderNo);
        return tradePaymentOrderMapper.selectOne(tradePaymentOrder);
    }


    @Override
    @PostMapping("/_init_order_params")
    @Transactional(rollbackFor = Exception.class)
    @ApiOperation("初始化给APP调用的参数")
    public String initOrderParams(@RequestBody @Validated PayParams payParams) {

        logger.info("init_order_params---》{}", payParams);
        payParams.setOrderTime(LocalDateTime.now());
        TradePaymentOrder order = this.loadByMerchantNoAndOrder(payParams.getOrderNo());

        //如果之前没有支付订单则生成支付订单
        if (order == null) {
            order = SealUtil.sealOrder(payParams);
            tradePaymentOrderMapper.insertUseGeneratedKeys(order);
        } else {
            order = SealUtil.sealUpdateOrder(payParams, order);
            tradePaymentOrderMapper.updateByPrimaryKey(order);
        }

        if (TradeStatusEnum.SUCCESS.name().equals(order.getStatus())) {
            throw new ParameterNotValidException("订单已支付");
        }

        //生成支付记录
        TradePaymentRecord record = SealUtil.sealRecord(order, payParams, buildService.buildTrxNo());
        tradePaymentRecordMapper.insertUseGeneratedKeys(record);

        //获取对应处理bean
        PayPolicy payPolicy = this.getPayPolicy(payParams.getPayWayCode());
        String payInfo = payPolicy.getPayInfo(payParams, record.getTrxNo());
        ShowController.cache = payInfo;
        return payInfo;
    }

    private PayPolicy getPayPolicy(String payWayCode) {

        PayPolicy payPolicy = applicationContext.getBean(payWayCode + PayPolicy.BEAN_SUFFIX, PayPolicy.class);
        if (payPolicy == null) {
            throw new CustomRuntimeException("没有对应的处理策略：" + payWayCode);
        }
        return payPolicy;
    }

    @Override
    @ApiOperation("刷新订单状态")
    @GetMapping("/ref_order_status")
    public TradePaymentOrder refOrderStatus(String orderNo) {

        TradePaymentOrder order = this.loadByMerchantNoAndOrder(orderNo);
        if (null == order) {
            logger.warn("刷新订单失败，订单不存在:{}",  orderNo);
            return null;
        }

        if (TradeStatusEnum.SUCCESS.name().equals(order.getStatus())) {
            return order;
        }

        PayPolicy payPolicy = getPayPolicy(order.getPayWayCode());
        TradePaymentRecord queryModel = new TradePaymentRecord();

        queryModel.setMerchantOrderNo(orderNo);
        queryModel.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());
        List<TradePaymentRecord> records = tradePaymentRecordMapper.select(queryModel);
        if (!CollectionUtils.isEmpty(records)) {
            Iterator<TradePaymentRecord> iterator = records.iterator();
            PayCallbackDTO dto = null;
            while (iterator.hasNext()) {
                TradePaymentRecord record = iterator.next();
                dto = payPolicy.orderQuery(order, record);
                if (TradeStatusEnum.SUCCESS.name().equals(dto.getStatus())) {
                    return tradePaymentManagerService.tradeCallback(dto);
                }
            }
        }
        return null;
    }

    @Override
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public String payCallback(@RequestBody ParseDTO parseDTO) {

        PayPolicy payPolicy = getPayPolicy(parseDTO.getPayWay());

        return payPolicy.callback(parseDTO);
    }

    @Override
    @RequestMapping(value = "/_search_orderNos", method = RequestMethod.POST)
    public List<TradePaymentOrder> loadByOrderNos(@RequestBody List<String> orderNos) {
        if(CollectionUtils.isEmpty(orderNos)) {
            return new ArrayList<>();
        }
        Example example = new Example(TradePaymentOrder.class);
        example.createCriteria().andIn("merchantOrderNo", orderNos);
        return tradePaymentOrderMapper.selectByExample(example);
    }

    /**
     * 退款URL 回调
     *
     * @return
     */
    public String refundCallback() {

        return null;
    }

    @Override
    @RequestMapping(value = "/_test", method = RequestMethod.GET)
    public String test(@RequestParam("str") String str) {
        return str+" woshi service";
    }
}
