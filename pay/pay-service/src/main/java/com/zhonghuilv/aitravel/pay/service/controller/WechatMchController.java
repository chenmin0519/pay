package com.zhonghuilv.aitravel.pay.service.controller;

import com.zhonghuilv.aitravel.common.excption.DataNotAvailableException;
import com.zhonghuilv.aitravel.pay.intf.clients.WechatMchClient;
import com.zhonghuilv.aitravel.pay.intf.pojo.WechatMch;
import com.zhonghuilv.aitravel.pay.service.mapper.WechatMchMapper;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@RestController
@RequestMapping("/wechat_mch")
@Api(tags = "微信商户号")
@Slf4j
public class WechatMchController extends BasicController<WechatMch> implements WechatMchClient {

    WechatMchMapper wechatMchMapper;

    @Autowired
    public WechatMchController(WechatMchMapper wechatMchMapper) {
        super(wechatMchMapper);
        this.wechatMchMapper = wechatMchMapper;
    }

    @ApiOperation(value = "添加服务商特约商务号 当前只有一个服务商pid可以不穿",
            extensions = @Extension(
                    name = "request", properties = @ExtensionProperty(name = "includes", value = "mchId,pid")))
    @RequestMapping(value = "/child", method = RequestMethod.POST)
    @Override
    public WechatMch addChild(@RequestBody @Validated WechatMch wechatMch) {

        if (Objects.isNull(wechatMch.getPid())) {

            WechatMch mch = new WechatMch();
            mch.setPid(-1L);
            List<WechatMch> select = wechatMchMapper.select(mch);
            if (CollectionUtils.isEmpty(select)) {
                throw new DataNotAvailableException("无服务商 请先添加服务商商户号");
            } else {
                select.stream().findFirst().map(WechatMch::getId).ifPresent(wechatMch::setPid);
                wechatMchMapper.insertUseGeneratedKeys(wechatMch);
            }
        }
        return wechatMch;
    }

    /**
     * 小程序绑定商务号
     *
     * @param id    商户号id
     * @param appid 小程序appid
     * @return
     */
    @ApiOperation("小程序绑定商务号 如果已经绑定了就修改")
    @Override
    @RequestMapping(value = "/{id}/_bind_app/{appid}", method = RequestMethod.POST)
    public boolean bindApp(@PathVariable("id") @ApiParam("商务号id") Long id,
                           @PathVariable("appid") @ApiParam("小程序或微信app的appid") String appid) {

        if (wechatMchMapper.appidBindCount(appid) != 0) {
            wechatMchMapper.unbindApp(id, appid);
//            throw new ParameterNotValidException("小程序只能绑定一个商务号");
        }
        return wechatMchMapper.bindApp(id, appid) == 1;
    }

    /**
     * 小程序解绑商务号
     *
     * @param id    商户号id
     * @param appid 小程序appid
     * @return
     */
    @ApiOperation("小程序解绑商务号")
    @Override
    @RequestMapping(value = "/{id}/_unbind_app/{appid}", method = RequestMethod.POST)
    public boolean unbindApp(@PathVariable("id") @ApiParam("商务号id") Long id,
                             @PathVariable("appid") @ApiParam("小程序或微信app的appid") String appid) {

        return wechatMchMapper.unbindApp(id, appid) == 1;
    }

}

