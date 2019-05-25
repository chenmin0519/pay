package com.zhonghuilv.aitravel.pay.service.service;

import com.zhonghuilv.aitravel.pay.service.supports.wechat.client.WechatPayClient;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface WechatMchService {

    /**
     * 加载商务号
     * @param appid 小程序ID
     * @return
     */
    WechatPayClient loadClient(String appid);
}
