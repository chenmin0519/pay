package com.zhonghuilv.aitravel.pay.api.controller;

import com.zhonghuilv.aitravel.pay.api.policy.AlipayParsePolicy;
import com.zhonghuilv.aitravel.pay.api.policy.ParsePolicy;
import com.zhonghuilv.aitravel.pay.api.policy.WechatParsePolicy;
import com.zhonghuilv.aitravel.pay.intf.clients.PayCoreClient;
import com.zhonghuilv.aitravel.pay.intf.common.excption.ParameterNotValidException;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-24 2019-05-24
    * @Version:     1.0
    * @JDK:         10
    */
@RestController
@RequestMapping("/pay/notify")
@Slf4j
public class NotifyController {

    private List<ParsePolicy> parsePolicies = new ArrayList<>();

    @PostConstruct
    public void init() {
        parsePolicies.add(new AlipayParsePolicy());
        parsePolicies.add(new WechatParsePolicy());
    }

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    PayCoreClient payCoreClient;

    @PostMapping("/{payWay}/{appid}")
    public String notify(@PathVariable("payWay") String payWay,
                         @PathVariable(value = "appid", required = false) String appid,
                         HttpServletRequest request) {

        log.debug("支付回调:{},{}", payWay, appid);
        ParseDTO parseDTO = null;
        for (ParsePolicy policy : parsePolicies) {
            if (policy.support(payWay)) {
                parseDTO = policy.parse(request);
            }
        }
        if (parseDTO == null) {
            throw new ParameterNotValidException("解析参数为空");
        }
        parseDTO.setAppid(appid);
        return payCoreClient.payCallback(parseDTO);
    }

    @GetMapping("/time")
    public LocalDateTime time() {

        return LocalDateTime.now();
    }

}
