package com.zhonghuilv.aitravel.pay.service.supports.wechat.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 微信支付 返回结果  基础参数
 * <p/>
 * WechatPayClient.convert转换， 会处理data类型，WechatPayTradeStatus类型，
 */
@Data
public abstract class WechatPayResponse {
    // 业务结果  SUCCESS/FAIL   !!!!!!!!!!!!!!!
    @JacksonXmlProperty(localName = "result_code")
    @JacksonXmlCData
    protected String resultCode;

    //  return_code, return_msg 是所有返回结果的 基础。

    // SUCCESS/FAIL      此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    @JacksonXmlProperty(localName = "return_code")
    @JacksonXmlCData
    protected String returnCode;

    // 返回信息，如非空，为错误原因 如，签名失败 ,参数格式校验错误
    @JacksonXmlProperty(localName = "return_msg")
    @JacksonXmlCData
    protected String returnMsg;

    @ApiModelProperty("微信业务错误码")
    @JacksonXmlProperty(localName = "err_code")
    @JacksonXmlCData
    protected String errCode;

    @ApiModelProperty("结果信息描述")
    @JacksonXmlProperty(localName = "err_code_des")
    @JacksonXmlCData
    protected String errCodeDes;

    // 判断操作是否成功，即通信结果和业务结果均为 SUCCESS时候表示操作成功
    @JsonIgnore
    public boolean isSuccess() {
        return StringUtils.isNotBlank(resultCode) && ResultCode.SUCCESS.getValue().equalsIgnoreCase(resultCode);
    }

    @JsonIgnore
    public String errormsg() {
        if (ResultCode.SUCCESS.name().equals(returnCode)) {
            return errCodeDes;
        }
        return returnMsg;
    }


}
