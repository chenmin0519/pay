package com.zhonghuilv.aitravel.common.excption;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class ParameterNotValidException extends CustomRuntimeException {

    /**
     * 参数校验异常的基数
     */
    private static final Long ERRCODE_MIN = 1000_000L;

    public ParameterNotValidException(Long errcode, String message) {
        super(1000_000L + errcode, message);
    }

    public ParameterNotValidException(String message) {
        super(1000_000L, message);
    }
}
