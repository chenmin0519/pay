package com.zhonghuilv.aitravel.pay.intf.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class PayCommonUtil {

    private static final Logger log = LoggerFactory.getLogger(PayCommonUtil.class.getName());

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(String characterEncoding, SortedMap<String, String> params, String API_KEY) {

        String stringSignTemp = getUrlParamsByMap(params) + "key=" + API_KEY;
        //算出摘要
        String mysign = MD5Util.MD5Encode(stringSignTemp, characterEncoding).toLowerCase();
        String tenpaySign = params.get("sign").toLowerCase();
        return tenpaySign.equals(mysign);
    }

    /**
     * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     *
     * @return boolean
     */
    public static boolean isTenpaySign(SortedMap<String, String> params, String API_KEY) {

        return isTenpaySign(DEFAULT_CHARSET, params, API_KEY);
    }

    /**
     * 签名
     * @param params 参数
     * @param apiKey 商户密钥
     * @return
     */
    public static String sign(Map<String, Object> params, String apiKey) {

        SortedMap<String, String> sortedMap = new TreeMap<>();
        params.forEach((key, value) -> sortedMap.put(key, String.valueOf(value)));
        return createSign(DEFAULT_CHARSET, sortedMap, apiKey);
    }

    /**
     * sign签名
     *
     * @param params 请求参数
     */
    public static String createSign(SortedMap<String, String> params, String API_KEY) {

        String stringSignTemp = getUrlParamsByMap(params) + "key=" + API_KEY;
        return MD5Util.MD5Encode(stringSignTemp, DEFAULT_CHARSET).toUpperCase();
    }

    /**
     * sign签名
     *
     * @param characterEncoding 编码格式
     * @param params            请求参数
     */
    public static String createSign(String characterEncoding, SortedMap<String, String> params, String API_KEY) {

        String stringSignTemp = getUrlParamsByMap(params) + "key=" + API_KEY;
        log.info("stringSignTemp--->>>>" + stringSignTemp);
        return MD5Util.MD5Encode(stringSignTemp, characterEncoding).toUpperCase();
    }

    /**
     * sign签名
     *
     * @param characterEncoding 编码格式
     */
    public static String createSign(String characterEncoding, WechatPayParams payParams, String API_KEY) {

        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
        SortedMap<String, Object> map = objectMapper.convertValue(payParams, SortedMap.class);
        SortedMap<String, String> strMap = new TreeMap<>();
        map.forEach((key, value) -> strMap.put(key, String.valueOf(value)));
        String stringSignTemp = getUrlParamsByMap(strMap) + "key=" + API_KEY;
        log.info("stringSignTemp--->>>>" + stringSignTemp);
        return MD5Util.MD5Encode(stringSignTemp, characterEncoding).toUpperCase();
    }

    /**
     * 使用 Map按key进行排序
     *
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * @param parameters 请求参数
     * @return
     * @Description：将请求参数转换为XML格式的string
     */
    public static String getRequestXml(SortedMap<String, String> parameters) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        parameters.forEach((key, value) -> {
            if ("attach".equalsIgnoreCase(key) || "body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<" + key + ">" + "<![CDATA[" + value + "]]></" + key + ">");
            } else {
                sb.append("<" + key + ">" + value + "</" + key + ">");
            }
        });
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * 取出一个指定长度大小的随机正整数.
     *
     * @param length int 设定所取出随机数的长度。length小于11
     * @return int 返回生成的随机数。
     */
    public static int buildRandom(int length) {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < length; i++) {
            num = num * 10;
        }
        return (int) ((random * num));
    }

    /**
     * 获取当前时间 yyyyMMddHHmmss
     *
     * @return String
     */
    public static String getCurrTime() {

        return getDateFormat(new Date());
    }

    public static String getDateFormat(Date date) {
        SimpleDateFormat outFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return outFormat.format(date);
    }

    /**
     * 获取根据key排序house的 url参数拼接值
     *
     * @param params 参数map
     * @return
     */
    private static String getUrlParamsByMap(SortedMap<String, String> params) {
        Map<String, String> resultMap = sortMapByKey(params);
        StringBuffer sb = new StringBuffer();

        resultMap.forEach((k, v) -> {
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        });
        return sb.toString();
    }

}
