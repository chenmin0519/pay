package com.zhonghuilv.aitravel.common.excption;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class ServiceNotSupport extends CustomRuntimeException {

    private static final Long ERRCODE_MIN = 3000_000L;

    public ServiceNotSupport(Long errcode, String message) {
        super(ERRCODE_MIN + errcode, message);
    }

    public ServiceNotSupport(String message) {
        super(ERRCODE_MIN, message);
    }
}
