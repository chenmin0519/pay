package com.zhonghuilv.aitravel.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public final class RegUtils {

    private RegUtils() {
    }

    public static boolean isPhone(String phone) {
        if (StringUtils.isBlank(phone))
            return true;
        //1 开的头11位 。。。
        return Pattern.matches("^1[0-9]{10}$", phone);
    }

}
