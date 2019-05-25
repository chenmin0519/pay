package com.zhonghuilv.aitravel.pay.api.policy;

import com.zhonghuilv.aitravel.pay.intf.enums.PayWayEnum;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-23 2019-05-23
    * @Version:     1.0
    * @JDK:         10
    */
@Slf4j
public class AlipayParsePolicy implements ParsePolicy {

    @Override
    public boolean support(String plat) {
        return PayWayEnum.ALIPAY.name().equalsIgnoreCase(plat);
    }

    @Override
    public ParseDTO parse(HttpServletRequest request) {

        //获取支付宝POST过来反馈信息
        ParseDTO parseDTO = new ParseDTO();
        parseDTO.setPayWay(PayWayEnum.ALIPAY.name());
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        requestParams.forEach((key, values) -> {
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(key, valueStr);
        });
        parseDTO.setParams(params);
        parseDTO.setSourse(params.toString());
        log.debug("解析支付宝参数成功:" + params.toString());
        return parseDTO;
    }
}
