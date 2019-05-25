package com.zhonghuilv.aitravel.pay.intf.pojo;


import com.zhonghuilv.aitravel.pay.intf.enums.EnumPayStatus;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumPayType;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumTradeType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 支付订单
 */
@Data
public class PayOrder {
    private int id;
    private String payTypeCode; // PayTypeCode， 支付类型，微信 ，支付宝
    private String tradePayNo; // 业务方 支付订单号，
    private String payOrderNo;// 用于微信，支付宝的  商户交易流水号， 唯一索引。  out_trade_no
    private String prePayId;// 微信、支付宝返回的 给app或者网页的支付凭证，  客户端通过此信息调起支付界面。
    private String payId; // 微信支付内部订单号(transaction_id)，  支付宝内部交易号(trade_no)，  一般使用 payOrderNo,
    private String userIp;
    private int payAmount; // 支付金额，精确到分
    private LocalDateTime payTime; // 支付时间
    private int status; // EnumPayStatus
    private String errorCode; // 如果创建订单失败，则保存第三方返回的失败错误码
    private String errorMsg;
    private LocalDateTime startTime;// 支付申请时间
    private LocalDateTime expireTime; // 支付过期时间， 默认为2小时
    private String openId; // 微信为用户的openId，支付宝为buyer_id	买家支付宝用户号
    private String buyerLogonId;// 支付宝中：买家支付宝账号
    private String notifyUrl; // 回调业务方的url
    private String extra;// 附加信息， 支付完成后通知时候会原封不动返回业务方。
    private String subject; // 订单标题，微信中对应body字段，
    private String detail; // 订单描述，微信中对应detail字段，为json格式。 支付宝中对应 body字段，表示描述，字符串
    private String codeUrl; // 二维码链接
    private String merchantId; // 业务方 商户号 ,PayMerchant
    private String tradeType; // TradeTypeCode，支付类型，如扫码，app支付，wap支付等。
    private String returnUrl; // 支付成功页，  支付宝：页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问，  微信需要在前端自己设置
    private int refundAmount; // 退款额度，精确到分

    public EnumPayType getPayTypeCodeEnum() {
        return EnumPayType.valueOf(payTypeCode);
    }

    public EnumTradeType getTradeTypeCodeEnum() {
        return EnumTradeType.getTradeTypeCode(tradeType);
    }

    public String getStatusDesc() {
        EnumPayStatus s = EnumPayStatus.getPayStatus(status);
        if (s != null) {
            return s.getName();
        }
        return "";
    }

}
