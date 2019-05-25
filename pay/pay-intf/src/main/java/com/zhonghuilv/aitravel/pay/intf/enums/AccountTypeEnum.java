package com.zhonghuilv.aitravel.pay.intf.enums;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public enum  AccountTypeEnum {
    USER("用户账户"),
    PLAT("平台账户");

    private String desc;

    AccountTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

}
