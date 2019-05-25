package com.zhonghuilv.aitravel.pay.service.supports;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.zhonghuilv.aitravel.pay.intf.util.WechatPayParams;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
    * @Description: 申请退款参数
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Data
@Builder
@JacksonXmlRootElement(localName = "xml")
public class RefundWechatParams extends WechatPayParams {

    @NotNull
    @JacksonXmlCData
    @JacksonXmlProperty(localName = "transaction_id")
    @ApiModelProperty("微信订单号")
    private String transactionId;

    @NotNull
    @JacksonXmlCData
    @JacksonXmlProperty(localName = "total_fee")
    @ApiModelProperty("订单金额")
    private Integer totalFee;

    @NotNull
    @JacksonXmlCData
    @JacksonXmlProperty(localName = "refund_fee")
    @ApiModelProperty("退款金额")
    private Integer refundFee;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "refund_fee_type")
    @ApiModelProperty("货币种类")
    private String refundFeeType = "CNY";

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "refund_desc")
    @ApiModelProperty("退款原因")
    private String refundDesc;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "notify_url")
    @ApiModelProperty("退款结果通知url")
    private String notifyUrl;

}
