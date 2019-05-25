package com.zhonghuilv.aitravel.pay.service.supports.wechat.request;


import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.WechatBillCheckModel;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.response.WechatPayBillResponse;

/**
 * 微信统一下单接口
 */
public class WechatPayDownloadBillRequest implements WechatPayRequest<WechatBillCheckModel, WechatPayBillResponse> {

    private WechatBillCheckModel model;

    public WechatPayDownloadBillRequest(WechatBillCheckModel model) {
        this.model = model;
    }

    @Override
    public String getApiAction() {
        return "/pay/downloadbill";
    }

    @Override
    public WechatBillCheckModel getModel() {
        return this.model;
    }

    @Override
    public void setModel(WechatBillCheckModel model) {
        this.model = model;
    }

    @Override
    public Class<WechatBillCheckModel> getObjectClass() {
        return WechatBillCheckModel.class;
    }

    @Override
    public Class<WechatPayBillResponse> getResponseClass() {
        return WechatPayBillResponse.class;
    }

    @Override
    public boolean requireCert() {
        return false;
    }
}
