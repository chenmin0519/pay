package com.zhonghuilv.aitravel.pay.intf.common.excption;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class ServiceUnrealized extends CustomRuntimeException {
    private static final Long ERRCODE_MIN = 4000_000L;

    public ServiceUnrealized(String message) {
        super(ERRCODE_MIN, message);
    }


    public ServiceUnrealized(Long errcode, String message) {
        super(ERRCODE_MIN + errcode, message);
    }
}
