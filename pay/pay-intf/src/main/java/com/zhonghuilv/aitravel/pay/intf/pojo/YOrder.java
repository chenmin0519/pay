package com.zhonghuilv.aitravel.pay.intf.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
    * @Description:    订单实体
    * @Author:         chenmin
    * @CreateDate:     2019-05-15 2019-05-15
    * @Version:        1.0
    */
@ApiModel("订单信息")
@Table(name = "y_order")
public class YOrder {
    @Id
    @OrderBy("desc")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ApiModelProperty(value = "订单创建者手机号/账号")
    private String createPhone;
    @ApiModelProperty(value = "优惠券")
    private String coupon;
    @ApiModelProperty(value = "订单名称")
    private String orderName;
    @ApiModelProperty(value = "店家名称")
    private String shopName;
    @ApiModelProperty(value = "商品数量")
    private Integer goodsCount;
    @ApiModelProperty(value = "支付类型 WEIXIN微信，ALIPAY支付宝")
    private String paymentType;
    @ApiModelProperty(value = "状态 1未付款 2已付款 3交易成功 4交易关闭")
    private Integer state;
    @ApiModelProperty(value = "实付金额（单位分）")
    private Integer payment;
    @ApiModelProperty(value = "支付时间")
    private Date paymentTime;
    @ApiModelProperty(value = "交易完成时间")
    private Date endTime;
    @ApiModelProperty(value = "最后支付超时 交易关闭时间")
    private Date closeTime;
    @ApiModelProperty(value = "买家留言")
    private String buyerMessage;
    @ApiModelProperty(value = "订单编号")
    private String orderNum;
    @ApiModelProperty(value = "标题图片")
    private String photo;
    @ApiModelProperty(value = "收货地址id")
    private Long addressId;
    @ApiModelProperty(value = "用户id")
    private Long userId;
    @ApiModelProperty(value = "备注")
    private Long remark;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getRemark() {
        return remark;
    }

    public void setRemark(Long remark) {
        this.remark = remark;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreatePhone() {
        return createPhone;
    }

    public void setCreatePhone(String createPhone) {
        this.createPhone = createPhone;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }
}
