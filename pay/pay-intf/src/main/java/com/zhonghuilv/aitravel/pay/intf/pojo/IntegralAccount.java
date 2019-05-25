package com.zhonghuilv.aitravel.pay.intf.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.StatePO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Table(name = "integral_account")
@ApiModel("积分账户")
@Data
public class IntegralAccount extends StatePO {


    /**
     * 账户余额
     */
    @ApiModelProperty("账户余额")
    private Integer balance;

    @ApiModelProperty(value = "乐观锁标识", hidden = true)
    private Integer version;
    /**
     * 不可用余额
     */
    @ApiModelProperty("不可用余额")
    private Integer unbalance;

    /**
     * 总收益
     */
    @ApiModelProperty("总收益")
    private Integer totalIncome;

    /**
     * 总支出
     */
    @ApiModelProperty("总支出")
    private Integer totalExpend;

    @ApiModelProperty("用户ID")
    @NotNull
    private Long userId;

    @ApiModelProperty("")
    @NotNull
    private Long scenicId;

    @ApiModelProperty(value = "账号摘要 判断数据是否合法的凭证", hidden = true)
    @JsonIgnore
    private String digest;

}