package com.zhonghuilv.aitravel.pay.service.supports.wechat.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 微信支付订单查询 接口参数
 * <p/>
 * 两个参数 至少传一个
 */
@Data
@Builder
public class WechatPayQueryModel extends WechatPayModel {
    // 商户订单号：商户系统内部的订单号，当没提供transaction_id时需要传这个。
    @JacksonXmlProperty(localName = "out_trade_no")
	@JacksonXmlCData
    private String outTradeNo;

    // 微信订单号，优先使用
    @JacksonXmlProperty(localName = "transaction_id")
	@JacksonXmlCData
    private String transactionId;

}
