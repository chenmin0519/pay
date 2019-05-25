package com.zhonghuilv.aitravel.common.util;

import com.zhonghuilv.aitravel.common.constants.ApplicationConfig;

import java.util.Objects;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class NumberUtils {

    private NumberUtils() {
    }

    public static boolean needCount(Integer count) {
        return (Objects.nonNull(count) && count.compareTo(ApplicationConfig.INT_ZERO) == 1);
    }

    public static boolean needCount(Long count) {
        return (Objects.nonNull(count) && count.compareTo(ApplicationConfig.LONG_ZERO) == 1);
    }
}
