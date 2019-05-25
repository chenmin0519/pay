package com.zhonghuilv.aitravel.pay.service.controller;

import com.zhonghuilv.aitravel.pay.intf.pojo.IntegralAccount;
import com.zhonghuilv.aitravel.pay.service.mapper.IntegralAccountMapper;
import com.zhonghuilv.aitravel.pay.service.service.IntegralAccountService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@RestController
@RequestMapping("/integral_account")
@Api(value = "IntegralAccountController", tags = "积分账户")
public class IntegralAccountController extends BasicController<IntegralAccount> {

     IntegralAccountMapper integralAccountMapper;

    @Autowired
    IntegralAccountService integralAccountService;

    @Autowired
    public IntegralAccountController(IntegralAccountMapper integralAccountMapper) {
        super(integralAccountMapper);
        this.integralAccountMapper = integralAccountMapper;
    }

    @PatchMapping("/_freeze")
    public IntegralAccount freezeIntegral(@RequestParam("userId") Long userId,
                                          @RequestParam("scenicId") Long scenicId,
                                          @RequestParam("integral") Integer integral) {
        return integralAccountService.freeze(userId, scenicId, integral);
    }

}

