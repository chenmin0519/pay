package com.zhonghuilv.aitravel.pay.api.policy;

import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.common.excption.ParameterNotValidException;
import com.zhonghuilv.aitravel.pay.intf.common.excption.ServiceUnrealized;
import com.zhonghuilv.aitravel.pay.intf.enums.PayWayEnum;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-23 2019-05-23
    * @Version:     1.0
    * @JDK:         10
    */
@Slf4j
public class WechatParsePolicy implements ParsePolicy {

    @Override
    public boolean support(String plat) {
        return PayWayEnum.WEIXIN.name().equalsIgnoreCase(plat);
    }

    @Override
    public ParseDTO parse(HttpServletRequest request) {

        ParseDTO parseDTO = new ParseDTO();
        parseDTO.setPayWay(PayWayEnum.WEIXIN.name());
        Map<String, String> params = null;
        InputStream inputStream = null;
        try {
            // 读取参数
            inputStream = request.getInputStream();

            String xml = org.apache.commons.io.IOUtils.toString(inputStream, PayConstant.CHARSET);
            if (org.apache.commons.lang3.StringUtils.isBlank(xml)) {
                throw new ParameterNotValidException("入参不能为空！");
            }
            params = com.zhonghuilv.aitravel.pay.intf.util.XMLUtil.doXMLParse(xml);
            log.debug("解析微信支付回调参数:" + params);
            parseDTO.setSourse(xml);
            SortedMap<String, String> packageParams = new TreeMap<String, String>();
            params.forEach((key, value) -> packageParams.put(key, value == null ? value : value.trim()));

        } catch (IOException e) {
            throw new ServiceUnrealized("解析微信参数失败");
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        parseDTO.setParams(params);
        return parseDTO;
    }
}
