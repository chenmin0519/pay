
package com.zhonghuilv.aitravel.pay.intf.pojo;

import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.MainPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@ApiModel("积分记录")
@Table(name = "integral_history")
@Data
public class IntegralHistory extends MainPO {

    @Id
    @OrderBy("desc")
    private Long id;

    @ApiModelProperty(value = "积分账户id", required = true)
    private Long accountId;

    @ApiModelProperty(value = "用户Id", required = true)
    private Long userId;

    @ApiModelProperty(value = "", required = true)
    private Long scenicId;

    @ApiModelProperty(value = "备注信息", required = true)
    private String remark;

    @ApiModelProperty(value = "变动积分", required = true)
    private Integer integral;

    @ApiModelProperty(value = "变动后积分", required = true)
    private Integer balance;

    @ApiModelProperty(value = "资金变动方向", required = true)
    private String fundDirection;

    @ApiModelProperty(value = "请求号", hidden = true)
    private String requestNo;

    @ApiModelProperty(value = "业务类型")
    private String bizType;

    @ApiModelProperty(value = "相关值业务的值")
    private String bizValue;
}

