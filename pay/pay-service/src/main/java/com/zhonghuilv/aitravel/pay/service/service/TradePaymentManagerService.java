package com.zhonghuilv.aitravel.pay.service.service;

import com.zhonghuilv.aitravel.pay.intf.PayCallbackDTO;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface TradePaymentManagerService {

    /**
     * 第三方交易状态变更
     * @param payCallback
     */
    TradePaymentOrder tradeCallback(PayCallbackDTO payCallback);
}
