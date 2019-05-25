
package com.zhonghuilv.aitravel.pay.intf.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.MainPO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@ApiModel("微信商户号")
@Table(name = "wechat_mch")
@Data
public class WechatMch extends MainPO {

    @Id
    @OrderBy("desc")
    private Long id;

//    @NotNull
//    @ApiModelProperty(value = "景区id", required = true)
//    private Long scenicId;

    @NotNull(message = "商户号不能为空")
    @ApiModelProperty(value = "微信支付分配的商户号", required = true)
    private String mchId;

    //    @NotNull
    @ApiModelProperty("-1-服务商 0-普通商户  服务商ID-服务商特约商户")
    private Long pid;

    @ApiModelProperty(value = "商户平台设置的密钥key 使用服务商开发不需要配置")
    private String partnerKey;

    @ApiModelProperty(value = "api证书在oss（私有块）的路径( 没有上传不支持退款) 使用服务商开发不需要配置")
    private String certPath;

    @ApiModelProperty(value = "状态")
    private Boolean state;

    @Transient
    @ApiModelProperty("绑定的所有小程序ID")
    private List<String> appid;

    /**
     * 是否为特约商户
     *
     * @return
     */
    @JsonIgnore
    public boolean isServerChild() {
        return this.pid > 0;
    }

    /**
     * 是否为服务商
     *
     * @return
     */
    @JsonIgnore
    public boolean isServer() {
        return this.pid.equals(-1L);
    }

}

