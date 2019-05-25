package com.zhonghuilv.aitravel.pay.service.exception;


import com.zhonghuilv.aitravel.common.excption.CustomRuntimeException;

/**
    * @Description: 支付异常
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class PayException extends CustomRuntimeException {

    /**
     * 数据库操作,insert返回0
     */
    public static final PayException DB_INSERT_RESULT_0 = new PayException(
            1L, "数据库操作,insert返回0");

    /**
     * 数据库操作,update返回0
     */
    public static final PayException DB_UPDATE_RESULT_0 = new PayException(
            2L, "数据库操作,update返回0");

    /**
     * 数据库操作,selectOne返回null
     */
    public static final PayException DB_SELECTONE_IS_NULL = new PayException(
            3L, "数据库操作,单行查询返回null");

    /**
     * 数据库操作,list返回null
     */
    public static final PayException DB_LIST_IS_NULL = new PayException(
            4L, "数据库操作,list返回null");

    /**
     * 生成序列异常时
     */
    public static final PayException DB_GET_SEQ_NEXT_VALUE_ERROR = new PayException(
            7L, "序列生成超时");

    /**
     * 生成支付信息异常
     *
     * @param message
     */
    public static final PayException TRADE_PARAM_ERROR = new PayException(8L, "交易参数出错");

    /**
     * 生成支付信息异常
     *
     * @param message
     */
    public static final PayException TRADE_TRX_NO_NOT_EXISTS = new PayException(9L, "流水不存在");

    public PayException(String message) {
        super(400_000L, message);
    }

    public PayException(Long code, String message) {
        super(400_000L + code, message);
    }
}
