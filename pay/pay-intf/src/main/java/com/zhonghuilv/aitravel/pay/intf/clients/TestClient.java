package com.zhonghuilv.aitravel.pay.intf.clients;

import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@FeignClient(value = PayConstant.SERVICE_NAME)
@RequestMapping("/show")
public interface TestClient {
    @GetMapping("/timeout")
    @ApiOperation("sleep")
    String timeout(@RequestParam(value = "timeout", defaultValue = "5000") Long timeout);
}
