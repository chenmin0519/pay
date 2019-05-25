package com.zhonghuilv.aitravel.pay.intf.clients;

import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.RefundAuditDTO;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.RefundParamDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
    * @Description: 退款
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@FeignClient(value = PayConstant.SERVICE_NAME)
@RequestMapping("/refund_record")
public interface RefundRecordClient {

    @RequestMapping(value = "/_audit", method = RequestMethod.POST)
    boolean audit(@RequestBody RefundAuditDTO auditDTO);

    @RequestMapping(value = "/_apply", method = RequestMethod.POST)
    RefundRecord apply(@RequestBody RefundParamDTO refundParam);

    @RequestMapping(value = "/_refund", method = RequestMethod.POST)
    boolean refund(@RequestBody RefundParamDTO refundParam);
}

