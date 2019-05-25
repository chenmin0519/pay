package com.zhonghuilv.aitravel.pay.service.pojo;

import com.zhonghuilv.aitravel.pay.service.exception.PayException;

public class ApiException extends PayException {
    int code;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
