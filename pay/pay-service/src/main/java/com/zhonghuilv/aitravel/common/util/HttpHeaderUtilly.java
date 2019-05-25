package com.zhonghuilv.aitravel.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class HttpHeaderUtilly {

    private HttpHeaderUtilly(){}
    /**
     * 获取当前请求头信息
     *
     * @param headerName
     * @return
     */
    public static String getHeaderValue(String headerName) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest().getHeader(headerName);
        }
        return null;
    }

    /**
     * 获取当前请求头信息
     *
     * @param headerName
     * @return
     */
    public static String getHeaderValueOrDefault(String headerName, String defaultValue) {
        String result = getHeaderValue(headerName);
        return StringUtils.isBlank(result) ? defaultValue : result;
    }
}
