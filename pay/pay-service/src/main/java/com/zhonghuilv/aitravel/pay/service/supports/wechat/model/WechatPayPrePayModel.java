package com.zhonghuilv.aitravel.pay.service.supports.wechat.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信统一下单 接口参数
 */
@Data
@Builder
@XmlRootElement(name = "xml")
public class WechatPayPrePayModel extends WechatPayModel {
    // 非必填 设备号  终端设备号(门店号或收银设备ID)，注意：PC网页或公众号内支付请传"WEB"
    @JacksonXmlProperty(localName = "device_info")
    private String deviceInfo = "WEB";

    // 商品描述  商品或支付简要描述
    @JacksonXmlProperty(localName = "body")
    private String body;

    // 商品名称 明细列表，json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
    @JacksonXmlProperty(localName = "detail")
    private String detail;

    // 非必填 附加数据， 在查询Api和支付通知中 原样返回，该字段主要用于商户携带订单的自定义数据
    @JacksonXmlProperty(localName = "attach")
    private String attach;

    // 商户订单号， 商户内部系统的订单号，  32个字符内，可包含字母。 微信支付要求商户订单号保持唯一性
    @JacksonXmlProperty(localName = "out_trade_no")
    private String outTradeNo;

    //非必填 默认人民币  CNY
    @JacksonXmlProperty(localName = "fee_type")
    private String feeType;

    // 订单总金额，单位为分
    @JacksonXmlProperty(localName = "total_fee")
    private int totalFee;

    //  app和网页支付提交用户Id，native支付填调用微信支付Api的机器IP
    @JacksonXmlProperty(localName = "spbill_create_ip")
    private String spbillCreateIp;

    //  非必填  交易开始时间  格式为yyyyMMddHHmmss
    @JacksonXmlProperty(localName = "time_start")
    @JacksonXmlCData
    @JsonFormat
    private String timeStart;

    // 非必填   交易结束时间  格式为yyyyMMddHHmmss
    @JacksonXmlProperty(localName = "time_expire")
    @JacksonXmlCData
    private String timeExpire;

    // 非必填  商品标记，代金券或立减优惠功能的参数
    @JacksonXmlProperty(localName = "goods_tag")
    @JacksonXmlCData
    private String goodsTag;

    // 通知地址， 接受微信支付异步通知回调地址，不能携带参数。
    @JacksonXmlProperty(localName = "notify_url")
    @JacksonXmlCData
    private String notifyUrl;

    // 交易类型，  JSAPI,NATIVE,APP
    @JacksonXmlProperty(localName = "trade_type")
    @JacksonXmlCData
    private String tradeType;

    // 非必填  商品Id trade_type=NATIVE，此参数必传。此id为二维码中包含的商品ID，商户自行定义。
    @JacksonXmlProperty(localName = "product_id")
    @JacksonXmlCData
    private String productId;

    // 非必填  指定支付方式  no_credit--指定不能使用信用卡支付
    @JacksonXmlProperty(localName = "limit_pay")
    @JacksonXmlCData
    private String limitPay;

    // 非必填 trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识
    @JacksonXmlProperty(localName = "openid")
    @JacksonXmlCData
    private String openId;

    // 服务商模式下小程序支付必填 trade_type=JSAPI
    @JacksonXmlProperty(localName = "sub_openid")
    @JacksonXmlCData
    private String subOpenid;
}
