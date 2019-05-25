package com.zhonghuilv.aitravel.pay.intf.clients;

import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.PayParams;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
    * @Description: 支付核心
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@FeignClient(value = PayConstant.SERVICE_NAME)
@RequestMapping("/pay_core")
public interface PayCoreClient {

    @RequestMapping(value = "/load_order_by_order_no", method = RequestMethod.GET)
    @ApiOperation("通过订单号查找订单（处理成功h后status为SUCCESS）")
    TradePaymentOrder loadOrderByOrderNo(@RequestParam("orderNo") @ApiParam("订单号") String orderNo);

    @RequestMapping(value = "/_init_order_params", method = RequestMethod.POST)
    @ApiOperation("初始化支付订单（APP调用的参数）")
    String initOrderParams(@RequestBody @Validated PayParams payParams);

    @ApiOperation("刷新订单状态")
    @RequestMapping(value = "/ref_order_status", method = RequestMethod.GET)
    TradePaymentOrder refOrderStatus(@RequestParam("orderNo") String orderNo);

    /**
     * 支付成功的回调
     * @param parseDTO
     * @return
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    String payCallback(@RequestBody ParseDTO parseDTO);


    @ApiOperation("通过orderNo in查询")
    @RequestMapping(value = "/_search_orderNos", method = RequestMethod.POST)
    List<TradePaymentOrder> loadByOrderNos(@RequestBody List<String> orderNos);

    @ApiOperation("测试")
    @RequestMapping(value = "/_test", method = RequestMethod.GET)
    String test(@RequestParam("str") String str);
}
