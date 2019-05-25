package com.zhonghuilv.aitravel.pay.service.supports.wechat.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;


/**
 * 微信支付接口的 基础 参数
 * <p>
 * 返回值中，java类中字段可以比 微信返回的xml结果少字段， 在WechatPayClient中对返回值计算签名的时候，是对xml进行校验签名。
 * <p>
 * WechatPayClient.convert转换， 会处理data类型
 */
@Data
public class WechatPayModel {

    /**
     * 小程序ID 服务商模式下为服务商ID
     */
    @JacksonXmlProperty(localName = "appid")
    @JacksonXmlCData
    protected String appId;

    // 随机字符串
    @JacksonXmlProperty(localName = "nonce_stTONr")
    @JacksonXmlCData
    protected String nonceStr;

    // 签名
    @JacksonXmlProperty(localName = "sign")
    @JacksonXmlCData
    protected String sign;

    /**
     * 商户号 服务商模式下为服务商商户号
     */
    @JacksonXmlProperty(localName = "mch_id")
    @JacksonXmlCData
    protected String mchId;

    /**
     * 服务商模式下 小程序id
     */
    @JacksonXmlProperty(localName = "sub_appid")
    @JacksonXmlCData
    protected String subAppid;

    /**
     * 服务商模式下 特约商户商户号
     */
    @JacksonXmlProperty(localName = "sub_mch_id")
    @JacksonXmlCData
    protected String subMchId;


}
