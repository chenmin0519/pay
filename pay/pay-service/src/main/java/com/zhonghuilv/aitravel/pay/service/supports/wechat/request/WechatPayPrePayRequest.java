package com.zhonghuilv.aitravel.pay.service.supports.wechat.request;


import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.WechatPayPrePayModel;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.response.WechatPayPrePayResponse;

/**
 * 微信统一下单接口
 */
public class WechatPayPrePayRequest implements WechatPayRequest<WechatPayPrePayModel, WechatPayPrePayResponse> {
    private WechatPayPrePayModel model;

    public WechatPayPrePayRequest(WechatPayPrePayModel model) {
        this.model = model;
    }

    @Override
    public String getApiAction() {
        return "/pay/unifiedorder";
    }

    @Override
    public WechatPayPrePayModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(WechatPayPrePayModel model) {
        this.model = model;
    }

    @Override
    public Class<WechatPayPrePayModel> getObjectClass() {
        return WechatPayPrePayModel.class;
    }

    @Override
    public Class<WechatPayPrePayResponse> getResponseClass() {
        return WechatPayPrePayResponse.class;
    }

    @Override
    public boolean requireCert() {
        return false;
    }
}
