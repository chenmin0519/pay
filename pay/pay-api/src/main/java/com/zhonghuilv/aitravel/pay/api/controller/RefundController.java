package com.zhonghuilv.aitravel.pay.api.controller;

import com.zhonghuilv.aitravel.pay.intf.clients.OrderClient;
import com.zhonghuilv.aitravel.pay.intf.clients.PayCoreClient;
import com.zhonghuilv.aitravel.pay.intf.clients.RefundRecordClient;
import com.zhonghuilv.aitravel.pay.intf.common.ApiResult;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumOrderNum;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.YOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.RefundAuditDTO;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.RefundParamDTO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenmin
 * @title: RefundController
 * @projectName pay
 * @description: TODO
 * @date 2019-05-2411:18
 * @Version: 1.0
 * @JDK: 10
 */
@RestController
@RequestMapping("/pay/refund")
@Api("退款")
public class RefundController {

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private RefundRecordClient refundRecordClient;

    @Autowired
    private PayCoreClient payCoreClient;

    /**
     * 申请退款
     *
     * @param refundParam
     * @return
     */
    @RequestMapping(value = "/_apply", method = RequestMethod.POST)
    public ApiResult<RefundRecord> apply(@RequestBody RefundParamDTO refundParam) {
        if(refundParam.getOrderNo() == null){
            return ApiResult.error("订单编号为空！");
        }
        if(!refundParam.getOrderNo().startsWith(EnumOrderNum.TN.getKey()) ){
            return ApiResult.error("订单为升星，或者兑奖订单不允许退款操作！");
        }
        YOrder yOrder = orderClient.queryOrderByOrderNum(refundParam.getOrderNo());
        refundParam.setRefundAmound(yOrder.getPayment());
        refundParam.setUserId(yOrder.getUserId());
        return ApiResult.success(refundRecordClient.apply(refundParam));
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public String test(){
        return payCoreClient.test("hello");
    }
    /**
     * 审核
     *
     * @param auditDTO
     * @return
     */
    @RequestMapping(value = "/_audit", method = RequestMethod.POST)
    public ApiResult<Boolean> audit(@RequestBody RefundAuditDTO auditDTO) {
        Boolean flag = false;
        try{
            flag = refundRecordClient.audit(auditDTO);
        }catch (Exception e){
            e.printStackTrace();
            ApiResult.error("退款审核失败"+e.getMessage());
        }
        if(flag){
            return ApiResult.success(flag);
        }else{
            return ApiResult.error("退款审核失败");
        }
    }

    /**
     * 直接退款
     * @param refundParam
     * @return
     */
    @RequestMapping(value = "/_refund", method = RequestMethod.POST)
    public ApiResult<Boolean> refund(@RequestBody RefundParamDTO refundParam) {
        if(refundParam.getOrderNo() == null){
            return ApiResult.error("订单编号为空！");
        }
        if(!refundParam.getOrderNo().startsWith(EnumOrderNum.TN.getKey()) ){
            return ApiResult.error("订单为升星，或者兑奖订单不允许退款操作！");
        }
        Boolean flag = false;
        try{
            flag = refundRecordClient.refund(refundParam);
        }catch (Exception e){
            e.printStackTrace();
            ApiResult.error("直接退款失败"+e.getMessage());
        }
        if(flag){
            return ApiResult.success(flag);
        }else{
            return ApiResult.error("直接退款失败");
        }
    }
}
