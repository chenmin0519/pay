package com.zhonghuilv.aitravel.pay.intf.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
    * @Description: java config 配置ossclient
    * oss 最大权限配置 后台操作文件时使用
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OSSConfig {

    protected static final Map<String, OSSConfig> OSS_CONFIG_MAP = new ConcurrentHashMap<>();

    static {

        String accessKeyId = "LTAIKPSkrGvj5CUA";
        String accessKeySecret = "mill2057G3FW6p9sgq1c1EODTpjTQU";

        String endpointHangezhou = "oss-cn-hangzhou.aliyuncs.com";
        String endpointShenzhen = "oss-cn-shenzhen.aliyuncs.com";
        OSS_CONFIG_MAP.put("dev",
                new OSSConfig(
                        accessKeyId,
                        accessKeySecret,
                        new BucketConfig("ailx-static-dev",
                                endpointShenzhen,
                                "http://ailx-static-dev.oss-cn-hangzhou.aliyuncs.com")
                        , new BucketConfig("ailx-static-dev-security",
                        endpointHangezhou)
                ));

        OSS_CONFIG_MAP.put("test",
                new OSSConfig(
                        accessKeyId,
                        accessKeySecret,
                        new BucketConfig("ailx-static-dev",
                                endpointShenzhen,
                                "http://ailx-static-dev.oss-cn-hangzhou.aliyuncs.com")
                        , new BucketConfig("ailx-static-dev-security",
                        endpointHangezhou)
                ));

        OSS_CONFIG_MAP.put("prod",
                new OSSConfig(
                        accessKeyId,
                        accessKeySecret,
                        new BucketConfig("ailx-static",
                                endpointShenzhen,
                                "http://ailx-static.oss-cn-hangzhou.aliyuncs.com")
                        , new BucketConfig("ailx-static-security",
                        endpointHangezhou)
                ));
//
//        OSS_CONFIG_MAP.put("test",
//                new OSSConfig("LTAIKPSkrGvj5CUA",
//                        "LTAIKPSkrGvj5CUA",
//                        "oss-cn-hangzhou.aliyuncs.com",
//                        "ailx-static-dev",
//                        "ailx-static-dev-security",
//                        "http://ailx-static-dev.oss-cn-hangzhou.aliyuncs.com"));
//
//        OSS_CONFIG_MAP.put("prod",
//                new OSSConfig("LTAIKPSkrGvj5CUA",
//                        "gxtu4BHLogLzgYRMEb4XfOD82waBP8",
//                        "oss-cn-hangzhou.aliyuncs.com",
//                        "ailx-static",
//                        "ailx-static-security",
//                        "http://ailx-static.oss-cn-hangzhou.aliyuncs.com"));
    }


    private String accessKeyId;

    private String accessKeySecret;

    private BucketConfig defaultBucket;

    private BucketConfig securityBucket;

    public static OSSConfig getConfigByProfile(String profile) {
        return OSS_CONFIG_MAP.get(profile);
    }

    @Data
    @AllArgsConstructor
    public static class BucketConfig {

        private String name;

        private String endpoint;

        private String bucketDomain;

        public BucketConfig(String name, String endpoint) {
            this.name = name;
            this.endpoint = endpoint;
            this.bucketDomain = "http://" + name + "." + endpoint;
        }
    }

}

