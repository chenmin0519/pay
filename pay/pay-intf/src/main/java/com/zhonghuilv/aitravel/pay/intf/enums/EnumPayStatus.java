package com.zhonghuilv.aitravel.pay.intf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * 支付状态
 */
@AllArgsConstructor
@Getter
public enum EnumPayStatus {
    CREATE_PAYMENT(0, "创建支付订单"),
    CREATE_PAYMENT_SUCCESS(1, "创建支付订单成功"),
    PAY_SUCCESS(2, "支付成功"),
    CREATE_PAYMENT_FAIL(3, "创建支付订单失败"),
    PAY_FAIL(4, "支付失败"),
    PAY_CHECKING(5, "支付中"),
    PAY_CLOSE(6, "支付订单关闭"),
    REFUND_PART(7, "部分退款"),
    REFUND_SUCCESS(8, "全部退款"),;

    private Integer value;
    private String name;

    public static EnumPayStatus getPayStatus(int status) {
        return Stream.of(values())
                .filter(payTye -> payTye.getValue().equals(status)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("获取支付类型枚举失败！"));
    }

    private static Map<Integer, String> map = new HashMap<Integer, String>(EnumPayStatus.values().length);

    static {
        for (EnumPayStatus enumPayStatus : EnumPayStatus.values()) {
            map.put(enumPayStatus.getValue(), enumPayStatus.getName());
        }
    }

    public static Map<Integer, String> getPayStatusMap() {
        return map;
    }
}
