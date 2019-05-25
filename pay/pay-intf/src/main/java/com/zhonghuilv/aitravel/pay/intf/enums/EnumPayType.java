package com.zhonghuilv.aitravel.pay.intf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * 支付类型
 */
@AllArgsConstructor
@Getter
public enum EnumPayType {
    WECHAT_PAY(1, "微信"), ALI_PAY(2, "支付宝");

    private Integer id;
    private String name;

    public static EnumPayType getPayType(int id) {

        return Stream.of(values())
                .filter(payTye -> payTye.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("获取支付类型枚举失败！"));
    }

}
