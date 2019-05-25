package com.zhonghuilv.aitravel.pay.service.service.impl;

import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.service.mapper.TradePaymentOrderMapper;
import com.zhonghuilv.aitravel.pay.service.service.TradePaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Service
public class TradePaymentOrderServiceImpl implements TradePaymentOrderService {

    @Autowired
    TradePaymentOrderMapper tradePaymentOrderMapper;

    @Override
    public TradePaymentOrder loadByMerchantNoAndOrder(String orderNo) {
        TradePaymentOrder tradePaymentOrder = new TradePaymentOrder();
        tradePaymentOrder.setMerchantOrderNo(orderNo);
        return tradePaymentOrderMapper.selectOne(tradePaymentOrder);
    }

    @Override
    public boolean updateSelective(TradePaymentOrder order) {
        return tradePaymentOrderMapper.updateByPrimaryKeySelective(order) == 1;
    }
}
