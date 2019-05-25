package com.zhonghuilv.aitravel.pay.service.pojo;


import com.zhonghuilv.aitravel.pay.intf.enums.EnumPayType;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumTradeType;
import lombok.Data;

@Data
public class PayResponse extends ApiResponse {
    private String tradePayNo;
    private String payOrderNo;
    private EnumPayType payType;
    private EnumTradeType tradeType;
    // 如果是 扫码，则返回二维码内容，string类型， 如果是wap和app，则返回 支付凭据 map类型，转成json则为JsonObject
    private Object credential;

}
