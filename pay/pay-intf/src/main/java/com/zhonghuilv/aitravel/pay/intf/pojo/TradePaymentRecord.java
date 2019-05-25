
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
@ApiModel("支付记录表")
@Table(name = "trade_payment_record")
@Data
public class TradePaymentRecord extends MainPO {

    @Id
	@OrderBy("desc")
    public Long id;

    @ApiModelProperty(value = "版本号", required = true)
    private Integer version;

    @ApiModelProperty(value = "状态(参考枚举:paymentrecordstatusenum)")
    private String status;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商户订单号", required = true)
    private String merchantOrderNo;

    @ApiModelProperty(value = "支付流水号", required = true)
    private String trxNo;

    @ApiModelProperty(value = "银行订单号")
    private String bankOrderNo;

    @ApiModelProperty(value = "银行流水号")
    private String bankTrxNo;

    @ApiModelProperty(value = "付款人编号")
    private Long payerUserId;

    @ApiModelProperty(value = "下单ip(客户端ip,从网关中获取)")
    private String orderIp;

    @ApiModelProperty(value = "从哪个页面链接过来的(可用于防诈骗)")
    private String orderRefererUrl;

    @ApiModelProperty(value = "订单金额")
    private Integer orderAmount;

    @ApiModelProperty(value = "支付方式编号")
    private String payWayCode;

    @ApiModelProperty(value = "支付成功时间")
    private java.time.LocalDateTime paySuccessTime;

    @ApiModelProperty(value = "完成时间")
    private java.time.LocalDateTime completeTime;

    @ApiModelProperty(value = "是否退款(100:是,101:否,默认值为:101)")
    private String isRefund;

    @ApiModelProperty(value = "退款次数(默认值为:0)")
    private Integer refundTimes;

    @ApiModelProperty(value = "成功退款总金额")
    private Integer successRefundAmount;

    @ApiModelProperty(value = "订单来源()")
    private String orderFrom;

    @ApiModelProperty(value = "第三方返回信息")
    private String bankReturnMsg;

}

