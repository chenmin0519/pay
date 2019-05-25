package com.zhonghuilv.aitravel.pay.intf.common.pojo.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderBy;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Data
public class StatePO extends MainPO {

    @Id
    @OrderBy("desc")
    private Long id;


    @ApiModelProperty(value = "状态")
    private Integer state;

}
