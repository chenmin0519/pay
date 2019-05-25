package com.zhonghuilv.aitravel.pay.service.controller;

import com.zhonghuilv.aitravel.pay.intf.clients.IntegralHistoryClient;
import com.zhonghuilv.aitravel.pay.intf.pojo.IntegralHistory;
import com.zhonghuilv.aitravel.pay.service.mapper.IntegralHistoryMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@RestController
@RequestMapping("/integral_history")
@Api(value = "IntegralHistoryController", tags = "积分记录")
public class IntegralHistoryController extends BasicController<IntegralHistory> implements IntegralHistoryClient{

	 IntegralHistoryMapper integralHistoryMapper;

    @Autowired
    public IntegralHistoryController(IntegralHistoryMapper integralHistoryMapper) {
        super(integralHistoryMapper);
        this.integralHistoryMapper =integralHistoryMapper;
    }


}

