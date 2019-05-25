package com.zhonghuilv.aitravel.service.util;

import tk.mybatis.mapper.entity.Example;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class ExampleBuiler {

    public static Example cls(Class<?> clazz) {

        return new Example(clazz);
    }
}
