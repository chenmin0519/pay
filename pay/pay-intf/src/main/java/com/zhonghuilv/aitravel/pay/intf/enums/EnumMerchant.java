package com.zhonghuilv.aitravel.pay.intf.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@AllArgsConstructor
@Getter
public enum EnumMerchant {

    AI_TRAVAL("点创未来", "点创未来");
    private String code;

    private String name;
}
