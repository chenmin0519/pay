package com.zhonghuilv.aitravel.pay.intf.enums;

/**
 * @author chenmin
 * @title: EnumOrderNum
 * @projectName shop-springboot
 * @description: TODO
 * @date 2019-05-1916:12
 * @Version: 1.0
 * @JDK: 10
 */
public enum EnumOrderNum {
    TN("TN","兑奖订单"),SN("SN","升星订单"),NO("NO","普通订单");
    private String key;
    private String value;

    EnumOrderNum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
