package com.zhonghuilv.aitravel.pay.service.service;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface BuildService {
    /**
     * 获取支付流水号
     **/
    String buildTrxNo();

    String buildRefundTrxNo();

    /**
     * 获取银行订单号
     **/
    String buildBankOrderNo();

    /**
     * 获取对账批次号
     **/
    String buildReconciliationNo();
}
