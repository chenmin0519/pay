package com.zhonghuilv.aitravel.pay.service.util;

import com.zhonghuilv.aitravel.pay.intf.PayParams;
import com.zhonghuilv.aitravel.pay.intf.enums.TradeStatusEnum;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentRecord;
import com.zhonghuilv.aitravel.pay.service.exception.PayException;

/**
 * 封装 工具类
 * 将传过来的参数对象 转换成订单和流水
 */
public class SealUtil {


    private SealUtil() {
        throw new IllegalStateException("Utility class");
    }
    /**
     * 支付订单实体封装
     *
     * @param payParams
     * @return
     */
    public static TradePaymentOrder sealOrder(PayParams payParams) {

        TradePaymentOrder order = new TradePaymentOrder();
        order.setVersion(1);
        order.setAppId(payParams.getAppid());
        order.setProductName(payParams.getProductName());//商品名称
        order.setMerchantOrderNo(payParams.getOrderNo());//订单号
        //订单来源 应用Id

        order.setOrderFrom(payParams.getOrderFrom());
        order.setBizCode(payParams.getBizCode());
        int orderPrice = payParams.getOrderPrice();
        if (orderPrice <= 0) {
            throw PayException.TRADE_PARAM_ERROR;
        }

        order.setOrderAmount(orderPrice);//订单金额

        order.setOrderTime(payParams.getOrderTime());//下单时间
        order.setOrderIp(payParams.getOrderIp());//下单IP

        order.setExpireTime(payParams.getOrderTime().plusMinutes(payParams.getOrderPeriod()));//订单过期时间
        order.setPayWayCode(payParams.getPayWayCode());//支付通道编码
        order.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());//订单状态 等待支付
        order.setProductId(payParams.getProductId());
        return order;
    }

    public static TradePaymentOrder sealUpdateOrder(PayParams payParams, TradePaymentOrder order) {

        order.setProductId(payParams.getProductId());
        order.setVersion(order.getVersion() + 1);
        order.setProductName(payParams.getProductName());//商品名称
        order.setMerchantOrderNo(payParams.getOrderNo());//订单号

        //订单来源 应用Id
        order.setOrderFrom(payParams.getOrderFrom());

        order.setBizCode(payParams.getBizCode());
        Integer orderPrice = payParams.getOrderPrice();
        if (orderPrice == null || orderPrice.intValue() <= 0) {
            throw PayException.TRADE_PARAM_ERROR;
        }

        order.setOrderAmount(orderPrice);//订单金额
        order.setOrderTime(payParams.getOrderTime());//下单时间
        order.setOrderIp(payParams.getOrderIp());//下单IP
        order.setOrderPeriod(payParams.getOrderPeriod());//订单有效期
        order.setExpireTime(payParams.getOrderTime().plusMinutes(payParams.getOrderPeriod()));//订单过期时间
        order.setPayWayCode(payParams.getPayWayCode());//支付通道编码

        return order;
    }

    /**
     * 封装 支付记录
     *
     * @return
     */
    public static TradePaymentRecord sealRecord(TradePaymentOrder order, PayParams payParams, String trxNo) {
        TradePaymentRecord record = new TradePaymentRecord();
        Integer orderAmount = order.getOrderAmount();

        record.setTrxNo(trxNo);// 支付流水
        record.setVersion(1);
        record.setIsRefund(Boolean.FALSE.toString());

        record.setProductName(order.getProductName());
        record.setMerchantOrderNo(order.getMerchantOrderNo());
        record.setOrderAmount(orderAmount);
        record.setOrderIp(order.getOrderIp());

        record.setPayWayCode(payParams.getPayWayCode());//支付通道编码

        record.setPayerUserId(order.getCreator());
//        record.setTrxType(order.getTrxType());//交易类型 消费
        record.setOrderFrom(order.getOrderFrom());//订单来源 用户消费
        record.setStatus(TradeStatusEnum.WAITING_PAYMENT.name());// 订单状态 等待付款

        return record;
    }


}
