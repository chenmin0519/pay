package com.zhonghuilv.aitravel.common.util;

import com.zhonghuilv.aitravel.common.excption.CustomRuntimeException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
    * @Description: 异步调用工具类
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Slf4j
public class FutureUtilly {

    /**
     * 预计需要执行的时间（秒）
     */
    public static final Integer DEFAULT_ESTIMATED_EXECUTION_SECONDS = 5;

    /**
     * @param executorService 当前
     */
    public static void end(ExecutorService executorService) {
        end(executorService, DEFAULT_ESTIMATED_EXECUTION_SECONDS);
    }

    /**
     * @param executorService           当前
     * @param estimatedExecutionSeconds 预计需要执行的时间
     */
    public static void end(ExecutorService executorService, Integer estimatedExecutionSeconds) {

        long ex = System.currentTimeMillis();
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(estimatedExecutionSeconds, TimeUnit.SECONDS)) {
                ex = System.currentTimeMillis() - ex;
                log.warn("任务执行时间超过预计时间:estimated:{} ms ,real:{}ms",
                        estimatedExecutionSeconds * 100, ex);
            }
        } catch (InterruptedException e) {
            throw new CustomRuntimeException("线程中断:" + e.getMessage());
        }
    }
}
