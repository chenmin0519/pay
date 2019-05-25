package com.zhonghuilv.aitravel.pay.service.supports.wechat.exception;

import com.zhonghuilv.aitravel.pay.service.exception.PayException;

public class WechatPayException extends PayException {
    public WechatPayException(String message) {
        super(100L, message);
    }

    public WechatPayException(Long code, String message) {
        super(code, message);
    }
}
