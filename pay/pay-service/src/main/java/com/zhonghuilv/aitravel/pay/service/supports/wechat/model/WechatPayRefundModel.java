package com.zhonghuilv.aitravel.pay.service.supports.wechat.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 微信退款接口 参数
 */
@Data
@Builder
public class WechatPayRefundModel extends WechatPayModel {
    // 非必填 设备号  终端设备号(门店号或收银设备ID)
    @JacksonXmlProperty(localName = "device_info")
	@JacksonXmlCData
    private String deviceInfo;

    // 微信订单号，优先使用
    @JacksonXmlProperty(localName = "transaction_id")
	@JacksonXmlCData
    private String transactionId;

    // 商户订单号， 商户内部系统的订单号，  32个字符内，可包含字母。 微信支付要求商户订单号保持唯一性
    @JacksonXmlProperty(localName = "out_trade_no")
	@JacksonXmlCData
    private String outTradeNo;

    // 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
    @JacksonXmlProperty(localName = "out_refund_no")
	@JacksonXmlCData
    private String outRefundNo;

    // 订单金额，精确到分
    @JacksonXmlProperty(localName = "total_fee")
	@JacksonXmlCData
    private int totalFee;

    // 退款金额 ，精确到分
    @JacksonXmlProperty(localName = "refund_fee")
	@JacksonXmlCData
    private int refundFee;

    // 货币种类	默认人民币
    @JacksonXmlProperty(localName = "refund_fee_type")
	@JacksonXmlCData
    private String refundFeeType;

    // 操作员帐号, 默认为商户号
    @JacksonXmlProperty(localName = "op_user_id")
	@JacksonXmlCData
    private String opUserId;

    @JacksonXmlCData
    @JacksonXmlProperty(localName = "refund_desc")
    private String refundDesc;

}
