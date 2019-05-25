package com.zhonghuilv.aitravel.pay.service.service;

import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */

public interface TradePaymentOrderService {

    TradePaymentOrder loadByMerchantNoAndOrder(String orderNo);

    boolean updateSelective(TradePaymentOrder order);
}
