package com.zhonghuilv.aitravel.pay.intf.common.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Data
@ApiModel("批处理转换类")
public class BatchUpdateDTO<T> implements Serializable{

    @ApiModelProperty("修改类容")
    private T model;

    @ApiModelProperty("修改主键集")
    private List<Long> ids;
}
