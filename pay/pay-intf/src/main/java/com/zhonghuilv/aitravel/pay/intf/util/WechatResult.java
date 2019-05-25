package com.zhonghuilv.aitravel.pay.intf.util;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.io.Serializable;

/**
 * 微信支付返回对象父类
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class WechatResult implements Serializable {

    @JacksonXmlProperty(localName = "return_code")
    @JacksonXmlCData
    protected String returnCode;

    @JacksonXmlProperty(localName = "return_msg")
    @JacksonXmlCData
    protected String returnMsg;


}
