package com.zhonghuilv.aitravel.pay.intf.common.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
//@Component
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Nullable
    @Override
    public LocalDateTime convert(String source) {

        return LocalDateTime.parse(source);
    }
}
