package com.zhonghuilv.aitravel.pay.service.supports.wechat.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumOrderFrom;
import com.zhonghuilv.aitravel.pay.intf.util.PayCommonUtil;
import com.zhonghuilv.aitravel.pay.intf.util.RandomUtility;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 微信支付  统一下单接口 返回值
 */
@Data
public class WechatPayPrePayResponse extends WechatPayResponse {
    // 公众账号ID
    @JacksonXmlProperty(localName = "appid")
    @JacksonXmlCData
    private String appId;

    // 微信支付商户号
    @JacksonXmlProperty(localName = "mch_id")
    @JacksonXmlCData
    private String mchId;

    // 设备号
    @JacksonXmlProperty(localName = "device_info")
    @JacksonXmlCData
    private String deviceInfo;

    // 随机字符串
    @JacksonXmlProperty(localName = "nonce_str")
    @JacksonXmlCData
    private String nonceStr;

    // 签名
    @JacksonXmlProperty(localName = "sign")
    @JacksonXmlCData
    private String sign;

    // 错误代码
    @JacksonXmlProperty(localName = "err_code")
    @JacksonXmlCData
    private String errCode;

    // 错误代码描述
    @JacksonXmlProperty(localName = "err_code_des")
    @JacksonXmlCData
    private String errCodeDes;

    // 以下字段 在return_code 和result_code都为SUCCESS的时候有返回
    // 交易类型 TradeType
    @JacksonXmlProperty(localName = "trade_type")
    @JacksonXmlCData
    private String tradeType;

    // 预支付交易会话标识  微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
    @JacksonXmlProperty(localName = "prepay_id")
    @JacksonXmlCData
    private String prepayId;

    // trade_type为NATIVE是有返回，可将该参数值生成二维码展示出来进行扫码支付
    @JacksonXmlProperty(localName = "code_url")
    @JacksonXmlCData
    private String codeUrl;

    @JacksonXmlProperty(localName = "sub_appid")
    @JacksonXmlCData
    private String subAppid;

    @JacksonXmlProperty(localName = "sub_mch_id")
    @JacksonXmlCData
    private String subMchId;

    public Map<String, String> buildAppInfo(String orderForm, String partnerKey) {
        SortedMap<String, String> appSignMap = new TreeMap<>();

        if (EnumOrderFrom.MINI_APP.name().equals(orderForm)) {// 为小程序生成
            appSignMap.put("appId", StringUtils.isBlank(this.subAppid) ? appId : subAppid);
            appSignMap.put("package", "prepay_id=" + this.prepayId);
            appSignMap.put("signType", "MD5");
            appSignMap.put("timeStamp", System.currentTimeMillis() / 1000 + "");
            appSignMap.put("nonceStr", RandomUtility.get32UUID());
        } else {// 为APP生成签名
            appSignMap.put("appid", StringUtils.isBlank(this.subAppid) ? appId : subAppid);
            appSignMap.put("partnerid", this.getMchId());
            appSignMap.put("package", "Sign=WXPay");
            appSignMap.put("noncestr", RandomUtility.get32UUID());
            appSignMap.put("timestamp", System.currentTimeMillis() / 1000 + "");
            appSignMap.put("prepayid", this.prepayId);
        }

        String appSign = PayCommonUtil.createSign(appSignMap, partnerKey);
        appSignMap.put("sign", appSign);
        appSignMap.put("return_code", this.resultCode);
        appSignMap.put("return_msg", this.returnMsg);
        appSignMap.put("err_code", this.errCode);
        appSignMap.put("err_code_des", this.errCodeDes);

        if (EnumOrderFrom.H5_PAY.name().equals(orderForm)) {
            appSignMap.put("code_url", this.codeUrl);
        }
        return appSignMap;
    }

}
