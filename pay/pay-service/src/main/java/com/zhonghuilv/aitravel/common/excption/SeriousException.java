package com.zhonghuilv.aitravel.common.excption;

/**
    * @Description: 严重的异常 （可能需要发邮件通知开发人员的异常）
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class SeriousException extends CustomRuntimeException {

    private String devloperEmail = null;

    private String devloperName = "";
    private static final Long ERRCODE_MIN = 6000_000L;

    public SeriousException(Long errcode, String message) {
        this(ERRCODE_MIN + errcode, message, "", "");
    }

    public SeriousException(String message) {
        this(ERRCODE_MIN, message);
    }

    public SeriousException(Long errcode, String message, String devloperEmail, String devloperName) {
        super(ERRCODE_MIN + errcode, message);
        this.devloperEmail = devloperEmail;
        this.devloperName = devloperName;
    }
}
