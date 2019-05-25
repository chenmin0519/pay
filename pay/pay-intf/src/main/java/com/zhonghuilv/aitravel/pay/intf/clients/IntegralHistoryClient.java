package com.zhonghuilv.aitravel.pay.intf.clients;

import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.pojo.IntegralHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@FeignClient(value = PayConstant.SERVICE_NAME)
@RequestMapping("/integral_history")
public interface IntegralHistoryClient extends BasicClient<IntegralHistory> {

}

