package com.zhonghuilv.aitravel.pay.intf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * JSAPI 微信公众号， 微信浏览器支付（数据库中配置为  WAP,service中特殊处理即可）
 * <p/>
 * 业务方调用的时候，传以下3中类型即可，payService中对自动把WAP根据需要解析成JSAPI：
 * NATIVE 扫码
 * App  app支付
 * WAP 浏览器，网页版支付
 * <p/>
 * 微信支付类型：
 * https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=4_2
 */
@AllArgsConstructor
@Getter
public enum EnumTradeType {
    JSAPI(1, "WAP支付"), NATIVE(2, "扫码支付"), APP(3, "APP支付"), WAP(4, "WAP支付"), MICROPAY(5, "刷卡支付");
    private Integer id;
    private String name;

    public static EnumTradeType getTradeTypeCode(int id) {

        return Stream.of(values())
                .filter(payTye -> payTye.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("获取支付类型枚举失败！"));
    }

    // type： NATIVE,APP,WAP 等
    public static EnumTradeType getTradeTypeCode(String type) {
        return EnumTradeType.valueOf(type);
    }


}
