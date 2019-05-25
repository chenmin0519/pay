package com.zhonghuilv.aitravel.pay.service.supports.wechat.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.WechatPayTradeStatus;
import lombok.Data;

/**
 * 微信支付查询  返回值
 */
@Data
public class WechatPayQueryResponse extends WechatPayResponse {
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
    // 客户 微信 openid
    @JacksonXmlProperty(localName = "openid")
    @JacksonXmlCData
    private String openId;

    // 是否关注公众账号
    @JacksonXmlProperty(localName = "is_subscribe")
    @JacksonXmlCData
    private String isSubscribe;

    // 交易方式  TradeTypeCode
    @JacksonXmlProperty(localName = "trade_type")
    @JacksonXmlCData
    private String tradeType;

    // 交易状态   WechatPayTradeStatus，   WechatPayClient.convert中 需要加入WechatPayTradeStatus判断
    @JacksonXmlProperty(localName = "trade_state")
    @JacksonXmlCData
    private WechatPayTradeStatus tradeState;

    //付款银行	银行类型，采用字符串类型的银行标识
    @JacksonXmlProperty(localName = "bank_type")
    @JacksonXmlCData
    private String bankType;

    // 总金额  单位为分
    @JacksonXmlProperty(localName = "total_fee")
    @JacksonXmlCData
    private String totalFee;

    // 货币种类  默认人民币，CNY
    @JacksonXmlProperty(localName = "fee_type")
    @JacksonXmlCData
    private String feeType;

    // 微信支付订单号，微信内部订单号
    @JacksonXmlProperty(localName = "transaction_id")
    @JacksonXmlCData
    private String transactionId;

    // 客户订单号
    @JacksonXmlProperty(localName = "out_trade_no")
    @JacksonXmlCData
    private String outTradeNo;

    // 附加数据， 统一下单时候 传过来的 再原样传回去
    @JacksonXmlProperty(localName = "attach")
    @JacksonXmlCData
    private String attach;

    // 支付完成时间  格式为yyyyMMddHHmmss
    @JacksonXmlProperty(localName = "time_end")
    @JacksonXmlCData
    private String timeEnd;

    //  交易状态描述
    @JacksonXmlProperty(localName = "trade_state_desc")
    @JacksonXmlCData
    private String tradeStateDesc;

    //现金支付金额
    @JacksonXmlProperty(localName = "cash_fee")
    @JacksonXmlCData
    private String cashFee;

}
