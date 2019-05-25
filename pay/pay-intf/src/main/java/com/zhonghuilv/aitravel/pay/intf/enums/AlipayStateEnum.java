package com.zhonghuilv.aitravel.pay.intf.enums;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public enum AlipayStateEnum {
    WAIT_BUYER_PAY("等待买家付款"),
    TRADE_CLOSED("交易关闭"),
    TRADE_SUCCESS("支付成功"),
    TRADE_FINISHED("交易完成");

    private String desc;

    AlipayStateEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }


    public static AlipayStateEnum getEnum(String name) {
        AlipayStateEnum[] arry = AlipayStateEnum.values();
        for (int i = 0; i < arry.length; i++) {
            if (arry[i].name().equalsIgnoreCase(name)) {
                return arry[i];
            }
        }
        return null;
    }
}
