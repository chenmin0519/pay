package com.zhonghuilv.aitravel.pay.intf.util;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Data
@JacksonXmlRootElement(localName = "xml")
public class WechatPayParams implements Serializable {

    @ApiModelProperty(hidden = true,value = "微信分配的APPID")
    @JacksonXmlCData
    protected String appid;

    @ApiModelProperty(hidden = true, value = "微信支付分配的商户号")
    @JacksonXmlCData
    @JacksonXmlProperty(localName = "mch_id")
    protected String mchId;

    @ApiModelProperty(hidden = true, value = "随机字符串")
    @JacksonXmlCData
    @JacksonXmlProperty(localName = "nonce_str")
    protected String nonceStr;

    @ApiModelProperty(hidden = true, value = "签名")
    @JacksonXmlCData
    protected String sign;

    @ApiModelProperty(hidden = true, value = "签名类型")
    @JacksonXmlCData
    @JacksonXmlProperty(localName = "sign_type")
    protected String signType;

}
