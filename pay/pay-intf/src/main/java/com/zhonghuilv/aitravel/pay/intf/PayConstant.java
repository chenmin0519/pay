package com.zhonghuilv.aitravel.pay.intf;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface PayConstant {

    String SERVICE_NAME = "pay-service";

    String MQ_PREFIX = "pay.order.";


    String CHARSET = "UTF-8";

    /**
     * 支付宝时间格式
     * yyyy-MM-dd HH:mm:ss
     */
    DateTimeFormatter LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral(' ')
            .append(DateTimeFormatter.ISO_LOCAL_TIME)
            .toFormatter();

    /**
     * 微信时间格式
     * yyyyMMddHHmmss
     */
    DateTimeFormatter WEIXIN_DATE_TIME = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
}
