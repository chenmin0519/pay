package com.zhonghuilv.aitravel.pay.intf.clients;

import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.pojo.WechatMch;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
    * @Description: 微信商户号
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@FeignClient(value = PayConstant.SERVICE_NAME)
@RequestMapping("/wechat_mch")
public interface WechatMchClient extends BasicClient<WechatMch> {

    @ApiOperation(value = "添加服务商特约商务号 当前只有一个服务商pid可以不穿",
            extensions = @Extension(
                    name = "request", properties = @ExtensionProperty(name = "includes", value = "mchId,pid")))
    @RequestMapping(value = "/child", method = RequestMethod.POST)
    WechatMch addChild(@RequestBody WechatMch wechatMch);

    @ApiOperation("小程序绑定商务号")
    @RequestMapping(value = "/{id}/_bind_app/{appid}", method = RequestMethod.POST)
    boolean bindApp(@PathVariable("id") @ApiParam("商务号id") Long id,
                    @PathVariable("appid") @ApiParam("小程序或微信app的appid") String appid);

    @ApiOperation("小程序解绑商户号")
    @RequestMapping(value = "/{id}/_unbind_app/{appid}", method = RequestMethod.POST)
    boolean unbindApp(@PathVariable("id") @ApiParam("商务号id") Long id,
                      @PathVariable("appid") @ApiParam("小程序或微信app的appid") String appid);
}

