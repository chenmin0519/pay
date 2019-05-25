package com.zhonghuilv.aitravel.pay.intf.pojo.dto;

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
@ApiModel("退款参数")
@Data
public class RefundParamDTO {

    @ApiModelProperty(value = "退款用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty(value = "", hidden = true)
    private Long scenicId;

    @NotNull(message = "订单编号不能为空")
    @ApiModelProperty("订单号 ")
    private String orderNo;

    @ApiModelProperty("退款原因")
    private String desc;

    @ApiModelProperty(value = "退款金额(单位：分)", hidden = true)
    private Integer refundAmound;
}
