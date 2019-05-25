package com.zhonghuilv.aitravel.pay.api.controller;

import com.zhonghuilv.aitravel.pay.intf.clients.TestClient;
import com.zhonghuilv.aitravel.pay.intf.common.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-23 2019-05-23
    * @Version:     1.0
    * @JDK:         10
    */
@RestController
@RequestMapping("/pay/test")
public class TestController {


    @Autowired
    TestClient testClient;

    @GetMapping("/timeout")
    public ApiResult<String> timeout() {
        return ApiResult.success(testClient.timeout(5000L));
    }

    @GetMapping("/timeout/local")
    public ApiResult<String> localtimeout(@RequestParam(value = "timeout", required = false, defaultValue = "5000")
                                                  Long timeout) throws InterruptedException {


        Thread.sleep(timeout);
        return ApiResult.success("sleep " + timeout + "ms");
    }
}
