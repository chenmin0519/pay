package com.zhonghuilv.aitravel.pay.service.service.impl;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zhonghuilv.aitravel.common.excption.DataNotAvailableException;
import com.zhonghuilv.aitravel.pay.intf.pojo.WechatMch;
import com.zhonghuilv.aitravel.pay.service.mapper.WechatMchMapper;
import com.zhonghuilv.aitravel.pay.service.service.WechatMchService;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.client.WechatPayClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Service
@CacheConfig(cacheNames = "pay:service:wechat:mch:")
public class WechatMchServiceImpl implements WechatMchService {

    @Autowired
    public WechatMchMapper wechatMchMapper;

    @Value("${spring.profiles:prod}")
    private String profiles;

    private ObjectMapper objectMapper = new XmlMapper();

    public WechatMchServiceImpl() {
        objectMapper.enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public WechatMch parent;

    /**
     * 加载商务号
     *
     * @param appid 小程序ID
     * @return
     */
    @Override
    @Cacheable(key = "#p0")
    public WechatPayClient loadClient(String appid) {

        WechatMch wechatMch = wechatMchMapper.selectByAppid(appid);
        if (wechatMch != null) {

            //特约商户
            if (wechatMch.isServerChild()) {
                WechatMch server = wechatMchMapper.selectByPrimaryKey(wechatMch.getPid());
                if (server == null) {
                    throw new DataNotAvailableException("服务商不存在！");
                }

                String serverAppid = wechatMchMapper.selectServerAppid(server.getId());
                if (StringUtils.isBlank(serverAppid)) {
                    throw new DataNotAvailableException("服务商APPid未配置！");
                }
                //构造服务商client
                return WechatPayClient.builder()
                        .appId(serverAppid)
                        .certFile(getCertFile(server.getCertPath()))
                        .apiKey(server.getPartnerKey())
                        .mchId(server.getMchId())
                        .subMchId(wechatMch.getMchId())
                        .subAppid(appid)
                        .mapper(objectMapper)
                        .build();
            }
            return WechatPayClient.builder()
                    .appId(appid)
                    .certFile(getCertFile(wechatMch.getCertPath()))
                    .apiKey(wechatMch.getPartnerKey())
                    .mchId(wechatMch.getMchId())
                    .mapper(objectMapper)
                    .build();
        }

        throw new DataNotAvailableException("该小程序未配置对应商户号！");
    }

    public byte[] getCertFile(String path) {
        //TODO 返回微信证书内容
//        if (StringUtils.isNotBlank(path)) {
//            OSSUtilly builder = OSSUtilly.builder(profiles);
//            try {
//                return IOUtils.toByteArray(builder.getSecurityFile(path)
//                        .getObjectContent());
//            } catch (IOException e) {
//                throw new DataNotAvailableException("获取证书失败：" + e.getMessage());
//            }
//        }
        return null;
    }
}
