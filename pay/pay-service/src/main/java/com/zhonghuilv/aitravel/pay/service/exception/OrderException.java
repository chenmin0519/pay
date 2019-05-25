package com.zhonghuilv.aitravel.pay.service.exception;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class OrderException extends PayException {

    public static final OrderException ACOUNT_NOT_EXISTE = new OrderException(12L, "订单不存在");
    public static final OrderException AMOUNT_NOT_MATCH = new OrderException(10L, "订单金额不匹配");
    public static final OrderException ORDER_CLOSED = new OrderException(11L, "订单已处理");

    public OrderException(String message) {
        super(message);
    }

    public OrderException(Long code, String message) {
        super(code, message);
    }
}
