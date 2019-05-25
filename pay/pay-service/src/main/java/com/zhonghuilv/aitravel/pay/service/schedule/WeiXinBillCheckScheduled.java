package com.zhonghuilv.aitravel.pay.service.schedule;

import com.zhonghuilv.aitravel.pay.intf.enums.EnumWeiXinBillType;
import com.zhonghuilv.aitravel.pay.intf.enums.PayWayEnum;
import com.zhonghuilv.aitravel.pay.service.supports.PayPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class WeiXinBillCheckScheduled {

    @Autowired
    ApplicationContext applicationContext;

    @Scheduled(cron = "0 0 10 1/1 * ? ")
    @Async
    public void exe(){
        //TODO:先实现一个appid的获取，随后要循环获取
        PayPolicy payPolicy = applicationContext.getBean(PayWayEnum.WEIXIN.name() + PayPolicy.BEAN_SUFFIX, PayPolicy.class);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyymmdd"));
        String appid = "wx6e566a629857ce13";
        String bill = payPolicy.getBill(date, appid, EnumWeiXinBillType.ALL.name());
    }
}
