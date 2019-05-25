package com.zhonghuilv.aitravel.pay.service.supports.wechat.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

/**
 * 微信刷卡支付 接口参数
 * <p/>
 * Note见：WechatPayMicroPayRequest
 * <p/>
 * 目前比微信接口少了 cash_fee等字段
 */
@Data
public class WechatPayMicroPayModel extends WechatPayModel {
    // 非必填 设备号  终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
    @JacksonXmlProperty(localName = "device_info")
	@JacksonXmlCData
    private String deviceInfo;

    // 商品描述  商品或支付简要描述
    @JacksonXmlProperty(localName = "body")
	@JacksonXmlCData
    private String body;

    // 商品名称 明细列表，json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
    @JacksonXmlProperty(localName = "detail")
	@JacksonXmlCData
    private String detail;

    // 非必填 附加数据， 在查询Api和支付通知中 原样返回，该字段主要用于商户携带订单的自定义数据
    @JacksonXmlProperty(localName = "attach")
	@JacksonXmlCData
    private String attach;

    // 商户订单号， 商户内部系统的订单号，  32个字符内，可包含字母。 微信支付要求商户订单号保持唯一性
    @JacksonXmlProperty(localName = "out_trade_no")
	@JacksonXmlCData
    private String outTradeNo;

    //非必填 默认人民币  CNY
    @JacksonXmlProperty(localName = "fee_type")
	@JacksonXmlCData
    private String feeType;

    // 订单总金额，单位为分
    @JacksonXmlProperty(localName = "total_fee")
	@JacksonXmlCData
    private int totalFee;

    //  app和网页支付提交用户Id，native支付填调用微信支付Api的机器IP
    @JacksonXmlProperty(localName = "spbill_create_ip")
	@JacksonXmlCData
    private String spbillCreateIp;

    // 非必填  商品标记，代金券或立减优惠功能的参数
    @JacksonXmlProperty(localName = "goods_tag")
	@JacksonXmlCData
    private String goodsTag;

    // 非必填  指定支付方式  no_credit--指定不能使用信用卡支付
    @JacksonXmlProperty(localName = "limit_pay")
	@JacksonXmlCData
    private String limitPay;

    // 授权码  扫码支付授权码，设备读取用户微信中的条码或者二维码信息
    @JacksonXmlProperty(localName = "auth_code")
	@JacksonXmlCData
    private String authCode;

}
