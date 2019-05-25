package com.zhonghuilv.aitravel.pay.intf.enums;

/**
 * 微信支付返回的相关状态
 */
public enum WechatStateEnum {

    SUCCESS("支付成功"),
    REFUND("转入退款"),
    NOTPAY("未支付"),
    CLOSED("已关闭"),
    REVOKED("已撤销（刷卡支付）"),
    USERPAYING("用户支付中"),
    PAYERROR("支付失败(其他原因，如银行返回失败)");

    private String desc;

    WechatStateEnum(String desc) {
        this.desc = desc;
    }

}
