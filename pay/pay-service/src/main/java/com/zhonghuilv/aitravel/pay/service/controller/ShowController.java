package com.zhonghuilv.aitravel.pay.service.controller;

import com.alibaba.fastjson.JSON;
import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.clients.TestClient;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.PayMessage;
import com.zhonghuilv.aitravel.pay.service.service.BuildService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.binding.BinderAwareChannelResolver;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@RequestMapping("/show")
@RestController
@EnableBinding
@Log4j2
public class ShowController implements TestClient {

    public static String cache;

    @Autowired
    BuildService buildService;

    @Autowired
    private BinderAwareChannelResolver resolver;

    @GetMapping("/{username}")
    @ApiOperation("api")
    public String username(@PathVariable("username") @ApiParam("用户名称") String userName) {

        return "userName is :" + userName;
    }

    @PostMapping("/result")
    @ApiOperation("api")
    public ModelAndView result(ModelMap modelMap) {

        return new ModelAndView("result", modelMap);
    }


    @GetMapping("/page")
    public ModelAndView page(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("pay");
    }

    @PostMapping("/get_form")
    public String get_form(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (cache != null) {
            return cache;
        }
        return "error  ";
    }

    @Autowired
    AmqpTemplate amqpTemplate;

    @Autowired
    BeanFactory beanFactory;

    @GetMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("发送mq消息")
    public PayMessage sendMsg(@RequestParam(required = false, defaultValue = "") String qu,
                              @RequestParam(required = false, defaultValue = "orderNo") String orderNo,
                              @RequestParam(required = false, defaultValue = "WEIXIN") String payWayCode,
                              @RequestParam(required = false, defaultValue = "SUCCESS") String status) {

        PayMessage message = new PayMessage(orderNo, payWayCode, status);
        amqpTemplate.convertAndSend(PayConstant.MQ_PREFIX + qu, JSON.toJSONString(message));
        return message;
    }

    @GetMapping("/send_steam")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("发送mq消息")
    public Message<PayMessage> sendStreamMsg(@RequestParam(required = false, defaultValue = "") String qu,
                                             @RequestParam(required = false, defaultValue = "orderNo") String orderNo,
                                             @RequestParam(required = false, defaultValue = "WEIXIN") String payWayCode,
                                             @RequestParam(required = false, defaultValue = "SUCCESS") String status) {


        Message<PayMessage> build = MessageBuilder
                .withPayload(new PayMessage(orderNo, payWayCode, status)).build();
        resolver.resolveDestination(PayConstant.MQ_PREFIX + qu)
                .send(build);

        return build;
    }

    @GetMapping("/aa")
    @ApiOperation("测试AA")
    public String aa() {
        return "aa exec";
    }


    @GetMapping("/timeout")
    @ApiOperation("sleep")
    @Override
    public String timeout(@RequestParam(value = "timeout", defaultValue = "5000") Long timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "InterruptedException:" + e.getLocalizedMessage();
        }
        return "sleep " + timeout + "ms";
    }
}
