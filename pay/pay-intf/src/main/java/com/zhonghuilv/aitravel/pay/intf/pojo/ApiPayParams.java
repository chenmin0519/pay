package com.zhonghuilv.aitravel.pay.intf.pojo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author chenmin
 * @title: ApiPayParams
 * @projectName shop-springboot
 * @description: TODO
 * @date 2019-05-2417:01
 * @Version: 1.0
 * @JDK: 10
 */
public class ApiPayParams {
    @ApiModelProperty("订单来源(H5_PAY;APP_PAY，MINI_APP)")
    @NotNull
    private  String orderFrom;

    @ApiModelProperty("appId")
    @NotNull
    private String appId;
    //    @ApiModelProperty("同步回调页面（H5支付完成支付时的页面）")
    //    private String returnUrl;
    @ApiModelProperty("订单id")
    @NotNull
    private Long orderId;

    @ApiModelProperty("支付类型 微信.WEIXIN，支付宝.ALIPAY")
    @NotNull
    private String payType;

    public String getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(String orderFrom) {
        this.orderFrom = orderFrom;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }
}
