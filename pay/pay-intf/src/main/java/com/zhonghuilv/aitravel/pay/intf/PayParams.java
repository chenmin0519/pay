package com.zhonghuilv.aitravel.pay.intf;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@ApiModel("支付参数")
@Data
@NoArgsConstructor
public class PayParams {

    @ApiModelProperty(value = "(微信或支付宝的应用id)", required = true)
    @NotNull(message = "appid不能为空")
    private String appid;

    @ApiModelProperty(value = "业务code（支付成功后发送消息的队列后缀）", required = true)
    private String bizCode;

    @ApiModelProperty(value = "商品名称", required = true)
    @NotNull(message = "商品名称不能为空")
    private String productName;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty(value = "下单用户", required = true)
    @NotNull(message = "下单用户不能为空")
    private Long userId;

    @ApiModelProperty(value = "订单来源(H5_PAY,APP_PAY，MINI_APP)", required = true)
    @NotNull(message = "订单来源不能为空")
    private String orderFrom;

    @ApiModelProperty(value = "商户订单号", required = true)
    @NotNull(message = "商户订单号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "下单时间", required = true)
    @NotNull(message = "下单时间不能为空")
    private LocalDateTime orderTime;

    @ApiModelProperty(value = "订单金额(分)", required = true)
    @NotNull(message = "订单金额不能为空")
    private int orderPrice;

    @ApiModelProperty(value = " 支付方式编码(参考PayWayEnum)", required = true)
    @NotNull(message = "支付方式不能为空")
    private String payWayCode;

    @ApiModelProperty(value = "小程序支付必传", required = true)
    private String opendId;

    @ApiModelProperty(value = "下单IP", required = true)
    @NotNull(message = "下单IP不能为空")
    private String orderIp;

    @ApiModelProperty(value = "订单有效期(分钟)", required = true)
    @NotNull(message = "订单有效期不能为空")
    private Integer orderPeriod;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("同步回调页面（H5支付完成支付时的页面）")
    private String returnUrl;

    @Builder
    private PayParams(@NotNull(message = "appid不能为空") String appid,
                      String bizCode, @NotNull(message = "商品名称不能为空")
                              String productName,
                      Long productId,
                      @NotNull(message = "下单用户不能为空") Long userId,
                      @NotNull(message =
                              "订单来源不能为空") String orderFrom,
                      @NotNull(message = "商户订单号不能为空") String orderNo,
                      @NotNull(message = "下单时间不能为空") LocalDateTime orderTime,
                      @NotNull(message = "订单金额不能为空") int orderPrice,
                      @NotNull(message = "支付方式不能为空") String payWayCode,
                      String opendId,
                      @NotNull(message = "下单IP不能为空") String orderIp,
                      @NotNull(message = "订单有效期不能为空") Integer orderPeriod,
                      String remark, String returnUrl) {
        this.appid = appid;
        this.bizCode = bizCode;
        this.productName = productName;
        this.productId = productId;
        this.userId = userId;
        this.orderFrom = orderFrom;
        this.orderNo = orderNo;
        this.orderTime = orderTime;
        this.orderPrice = orderPrice;
        this.payWayCode = payWayCode;
        this.opendId = opendId;
        this.orderIp = orderIp;
        this.orderPeriod = orderPeriod;
        this.remark = remark;
        this.returnUrl = returnUrl;
    }

}