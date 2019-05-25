package com.zhonghuilv.aitravel.pay.service.service.impl;

import com.zhonghuilv.aitravel.pay.intf.PayCallbackDTO;
import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.enums.TradeStatusEnum;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.PayMessage;
import com.zhonghuilv.aitravel.pay.service.exception.PayException;
import com.zhonghuilv.aitravel.pay.service.mapper.TradePaymentOrderMapper;
import com.zhonghuilv.aitravel.pay.service.mapper.TradePaymentRecordMapper;
import com.zhonghuilv.aitravel.pay.service.service.TradePaymentManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;


/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Service
public class TradePaymentManagerServiceImpl implements TradePaymentManagerService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BinderAwareChannelResolver resolver;

    @Autowired
    TradePaymentRecordMapper tradePaymentRecordMapper;

    @Autowired
    TradePaymentOrderMapper tradePaymentOrderMapper;

    /**
     * 更新流水
     *
     * @param record        流水号
     * @param bankTrxNo     银行（第三方交易流水）
     * @param platStatus    平台状态
     * @param payment       支付时间
     * @param bankReturnMsg 银行（第三方）返回的源信息
     * @return
     */
    private TradePaymentRecord updateRecord(TradePaymentRecord record, String bankTrxNo, String platStatus,
                                            LocalDateTime payment,
                                            String bankReturnMsg) {


        if (record == null) {
            throw PayException.DB_SELECTONE_IS_NULL;
        }
        // 平台成本
        //平台收入
        LocalDateTime now = LocalDateTime.now();
        record.setVersion(record.getVersion() + 1);//修改操作版本
        record.setPaySuccessTime(payment);
        record.setBankReturnMsg(bankReturnMsg);

        record.setStatus(platStatus);
        //设置银行流水号(支付宝流水)
        record.setBankTrxNo(bankTrxNo);
        record.setBankReturnMsg(bankReturnMsg);
        record.setCompleteTime(now);
        if (tradePaymentRecordMapper.updateByPrimaryKey(record) == 0) {
            throw PayException.DB_UPDATE_RESULT_0;
        }
        return record;
    }

    /**
     * 交易状态变更
     *
     * @param payCallback
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public TradePaymentOrder tradeCallback(PayCallbackDTO payCallback) {

        logger.info("payCallback--->{}", payCallback);
        if (Objects.isNull(payCallback) || Objects.isNull(payCallback.getStatus())) {
            logger.info("回调status为空");
            return null;
        }
        if (TradeStatusEnum.WAITING_PAYMENT.name().equals(payCallback.getStatus())) {
            logger.info("用户未支付！");
            return null;
        }
        //查询流水先

        TradePaymentRecord tradePaymentRecord = new TradePaymentRecord();
        tradePaymentRecord.setTrxNo(payCallback.getPlatTrxNo());
        TradePaymentRecord record = tradePaymentRecordMapper.selectOne(tradePaymentRecord);

        if (!TradeStatusEnum.WAITING_PAYMENT.name().equals(record.getStatus())) {
            logger.info("订单流水已处理--->{}", record);
            return null;
        }
        updateRecord(record, payCallback.getBankTrxNo(),
                payCallback.getStatus(), payCallback.getPayTime()/* ,payCallback.getReceiptAmount(),
                payCallback.getBuyerPayAmount()*/, payCallback.getBankReturnMsg());
        //error
        TradePaymentOrder orderQuery = new TradePaymentOrder();
        orderQuery.setMerchantOrderNo(record.getMerchantOrderNo());
        TradePaymentOrder order = tradePaymentOrderMapper.selectOne(orderQuery);

        //更新订单支付方式
        order.setPayWayCode(record.getPayWayCode());
        return completeSuccessOrder(order, payCallback.getStatus(), payCallback, payCallback
                .getReceiptAmount(), payCallback.getBankTrxNo());
    }

    /**
     * 改变订单状态
     *
     * @param order         订单对象
     * @param status        订单状态
     * @param payCallback   支付回调对象
     * @param receiptAmount 实收金额
     * @param bankOrderNo   第三方交易号
     */
    public TradePaymentOrder completeSuccessOrder(TradePaymentOrder order, String status,
                                                  PayCallbackDTO payCallback,
                                                  Integer receiptAmount, String bankOrderNo) {

        if (order == null)
            throw PayException.TRADE_PARAM_ERROR;

        if (!TradeStatusEnum.WAITING_PAYMENT.name().equals(order.getStatus())) {
            logger.warn("订单已处理 completeSuccessOrder--》{}", payCallback);
            return null;
        }


        order.setStatus(status);
        order.setBankTrxNo(bankOrderNo);
        order.setTrxNo(payCallback.getPlatTrxNo());
        logger.info("update order-->{}", order);
        tradePaymentOrderMapper.updateByPrimaryKeySelective(order);


        //处理之后发送mq信息
        PayMessage payMessage = new PayMessage(order.getMerchantOrderNo(), payCallback.getPayWay(), status);
        logger.info("send msg 2 {}:{}", PayConstant.MQ_PREFIX + order.getBizCode(), payMessage);

        resolver.resolveDestination(PayConstant.MQ_PREFIX + order.getBizCode())
                .send(MessageBuilder.withPayload(payMessage).build());
        return order;
    }
}
