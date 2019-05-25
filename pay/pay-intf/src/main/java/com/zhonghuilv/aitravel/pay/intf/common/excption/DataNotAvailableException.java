package com.zhonghuilv.aitravel.pay.intf.common.excption;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class DataNotAvailableException extends CustomRuntimeException {

    private static final Long ERRCODE_MIN = 7000_000L;

    public DataNotAvailableException(Long errcode, String message) {
        super(ERRCODE_MIN + errcode, message);
    }

    public DataNotAvailableException(String message) {
        super(ERRCODE_MIN, message);
    }
}
