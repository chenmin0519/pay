package com.zhonghuilv.aitravel.pay.service.service;

import com.zhonghuilv.aitravel.common.excption.ParameterNotValidException;
import com.zhonghuilv.aitravel.common.util.Assert;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumRefundState;
import com.zhonghuilv.aitravel.pay.intf.enums.TradeStatusEnum;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.RefundAuditDTO;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.RefundParamDTO;
import com.zhonghuilv.aitravel.pay.service.mapper.RefundRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Service
public class RefundService {

    @Autowired
    TradePaymentOrderService orderService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    BuildService buildService;

    @Autowired
    RefundRecordMapper refundRecordMapper;

    /**
     * 申请退款
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public RefundRecord apply(RefundParamDTO paramDTO) {

        TradePaymentOrder order = orderService.loadByMerchantNoAndOrder(paramDTO.getOrderNo());

        if (order == null) {
            throw new ParameterNotValidException("支付订单不存在");
        }
        if (!TradeStatusEnum.SUCCESS.name().equals(order.getStatus())) {
            throw new ParameterNotValidException("订单未支付，或已申请退款，或已退款！");
        }

        RefundRecord query = RefundRecord.builder().orderNo(paramDTO.getOrderNo()).build();
        //已退款金额
        Integer orderAmount = order.getOrderAmount();
        List<RefundRecord> select = refundRecordMapper.select(query);
        if (!CollectionUtils.isEmpty(select)) {
            //计算已申请退款金额
            int refundedAmount = select.stream().mapToInt(RefundRecord::getRefundAmount).sum();
            orderAmount = orderAmount - refundedAmount;
        }
        //且可以退款
        if (orderAmount.compareTo(paramDTO.getRefundAmound()) > 0) {
            throw new ParameterNotValidException("退款金额超过订单金额！：" + orderAmount + "," + paramDTO.getRefundAmound());
        }

        RefundRecord refundRecord = RefundRecord.builder()
                .trxNo(order.getTrxNo())
                .scenicId(paramDTO.getScenicId())
                .productName(order.getProductName())
                .version(1)
                .state(EnumRefundState.AUDITING.getState())
                .bankOrderNo(order.getBankTrxNo())
                .orderNo(order.getMerchantOrderNo())
                .refundIndex(0)
                .refundTrxNo(buildService.buildRefundTrxNo())
                .merchantId(order.getAppId())
                .bizType(order.getBizCode())
                .payWay(order.getPayWayCode())
                .refundRequestTime(LocalDateTime.now())
                .totalAmount(order.getOrderAmount())
                .refundAmount(paramDTO.getRefundAmound())
                .refundReason(paramDTO.getDesc())
                .requestApplyUserId(paramDTO.getUserId())
                .build();
        refundRecordMapper.insertUseGeneratedKeys(refundRecord);

        //修改支付订单状态
        TradePaymentOrder paymentOrder = new TradePaymentOrder();
        paymentOrder.setStatus(TradeStatusEnum.REFUND_AUDIT.name());
        paymentOrder.setId(order.getId());
        paymentOrder.setVersion(order.getVersion() + 1);
        orderService.updateSelective(paymentOrder);
        return refundRecord;

    }

    /**
     * 审核退款
     *
     * @param auditDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean audit(RefundAuditDTO auditDTO) {

        RefundRecord refundRecord = refundRecordMapper.selectByPrimaryKey(auditDTO.getRefundRecordId());
        Assert.notNull(refundRecord, "退款记录不存在！");

        Assert.state(refundRecord.isAuditing(), "只能审核状态为审核中的数据！");

        //第三方退款 并获取第三方退款单号
        if (auditDTO.isPass()) {
            InternalRefundService internalRefundService = applicationContext.getBean(refundRecord.getPayWay() +
                    InternalRefundService.BEAN_NAME_SUFIX, InternalRefundService.class);
            internalRefundService.refund(refundRecord);
        }
        //build update po
        RefundRecord update = RefundRecord.builder()
                .id(refundRecord.getId())
                .auditor(auditDTO.getAuditor())
                .auditReason(auditDTO.getReason())
                .auditTime(LocalDateTime.now())
                .state((auditDTO.isPass() ? EnumRefundState.BANK_PROCESSING : EnumRefundState.AUDIT_REJECT).getState())
                .bankRefundTrxNo(refundRecord.getBankRefundTrxNo())
                .refundCompleteTime(LocalDateTime.now())
                .build();

        TradePaymentOrder order = orderService.loadByMerchantNoAndOrder(refundRecord.getOrderNo());
        if (auditDTO.isPass()) {
            TradePaymentOrder updateOrder = new TradePaymentOrder();
            updateOrder.setId(order.getId());
            updateOrder.setSuccessRefundAmount(Optional.ofNullable(order.getSuccessRefundAmount()).orElse(0) +
                    refundRecord.getRefundAmount());
            updateOrder.setStatus(TradeStatusEnum.REFUND_SUCCESS.name());
            updateOrder.setVersion(order.getVersion() + 1);
            orderService.updateSelective(updateOrder);
        }
        return refundRecordMapper.updateByPrimaryKeySelective(update) == 1;
    }

    /**
     * 直接退款
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean refund(RefundParamDTO paramDTO) {
        RefundRecord apply = this.apply(paramDTO);

        RefundAuditDTO auditDTO = new RefundAuditDTO();
        auditDTO.setAudit(Boolean.TRUE);
        auditDTO.setAuditor(-1L);
        auditDTO.setReason("后台退款，无审核流程！");
        auditDTO.setRefundRecordId(apply.getId());
        return this.audit(auditDTO);
    }
}
