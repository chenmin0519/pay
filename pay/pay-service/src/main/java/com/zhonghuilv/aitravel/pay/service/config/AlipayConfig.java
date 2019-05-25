package com.zhonghuilv.aitravel.pay.service.config;

import com.alipay.api.AlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Component
@ConfigurationProperties("pay.alipay")
@Data
@Validated
public class AlipayConfig {
    @NotNull
    private String appId;

    private String businessNo;

    private String apiIp;

    private String notifyUrl;
    @NotNull
    private String alipayPublicKey;
    @NotNull
    private String appPrivateKey;

    private String appPublicKey;
    @NotNull
    private String androidSign;
    @NotNull
    private String androidPackage;
    @NotNull
    private String alipayGateway;
    private String appGateway;
    private String appOauthCallback;
    @NotNull
    private String signType;

    private String sellerId;

    private Double payRate;

    private String productCode;

    private AlipayClient alipayClient;
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

}
