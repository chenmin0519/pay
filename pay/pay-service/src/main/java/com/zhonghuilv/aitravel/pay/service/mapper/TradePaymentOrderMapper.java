package com.zhonghuilv.aitravel.pay.service.mapper;

import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.service.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradePaymentOrderMapper extends CommonMapper<TradePaymentOrder> {

    TradePaymentOrder loadByMerchantNoAndOrder();
}