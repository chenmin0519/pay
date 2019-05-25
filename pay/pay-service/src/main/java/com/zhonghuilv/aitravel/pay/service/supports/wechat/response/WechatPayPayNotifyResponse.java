package com.zhonghuilv.aitravel.pay.service.supports.wechat.response;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.Date;

/**
 * 微信支付成功，异步通知接口
 */
public class WechatPayPayNotifyResponse extends WechatPayResponse {
    @JacksonXmlProperty(localName = "appid")
	@JacksonXmlCData

    private String appId;

    @JacksonXmlProperty(localName = "mch_id")
	@JacksonXmlCData

    private String mchId;

    @JacksonXmlProperty(localName = "device_info")
	@JacksonXmlCData

    private String deviceInfo;

    @JacksonXmlProperty(localName = "nonce_str")
	@JacksonXmlCData

    private String nonceStr;

    @JacksonXmlProperty(localName = "sign")
	@JacksonXmlCData

    private String sign;

    @JacksonXmlProperty(localName = "err_code")
	@JacksonXmlCData

    private String errCode;

    @JacksonXmlProperty(localName = "err_code_des")
	@JacksonXmlCData

    private String errCodeDes;

    // 客户 微信 openid
    @JacksonXmlProperty(localName = "openid")
	@JacksonXmlCData

    private String openId;

    // 是否关注公众账号
    @JacksonXmlProperty(localName = "is_subscribe")
	@JacksonXmlCData

    private String isSubscribe;

    @JacksonXmlProperty(localName = "trade_type")
	@JacksonXmlCData
    private String tradeType;

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

    private Date timeEnd;


//    @JacksonXmlProperty(localName = "trade_state_desc")
	@JacksonXmlCData


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrCodeDes() {
        return errCodeDes;
    }

    public void setErrCodeDes(String errCodeDes) {
        this.errCodeDes = errCodeDes;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
    }

}
