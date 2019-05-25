package com.zhonghuilv.aitravel.pay.service.util;

import java.util.UUID;

/**
    * @Description: 随机函数生成
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class RandomUtility {


    private RandomUtility() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取去掉横线的长度为32的UUID串.
     * @return uuid.
     * @author WuShuicheng.
     */
    public static String get32UUID(){
       return UUID.randomUUID().toString().replaceAll("-","");
    }
}
