package com.zhonghuilv.aitravel.pay.service.controller;

import com.zhonghuilv.aitravel.pay.intf.clients.RefundRecordClient;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.RefundAuditDTO;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.RefundParamDTO;
import com.zhonghuilv.aitravel.pay.service.mapper.RefundRecordMapper;
import com.zhonghuilv.aitravel.pay.service.service.RefundService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@RestController
@RequestMapping("/refund_record")
@Api(tags = "退款记录")
public class RefundRecordController implements RefundRecordClient {

     RefundRecordMapper refundRecordMapper;

    @Autowired
    RefundService refundService;


    /**
     * 审核
     *
     * @param auditDTO
     * @return
     */
    @Override
    @RequestMapping(value = "/_audit", method = RequestMethod.POST)
    public boolean audit(@RequestBody RefundAuditDTO auditDTO) {

        return refundService.audit(auditDTO);
    }

    /**
     * 申请退款
     *
     * @param refundParam
     * @return
     */
    @Override
    @RequestMapping(value = "/_apply", method = RequestMethod.POST)
    public RefundRecord apply(@RequestBody RefundParamDTO refundParam) {

        return refundService.apply(refundParam);
    }

    /**
     * 直接退款
     * @param refundParam
     * @return
     */
    @Override
    @RequestMapping(value = "/_refund", method = RequestMethod.POST)
    public boolean refund(@RequestBody RefundParamDTO refundParam) {
        return refundService.refund(refundParam);
    }
}

