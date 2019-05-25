
package com.zhonghuilv.aitravel.pay.intf.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.MainPO;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumRefundState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@ApiModel("退款记录")
@Table(name = "refund_record")
@Data
@NoArgsConstructor
public class RefundRecord extends MainPO {

    @Id
    @OrderBy("desc")
    private Long id;

    @ApiModelProperty(value = "版本号", required = true)
    private Integer version;

    @ApiModelProperty("景区ID")
    private Long scenicId;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "支付流水号")
    private String trxNo;

    @ApiModelProperty(value = "第三方订单号")
    private String bankOrderNo;

    @ApiModelProperty(value = "商务号id", required = true)
    private String merchantId;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "业务类型")
    private String bizType;

    @ApiModelProperty(value = "支付方式类型")
    private String payWay;

    @ApiModelProperty(value = "订单总金额")
    private Integer totalAmount;

    @ApiModelProperty(value = "退款索引 从0开始", hidden = true)
    private Integer refundIndex;

    @ApiModelProperty(value = "订单退款金额")
    private Integer refundAmount;

    @ApiModelProperty(value = "退款流水号", required = true)
    private String refundTrxNo;

    @ApiModelProperty(value = "第三方退款流水号")
    private String bankRefundTrxNo;

    @ApiModelProperty(value = "退款请求时间")
    private java.time.LocalDateTime refundRequestTime;

    @ApiModelProperty(value = "退款完成时间")
    private java.time.LocalDateTime refundCompleteTime;

    @ApiModelProperty(value = "退款请求,申请人登录名")
    private Long requestApplyUserId;

    @ApiModelProperty(value = "退款原因")
    private String refundReason;

    @ApiModelProperty(value = "审核人")
    private Long auditor;

    @ApiModelProperty(value = "审核信息")
    private String auditReason;

    @ApiModelProperty("审核时间")
    private LocalDateTime auditTime;

    @ApiModelProperty(value = "退款状态（(1, \"审核中\"),\n" +
            "    (2, \"审核通过 第三方处理中\"),\n" +
            "    (3, \"审核不通过\"),\n" +
            "    (4, \"第三方-退款关闭\"),\n" +
            "    (5, \"退款成功\"),\n" +
            "    (6, \"第三方退款异常 微信（退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq" +
            ".com）-交易中心，手动处理此笔退款）\"),;\n" +
            "  ）", reference = "EnumRefundState")
    private Integer state;

    @Builder
    public RefundRecord(Long id, Integer version, Long scenicId, String orderNo, String trxNo, String bankOrderNo,
                        String
                                merchantId, String
                                productName, String bizType, String payWay, Integer totalAmount, Integer refundIndex,
                        Integer
                                refundAmount, String refundTrxNo, String bankRefundTrxNo, LocalDateTime
                                refundRequestTime, LocalDateTime refundCompleteTime,
                        Long
                                requestApplyUserId, String refundReason, Long auditor, String auditReason,
                        LocalDateTime auditTime,
                        Integer state) {
        this.id = id;
        this.version = version;
        this.scenicId = scenicId;
        this.orderNo = orderNo;
        this.trxNo = trxNo;
        this.bankOrderNo = bankOrderNo;
        this.merchantId = merchantId;
        this.productName = productName;
        this.bizType = bizType;
        this.payWay = payWay;
        this.totalAmount = totalAmount;
        this.refundIndex = refundIndex;
        this.refundAmount = refundAmount;
        this.refundTrxNo = refundTrxNo;
        this.bankRefundTrxNo = bankRefundTrxNo;
        this.refundRequestTime = refundRequestTime;
        this.refundCompleteTime = refundCompleteTime;
        this.requestApplyUserId = requestApplyUserId;
        this.refundReason = refundReason;
        this.auditor = auditor;
        this.auditReason = auditReason;
        this.auditTime = auditTime;
        this.state = state;
    }

    /**
     * 是否为审核中
     *
     * @return
     */
    @JsonIgnore
    public boolean isAuditing() {
        return EnumRefundState.AUDITING.getState().equals(state);
    }
}

