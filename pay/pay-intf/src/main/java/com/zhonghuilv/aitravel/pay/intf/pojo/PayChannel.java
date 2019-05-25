package com.zhonghuilv.aitravel.pay.intf.pojo;


import com.zhonghuilv.aitravel.pay.intf.enums.EnumPayType;
import lombok.Data;

/**
 * 存储微信、支付宝  apikey，公钥私钥等信息。
 * <p>
 * 支付宝支付采用了RSA加密签名的安全通信机制，开发者可以通过支付宝的公钥验证消息的来源，同时使用自己的私钥进行信息加密
 */
@Data
public class PayChannel {
    private int id;
    private int payTypeId; // PayTypeCode.id
    private String payTypeName; // PayTypeCode.name
    private String payTypeCode; // 支付类型
    private String payChannelName; // 支付描述， 如 官方app支付， 扫码支付等。
    private String signType; // 加密方式，如 MD5 微信,  RSA 支付宝
    private String certFileId; // 仅微信使用，凭证文件Id，对应 FileResources
    private String apiKey; // 仅微信使用，开通微信支付后，会把 微信支付的账号，密码，以及 apikey发给开发者。  用于签名
    private String appId; // 微信为公众账号Id，  支付宝为20开头的一串数字（管理中心-我的应用）
    private String mchId; // 商户Id/合作伙伴Id， 例如 微信为12开头的一串数字(账户信息-微信支付商户号)，支付宝为（从我的应用-查看-使用者管理-使用者Id）
    private int status; // 1正常，2 不可用
    private String mchKey; // 商户私钥，  商户公钥需要在支付宝开放平台设置，
    private String platformKey; // 支付宝公钥，  又支付宝开放平台提供
    private int queryChannelId;// 对应PayChannel,0表示本身， 在调用支付宝查询订单(AliPayService.synchronize)功能时候，对应的开放平台秘钥Id。

    public EnumPayType getPayTypeCodeEnum() {
        return EnumPayType.getPayType(payTypeId);
    }
}
