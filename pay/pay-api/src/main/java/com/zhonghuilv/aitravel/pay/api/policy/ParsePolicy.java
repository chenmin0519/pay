package com.zhonghuilv.aitravel.pay.api.policy;

import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;

import javax.servlet.http.HttpServletRequest;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-23 2019-05-23
    * @Version:     1.0
    * @JDK:         10
    */
public interface ParsePolicy {

    boolean support(String plat);

    ParseDTO parse(HttpServletRequest request);
}
