package com.zhonghuilv.aitravel.pay.service.supports.wechat.service;

import com.zhonghuilv.aitravel.common.excption.ServiceNotSupport;
import com.zhonghuilv.aitravel.pay.intf.pojo.PayChannel;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.service.pojo.RefundResponse;
import com.zhonghuilv.aitravel.pay.service.service.InternalRefundService;
import com.zhonghuilv.aitravel.pay.service.supports.WeixinPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Service("WEIXIN" + InternalRefundService.BEAN_NAME_SUFIX)
public class WechatRefoundService implements InternalRefundService {

    @Autowired
    WeixinPolicy weixinPolicy;

    /**
     * 实际退款接口，  如果退款失败，一定要抛出异常，在RefundService中如果有异常则返回 业务方。
     *
     * @param refundOrder
     * @return
     */
    @Override
    public boolean refund(RefundRecord refundOrder) {

        return weixinPolicy.refund(refundOrder);
    }

    /**
     * 异步通知时候使用，  目前支付宝调用此方法
     *
     * @param channel
     * @param refundOrder
     * @param notify
     * @return
     */
    @Override
    public RefundResponse parse(PayChannel channel, RefundRecord refundOrder, String notify) {

        throw new ServiceNotSupport("not impl");
    }
}
