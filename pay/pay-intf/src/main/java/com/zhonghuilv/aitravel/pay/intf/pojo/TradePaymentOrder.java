
package com.zhonghuilv.aitravel.pay.intf.pojo;

import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.MainPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@ApiModel("支付订单表")
@Table(name = "trade_payment_order")
@Data
public class TradePaymentOrder extends MainPO {

    @Id
    @OrderBy("desc")
    public Long id;

    @ApiModelProperty(value = "版本号", required = true)
    private Integer version;

    @ApiModelProperty(value = "状态(参考枚举:orderstatusenum)")
    private String status;

    @ApiModelProperty("流水号")
    private String trxNo;

    @ApiModelProperty(value = "银行流水号")
    private String bankTrxNo;

    @ApiModelProperty("商品ID")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty("业务码")
    private String bizCode;

    @ApiModelProperty(value = "商户订单号", required = true)
    private String merchantOrderNo;

    @ApiModelProperty(value = "订单金额 单位分")
    private Integer orderAmount;

    @ApiModelProperty(value = "订单来源")
    private String orderFrom;

    @ApiModelProperty(value = "下单时间")
    private java.time.LocalDateTime orderTime;

    @ApiModelProperty(value = "下单ip(客户端ip,在网关页面获取)")
    private String orderIp;

    @ApiModelProperty(value = "下单APP")
    private String appId;

    @ApiModelProperty(value = "从哪个页面链接过来的")
    private String orderRefererUrl;

    @ApiModelProperty(value = "订单撤销原因")
    private String cancelReason;

    @ApiModelProperty(value = "订单有效期(单位分钟)")
    private Integer orderPeriod;

    @ApiModelProperty(value = "到期时间")
    private java.time.LocalDateTime expireTime;

    @ApiModelProperty(value = "支付方式(WEIXIN,ALIPAY)")
    private String payWayCode;

    @ApiModelProperty(value = "成功退款总金额")
    private Integer successRefundAmount;

}

