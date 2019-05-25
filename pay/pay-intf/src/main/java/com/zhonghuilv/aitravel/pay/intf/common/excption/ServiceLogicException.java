package com.zhonghuilv.aitravel.pay.intf.common.excption;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class ServiceLogicException extends CustomRuntimeException {

    public ServiceLogicException(Long errcode, String message) {
        super(errcode, message);
    }

    public ServiceLogicException(String message) {
        super(message);
    }
}
