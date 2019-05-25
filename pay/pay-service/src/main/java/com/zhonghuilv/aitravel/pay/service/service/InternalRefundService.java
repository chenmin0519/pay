package com.zhonghuilv.aitravel.pay.service.service;


import com.zhonghuilv.aitravel.pay.intf.pojo.PayChannel;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.service.pojo.RefundResponse;

/**
    * @Description: 订单退款
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface InternalRefundService {



    String BEAN_NAME_SUFIX = "RefoundService";

    /**
     * 实际退款接口，  如果退款失败，一定要抛出异常，在RefundService中如果有异常则返回 业务方。
     * 会将第三方退款单号设置到refundOrder中
     *
     * @param refundOrder
     * @return 是否成功
     */
    boolean refund(RefundRecord refundOrder);

    /**
     * 异步通知时候使用，  目前支付宝调用此方法
     *
     * @return
     */
    RefundResponse parse(PayChannel channel, RefundRecord refundOrder, String notify);
}
