package com.zhonghuilv.aitravel.pay.service.supports.wechat.response;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 微信退款接口 返回结果
 */
@Data
public class WechatPayRefundResponse extends WechatPayResponse {

    @JacksonXmlProperty(localName = "appid")
	@JacksonXmlCData
    private String appId;

    @JacksonXmlProperty(localName = "mch_id")
	@JacksonXmlCData
    private String mchId;

    @JacksonXmlProperty(localName = "device_info")
	@JacksonXmlCData
    private String deviceInfo;

    @JacksonXmlProperty(localName = "nonce_str")
	@JacksonXmlCData
    private String nonceStr;

    @JacksonXmlProperty(localName = "sign")
	@JacksonXmlCData
    private String sign;

    // 商户退款单号
    @JacksonXmlProperty(localName = "out_refund_no")
	@JacksonXmlCData
    private String outRefundNo;

    // 微信退款单号
    @JacksonXmlProperty(localName = "refund_id")
	@JacksonXmlCData
    private String refundId;

    // 退款渠道 ORIGINAL—原路退款，    BALANCE—退回到余额
    @JacksonXmlProperty(localName = "refund_channel")
	@JacksonXmlCData
    private String refundChannel;

    @JacksonXmlProperty(localName = "total_fee")
	@JacksonXmlCData
    private String totalFee;

    @JacksonXmlProperty(localName = "fee_type")
	@JacksonXmlCData
    private String feeType;

    // 微信订单号
    @JacksonXmlProperty(localName = "transaction_id")
	@JacksonXmlCData
    private String transactionId;

    // 商户订单号， 商户内部系统的订单号，  32个字符内，可包含字母。 微信支付要求商户订单号保持唯一性
    @JacksonXmlProperty(localName = "out_trade_no")
	@JacksonXmlCData
    private String outTradeNo;

    @JacksonXmlProperty(localName = "refund_fee")
    private String refundFee;

    /**
     * 现金支付金额
     */
    @JacksonXmlProperty(localName = "cash_fee")
    private String cashFee;

    /**
     * 退款代金券使用数量
     */
    @JacksonXmlProperty(localName = "coupon_refund_fee")
    private String couponRefundFee;

}
