package com.zhonghuilv.aitravel.pay.service.pojo;

import com.zhonghuilv.aitravel.pay.intf.enums.EnumPayType;
import lombok.Data;


/**
 * 支付回调内容
 */
@Data
public class PayNotifyResponse extends ApiResponse {
    private EnumPayType payType;
    private String extra;
    private int payAmount;
    private String tradePayNo;
    private String payOrderNo;
    private String status; // ResultCode：SUCCESS，FAIL
    private String payTime; // 支付时间， yyyy-MM-dd HH:mm:ss

}
