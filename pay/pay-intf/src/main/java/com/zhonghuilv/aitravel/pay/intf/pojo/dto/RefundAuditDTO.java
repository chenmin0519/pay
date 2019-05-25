package com.zhonghuilv.aitravel.pay.intf.pojo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@ApiModel(description = "退款审核原因")
@Data
public class RefundAuditDTO {

    @NotNull(message = "退款记录ID不能为空")
    @ApiModelProperty(value = "退款记录ID", required = true)
    private Long refundRecordId;

    @NotNull(message = "审核结果不能为空")
    @ApiModelProperty(value = "审核是否通过", example = "true", required = true)
    private Boolean audit;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty(value = "审核人", hidden = true)
    private Long auditor;

    @ApiModelProperty(value = "审核信息(成功 失败原因)")
    private String reason;

    /**
     * 是否审核通过
     *
     * @return
     */
    @JsonIgnore
    public boolean isPass() {
        return Boolean.TRUE.equals(audit);
    }
}
