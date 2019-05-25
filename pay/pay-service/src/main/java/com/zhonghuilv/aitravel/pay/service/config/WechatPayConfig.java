package com.zhonghuilv.aitravel.pay.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Component
@ConfigurationProperties("pay.wechatpay")
@Data
@Validated
public class WechatPayConfig {

    private String appSecret;

    @NotNull
    private String prepayUrl;
    private String notifyUrl;
    private String downloadBillUrl;
    private String refundUrl;
    private String orderqueryUrl;

    /**
     * 转账URL
     */
    private String transfersUrl;
    @NotNull
    private Double payRate;

    private String spbillCreateIp;

    @NotNull
    private String certPath;

    /**
     * 异步消息处理成功的响应数据
     */
    @NotNull
    private String respSuccess;

    /**
     * 异步消息处理失败的响应数据
     */
    @NotNull
    private String respError;

    @NotNull
    private Map<String, AppInfo> appInfo;

    public String getMechIdByAppid(String appid) {

        if (appInfo.containsKey(appid)) {
            return appInfo.get(appid).getMchId();
        }
        throw new IllegalArgumentException("appid未配置:" + appid);
    }

    public AppInfo findAppInfo(String appid) {
        if (appInfo.containsKey(appid)) {
            return appInfo.get(appid);
        }
        throw new IllegalArgumentException("appid未配置:" + appid);
    }

    @Data
    public static class AppInfo {

        private String appid;

        private String partnerKey;

        private String mchId;
    }
}
