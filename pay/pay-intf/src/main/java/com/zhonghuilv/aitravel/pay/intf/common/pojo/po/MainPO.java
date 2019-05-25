package com.zhonghuilv.aitravel.pay.intf.common.pojo.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Data
public class MainPO extends BasePO {

    @JsonIgnore
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @JsonIgnore
    @ApiModelProperty(value = "创建人")
    private Long creator;

    @JsonIgnore
    @ApiModelProperty("最后修改时间")
    private LocalDateTime editTime;

    @JsonIgnore
    @ApiModelProperty("最后修改人")
    private Long editor;
}
