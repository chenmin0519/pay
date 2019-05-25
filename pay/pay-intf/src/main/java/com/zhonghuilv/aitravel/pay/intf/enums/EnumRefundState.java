package com.zhonghuilv.aitravel.pay.intf.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create By zhengjing on 2018/7/5 11:26
 */
@AllArgsConstructor
@Getter
@ApiModel(value = "EnumRefundState", description = "退款信息 传state")
public enum EnumRefundState {

    AUDITING(1, "审核中"),
    BANK_PROCESSING(2, "审核通过 第三方处理中"),
    AUDIT_REJECT(3, "审核不通过"),
    REFUNDCLOSE(4, "第三方-退款关闭"),
    SUCCESS(5, "退款成功"),
    BANK_ERROR(6, "第三方退款异常 微信（退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往商户平台（pay.weixin.qq.com）-交易中心，手动处理此笔退款）"),;

    private Integer state;

    private String desc;

}
