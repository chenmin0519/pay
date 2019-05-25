package com.zhonghuilv.aitravel.pay.service.supports.wechat.request;

/**
 * 微信服务商
 *
 */
public interface IWechatService {

    /**
     * 获取服务商ID
     *
     * @return
     */
    String getAppid();

    /**
     * 商户号
     *
     * @return
     */
    String getMchId();

    /**
     * 小程序的APPID
     *
     * @return
     */
    String getSubAppid();

    /**
     * 子商户号
     *
     * @return
     */
    String getSubMchId();



}
