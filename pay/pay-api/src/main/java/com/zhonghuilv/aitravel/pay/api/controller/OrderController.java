package com.zhonghuilv.aitravel.pay.api.controller;

import com.alibaba.fastjson.JSON;
import com.zhonghuilv.aitravel.pay.intf.PayParams;
import com.zhonghuilv.aitravel.pay.intf.clients.OrderClient;
import com.zhonghuilv.aitravel.pay.intf.clients.PayCoreClient;
import com.zhonghuilv.aitravel.pay.intf.common.ApiResult;
import com.zhonghuilv.aitravel.pay.intf.pojo.ApiPayParams;
import com.zhonghuilv.aitravel.pay.intf.pojo.YOrder;
import com.zhonghuilv.aitravel.pay.intf.util.IpUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chenmin
 * @title: OrderController
 * @projectName pay
 * @description: TODO
 * @date 2019-05-2418:43
 * @Version: 1.0
 * @JDK: 10
 */
@RestController
@RequestMapping("/pay/order")
@Api("下单")
public class OrderController {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private PayCoreClient payCoreClient;

    /**
     * 订单过期时间，单位分钟
     */
    private Integer OUT_TIME = 30;

    @RequestMapping(value = "/initOrderParams", method = RequestMethod.POST)
    public ApiResult<String> initOrderParams(@RequestBody @Validated ApiPayParams apiPayParams, HttpServletRequest request){
        String result = "";
        YOrder yOrder = orderClient.queryOrderById(apiPayParams.getOrderId());
        if (yOrder == null) {
            return ApiResult.success("当前没有支付订单");
        }
        PayParams payParams = new PayParams();
        payParams.setAppid(apiPayParams.getAppId());
//        payParams.setReturnUrl(apiPayParams.getReturnUrl());
        payParams.setOrderPrice(yOrder.getPayment());
        payParams.setOrderPeriod(OUT_TIME);
        payParams.setOrderNo(yOrder.getOrderNum());
        payParams.setPayWayCode(apiPayParams.getPayType());
        payParams.setOrderFrom(apiPayParams.getOrderFrom());
        payParams.setOrderTime(LocalDateTime.now());
        payParams.setProductId(yOrder.getId());
        payParams.setUserId(yOrder.getUserId());
        payParams.setProductName(yOrder.getOrderName());
        payParams.setOrderIp(IpUtils.getIpAddr(request));
        result = payCoreClient.initOrderParams(payParams);
        Map<String, String> f = new HashMap<>();
        f.put("orderNum", yOrder.getOrderNum());
        f.put("payParam", result);
        return ApiResult.success(JSON.toJSONString(f));
    }
}
