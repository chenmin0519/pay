package com.zhonghuilv.aitravel.common.constants;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface ApplicationConfig {

    /**
     * feifn 调用时传入userID的headName
     */
    String FEIGN_HEADER_USER_ID = "f-user-id";

    String FEIGN_HTTP_HEADER_DATA_AUTHORITIES = "x-data-authorities";

    Integer INT_ZERO = Integer.valueOf(0);

    Long LONG_ZERO = Long.valueOf(0L);

    String DEFAULT_CHARSET = "UTF-8";

    String STRING_EMPTY = "";

}
