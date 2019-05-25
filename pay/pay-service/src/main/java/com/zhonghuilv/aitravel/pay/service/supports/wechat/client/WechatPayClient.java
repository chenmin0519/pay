package com.zhonghuilv.aitravel.pay.service.supports.wechat.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhonghuilv.aitravel.common.excption.DataNotAvailableException;
import com.zhonghuilv.aitravel.common.excption.ServiceLogicException;
import com.zhonghuilv.aitravel.pay.intf.util.PayCommonUtil;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.exception.WechatPayException;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.SignatureItem;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.WechatPayModel;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.request.WechatPayRequest;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.response.WechatPayResponse;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;

import javax.net.ssl.SSLContext;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 微信支付， 分装的客户端， 对request自动加上签名，转成xml， 发送请求到微信支付，  会返回结果做解析
 */
@Builder
@Slf4j
@Data
public class WechatPayClient {

    private ObjectMapper mapper;
    private static final String SERVER_URL = "https://api.mch.weixin.qq.com";
    private String appId; // 微信为公众账号Id 或服务商ID
    private String mchId; // 微信支付 商户号
    private String apiKey;// 秘钥，用于签名

    //服务商模式下的小程序ID
    private String subAppid;

    //服务商模式下的特约商户商务号
    private String subMchId;

    private byte[] certFile;// 退款时候 数字证书

    public <T extends WechatPayResponse> T execute(WechatPayRequest<? extends WechatPayModel, T> request) {
        String rsp = executePost(request);
        log.info("wechat.pay.rsp=" + rsp);

        T response = null;
        try {
            response = mapper.readValue(rsp, request.getResponseClass());
        } catch (IOException e) {
            throw new ServiceLogicException("微信响应数据转换出错：" + e.getMessage());
        }
        return response;
    }

    public <T extends WechatPayResponse> String  executePost(WechatPayRequest<? extends WechatPayModel, T> request) {

        return post(SERVER_URL + request.getApiAction(), request);
    }



    public String sign(SortedMap<String, String> data) {
        return PayCommonUtil.createSign(data, apiKey);
    }

    /**
     * 获取 返回值中的sign，以及计算sign的字符串
     *
     * @param rsp
     * @return
     */
    private SignatureItem getSignatureItem(String rsp) throws IOException {


        Map<String, Object> data = mapper.readValue(rsp, Map.class);
        if (data == null || data.isEmpty()) {
            return null;
        }
        if (!data.containsKey("sign")) {
            return null;
        }
        SignatureItem signatureItem = new SignatureItem();
        signatureItem.setSign(String.valueOf(data.get("sign")));
        signatureItem.setSignContent(PayCommonUtil.sign(data, this.apiKey));
        return signatureItem;
    }

    /**
     * 根据返回值 解析成responseModel，校验签名
     *
     * @param clazz
     * @param rsp
     * @param <T>
     * @return
     * @throws WechatPayException
     */
    public <T extends WechatPayResponse> T parse(Class<T> clazz, String rsp) throws IOException {
        T response = mapper.readValue(rsp, clazz);

        SortedMap<String, String> convert = convert(response);
        if (response == null) {
            throw new WechatPayException("微信支付 解析结果失败!");
        }

        SignatureItem signItem = getSignatureItem(rsp);
        if (signItem == null) {
            throw new WechatPayException(response.toString());
        }

        if (response.isSuccess() || (!response.isSuccess() && !StringUtils.isEmpty(signItem.getSign()))) {
            boolean checkContent = PayCommonUtil.isTenpaySign(convert, this.apiKey);
            if (!checkContent) {
                throw new WechatPayException("sign check fail: check Sign and Data Fail!");
            }
        }
        return response;
    }

    /**
     * 发起微信支付请求
     *
     * @param url
     * @param request
     * @param <T>
     * @return
     * @throws WechatPayException
     */
    private <T extends WechatPayResponse> String post(String url, WechatPayRequest<? extends WechatPayModel, T>
            request) {
        WechatPayModel model = request.getModel();

        model.setAppId(appId);
        model.setMchId(mchId);
        if (this.isServer()) {
            model.setSubAppid(subAppid);
            model.setSubMchId(subMchId);
        }
        model.setNonceStr(UUID.randomUUID().toString().replace("-", ""));

        SortedMap<String, String> data = convert(model);
        log.info(String.format("wechat.pay.url=%s,request=%s", url, data));
        String sign = sign(data);
        model.setSign(sign);
        data.put("sign", sign);

        String text;
        //判断是否需要带上 支付证书
        try (CloseableHttpClient client = this.getClient(request)) {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded");
            String bodyString = mapper.writeValueAsString(data);
//            bodyString = bodyString.replaceAll("(<)(/?)(TreeMap>)", "<$2xml>");
            log.trace("wechat requrest body:{}", bodyString);
            //替换掉
            StringEntity payload = new StringEntity(bodyString, "UTF-8");
            httpPost.setEntity(payload);
            text = client.execute(httpPost, response -> {
                StringBuilder builder = new StringBuilder();
                HttpEntity entity = response.getEntity();
                String temp;
                if (entity != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
                    while ((temp = bufferedReader.readLine()) != null) {
                        builder.append(temp);
                    }

                }

                return builder.toString();
            });
        } catch (Exception e) {
            throw new WechatPayException(e.getMessage());
        }
        return text;
    }

    /**
     * 是否为服务商模式
     *
     * @return
     */
    public boolean isServer() {
        return StringUtils.isNotBlank(subAppid);
    }

    public SortedMap<String, String> convert(Object model) {
        Map<String, Object> data = mapper.convertValue(model, Map.class);
        SortedMap<String, String> params = new TreeMap<>();
        data.forEach((key, value) -> params.put(key, String.valueOf(value)));
        return params;
    }

    //判断是否需要带上 支付证书
    private <T extends WechatPayResponse> CloseableHttpClient getClient(WechatPayRequest<? extends WechatPayModel, T>
                                                                                request) {
        CloseableHttpClient client;
        if (request.requireCert()) {
            try {
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                ByteArrayInputStream inputStream = new ByteArrayInputStream(certFile);
                try {
                    keyStore.load(inputStream, this.mchId.toCharArray());
                } catch (Exception exception) {
                    throw new DataNotAvailableException("加载商务号证书失败，请联系管理员重新配置:" + exception.getMessage());
                } finally {
                    inputStream.close();
                }

                SSLContext sslcontext = org.apache.http.ssl.SSLContexts.custom().loadKeyMaterial(keyStore, this.mchId
                        .toCharArray())
                        .build();

                SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslcontext, new
                        String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
                client = HttpClients.custom().setSSLSocketFactory(factory).build();
            } catch (Exception e) {
                throw new WechatPayException(e.getMessage());
            }

        } else {
            client = HttpClients.createDefault();
        }
        return client;
    }

}


















































