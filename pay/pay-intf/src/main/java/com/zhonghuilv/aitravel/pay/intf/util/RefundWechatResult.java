package com.zhonghuilv.aitravel.pay.intf.util;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 微信支付返回对象父类
 * Create By zhengjing on 2018/7/3 17:47
 */
@Data
@JacksonXmlRootElement(localName = "xml")
public class RefundWechatResult extends WechatResult {

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "err_code")
    @JacksonXmlCData
    protected String errCode;

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "err_code_des")
    @JacksonXmlCData
    protected String errCodeDes;


    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "appid")
    @JacksonXmlCData
    protected String appid;


    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "mch_id")
    @JacksonXmlCData
    protected String mchId;

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "sub_appid")
    @JacksonXmlCData
    protected String subAppid;

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "sub_mch_id")
    @JacksonXmlCData
    protected String subMchId;

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "nonce_str")
    @JacksonXmlCData
    protected String nonceStr;

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "sign")
    @JacksonXmlCData
    protected String sign;

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "transaction_id")
    @JacksonXmlCData
    protected String transactionId;

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "out_trade_no")
    @JacksonXmlCData
    protected String outTradeNo;


    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "out_refund_no")
    @JacksonXmlCData
    protected String outRefundNo;


    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "total_fee")
    @JacksonXmlCData
    protected Integer totalFee;

    @ApiModelProperty("")
    @JacksonXmlProperty(localName = "refund_fee")
    @JacksonXmlCData
    protected Integer refundFee;

}
