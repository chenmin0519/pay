package com.zhonghuilv.aitravel.pay.intf.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayMessage {

    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 支付方式
     */
    private String payWayCode;


    /**
     * 处理状态（参考TradeStatusEnum）
     */
    private String status;

}
