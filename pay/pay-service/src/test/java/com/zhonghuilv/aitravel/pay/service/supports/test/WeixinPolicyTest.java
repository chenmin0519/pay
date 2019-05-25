package com.zhonghuilv.aitravel.pay.service.supports.test;


import com.zhonghuilv.aitravel.PayServiceApplication;
import com.zhonghuilv.aitravel.pay.intf.enums.PayWayEnum;
import com.zhonghuilv.aitravel.pay.service.supports.PayPolicy;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PayServiceApplication.class)
@ActiveProfiles("dev")
@Log4j2
public class WeixinPolicyTest {

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test(){
        PayPolicy payPolicy = applicationContext.getBean(PayWayEnum.WEIXIN.name() + PayPolicy.BEAN_SUFFIX, PayPolicy.class);
        payPolicy.getBill("wx6e566a629857ce13","20180803","ALL");
    }

}
