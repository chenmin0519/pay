package com.zhonghuilv.aitravel.pay.api.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
    * @Description: mq消息头
    * @Author:      chenmin
    * @CreateDate:  2019-05-23 2019-05-23
    * @Version:     1.0
    * @JDK:         10
    */
public interface TravelOrderSink {

    String INPUT = "BIZ_CODE_ORDER";

    @Input(INPUT)
    SubscribableChannel travelOrderInput();
}
