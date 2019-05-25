package com.zhonghuilv.aitravel.pay.intf;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 支付成功之后转换
 */
@Data
public class PayCallbackDTO {

    /**
     * appid
     */
    private String appid;

    /**
     * 支付平台
     */
    private String payWay;
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 业务号
     */
    private String bizNo;
    /**
     * 实收金额(单位:分)
     */
    private int receiptAmount;

    /**
     * 总金额
     */
    private int totalAmount;

    /**
     * 买家支付金额
     */
    private int buyerPayAmount;

    /**
     * 第三方流水号
     */
    private String bankTrxNo;

    /**
     * 平台流水号
     */
    private String platTrxNo;

    /**
     * 平台状态码
     */
    private String status;

    private String bankReturnMsg;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    private String remark;

}
