package com.zhonghuilv.aitravel.pay.service.config;

import com.zhonghuilv.aitravel.common.constants.ApplicationConfig;
import com.zhonghuilv.aitravel.common.util.ComonPOUtil;
import com.zhonghuilv.aitravel.common.util.MainPOUtil;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.dto.BatchUpdateDTO;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.ComonPo;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.MainPO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;

/**
    * @Description: 在新增修改时 自动添加修改 新增信息
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Aspect
@Slf4j
@Component
public class MapperAspect {

    @Pointcut("execution(* com.zhonghuilv.aitravel..service.mapper.*.insert*(..)) || execution(* com.zhonghuilv" +
            ".aitravel..service.mapper.*" +
            ".update*(..))")
    public void log() {
    }

    private static final String BATCH_METHOD = "updateByExampleSelective";

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();
        String methodName = joinPoint.getSignature().getName();

        if (args != null && args.length > 0) {
            if (args[0] instanceof MainPO) {
                MainPO po = (MainPO) args[0];
                Long userId = getUserId();
                if (methodName.startsWith("insert")) {
                    MainPOUtil.initAdd(po, userId);
                } else if (methodName.startsWith("update")) {
                    MainPOUtil.initUpdate(po, userId);
                }
            } else if (args[0] instanceof ComonPo) {
                ComonPo po = (ComonPo) args[0];
                Long userId = getUserId();
                if (methodName.startsWith("insert")) {
                    ComonPOUtil.initAdd(po, userId);
                } else if (methodName.startsWith("update")) {
                    ComonPOUtil.initUpdate(po, userId);
                }
            } else if (args[0] instanceof BatchUpdateDTO<?> && BATCH_METHOD.equals(methodName)) {
                BatchUpdateDTO<?> batch = (BatchUpdateDTO<?>) args[0];
                if (batch.getModel() instanceof MainPO) {
                    MainPOUtil.initAdd((MainPO) batch.getModel(), getUserId());
                }
            }
        }

    }

    @PostConstruct
    public void beanBuildSuccess(){
        log.info("MapperAspect build success");
    }
    private Long getUserId() {

        String headerValue = getHeaderValue(ApplicationConfig.FEIGN_HEADER_USER_ID);
        if (StringUtils.isNotBlank(headerValue)) {
            return Long.valueOf(headerValue);
        }
        return -1L;
    }

    public String getHeaderValue(String headerName) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) requestAttributes).getRequest().getHeader(headerName);
        }
        return null;
    }

}
