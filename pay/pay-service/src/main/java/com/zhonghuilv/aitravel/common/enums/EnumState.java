package com.zhonghuilv.aitravel.common.enums;

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
public enum EnumState {

    NORMAL(1,"正常"),
    FORBIDEN(0,"禁用"),
    ;

    private Integer key;

    private String desc;
}
