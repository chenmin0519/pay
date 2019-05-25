package com.zhonghuilv.aitravel.pay.intf.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author chenmin
 * @title: YOrderDetail
 * @projectName shop-springboot
 * @description: TODO
 * @date 2019-05-1914:20
 * @Version: 1.0
 * @JDK: 10
 */
@ApiModel("订单详情信息")
@Table(name = "y_order_detail")
public class YOrderDetail {
    @Id
    private Long id;
    @ApiModelProperty(value = "商品id")
    private Long goodsId;
    @ApiModelProperty(value = "实际价格（单位分）")
    private Integer price;
    @ApiModelProperty(value = "折扣价格")
    private Integer conpenPrice;
    @ApiModelProperty(value = "购买数量")
    private Integer count;
    @ApiModelProperty(value = "商品图片地址")
    private String picPath;
    @ApiModelProperty(value = "订单id")
    private Long orderId;
    @ApiModelProperty(value = "商品规格")
    private String goodsNorms;
    private Long merchantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getConpenPrice() {
        return conpenPrice;
    }

    public void setConpenPrice(Integer conpenPrice) {
        this.conpenPrice = conpenPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getGoodsNorms() {
        return goodsNorms;
    }

    public void setGoodsNorms(String goodsNorms) {
        this.goodsNorms = goodsNorms;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }
}
