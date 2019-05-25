package com.zhonghuilv.aitravel.pay.intf.util;

import java.util.UUID;

/**
 * 随机函数生成
 */
public class RandomUtility {

    /**
     * 获取去掉横线的长度为32的UUID串.
     * @return uuid.
     * @author WuShuicheng.
     */
    public static String get32UUID(){
       return UUID.randomUUID().toString().replaceAll("-","");
    }
}
