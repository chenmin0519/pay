package com.zhonghuilv.aitravel.pay.service.supports.wechat.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 微信对账接口 请求参数
 */
@Data
@Builder
public class WechatBillCheckModel extends WechatPayModel{

    // 对账单日期
    @JacksonXmlProperty(localName = "bill_date")
    @NotNull
    private String billDate;

    // 账单类型
    @NotNull
    @JacksonXmlProperty(localName = "bill_type")
    private String billType;

    // 压缩账单
    @JacksonXmlProperty(localName = "tar_type")
    private String tarType;

}
