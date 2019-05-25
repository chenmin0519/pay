package com.zhonghuilv.aitravel.pay.service.service;

import com.zhonghuilv.aitravel.pay.intf.pojo.IntegralAccount;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface IntegralAccountService {

    /**
     * 冻结积分
     *
     * @param userId   用户id
     * @param scenicId 景区id
     * @param integral 积分
     * @return
     */
    IntegralAccount freeze(Long userId, Long scenicId, Integer integral);
}
