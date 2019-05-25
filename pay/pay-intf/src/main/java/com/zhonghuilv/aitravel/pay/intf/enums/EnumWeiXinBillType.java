package com.zhonghuilv.aitravel.pay.intf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EnumWeiXinBillType {
    ALL("返回当日所有订单信息，默认值"),

    SUCCESS("返回当日成功支付的订单"),

    REFUND("返回当日退款订单"),

    RECHARGE_REFUND("返回当日充值退款订单");

    String desc;
}
