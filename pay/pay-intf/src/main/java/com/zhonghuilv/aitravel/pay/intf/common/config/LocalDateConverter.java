package com.zhonghuilv.aitravel.pay.intf.common.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

/**
    * @Description: LocalDate 全局转换器
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
//@Component
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Nullable
    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source);
    }
}
