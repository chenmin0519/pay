package com.zhonghuilv.aitravel.pay.intf.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Data
public class ParseDTO {

    @NotNull(message = "支付方式不能为空")
    private String payWay;
    
    private String appid;
    /**
     * 解析前的数据
     */
    private String sourse;

    /**
     * 解析后的参数
     */
    private Map<String, String> params;
}
