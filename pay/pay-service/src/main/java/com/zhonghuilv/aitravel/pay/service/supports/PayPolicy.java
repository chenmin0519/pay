package com.zhonghuilv.aitravel.pay.service.supports;

import com.zhonghuilv.aitravel.pay.intf.PayCallbackDTO;
import com.zhonghuilv.aitravel.pay.intf.PayParams;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;

import java.util.Date;
import java.util.Map;

/**
    * @Description: 支付策略
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface PayPolicy {


    /**
     * bean的后缀
     */
    String BEAN_SUFFIX = "Policy";

    boolean support(String payWay);

    String callback(ParseDTO parseDTO);

    /**
     * 获取对账文件下载地址
     *
     * @param temporal
     * @return
     */
    String getReconDownloadUrl(Date temporal);

    /**
     * 获取支付信息
     *
     * @param payParams 支付参数
     * @param trxNo     平台流水号
     * @return
     */
    String getPayInfo(PayParams payParams, String trxNo);

    String getBill(String appid, String date , String type );

    /**
     * 根据传过来的map获取回调对象
     *
     * @param params
     * @param bankReturnMsg
     * @return
     */
    PayCallbackDTO getPayCallback(Map<String, String> params, String bankReturnMsg);

    /**
     * 订单查询
     *
     * @param order  订单对象
     * @param record 流水对象
     * @return
     */
    PayCallbackDTO orderQuery(TradePaymentOrder order, TradePaymentRecord record);

    /**
     * 转账到账号（提现操作）
     *
     * @param account    账号信息
     * @param transferVO 转账信息
     * @return
     */

    /**
     * 第三方授权信息
     *
     * @param code
     * @param appid
     * @return
     */
    String auth2info(String code, String appid);

    /**
     * 第三方授权信息
     *
     * @param code
     * @param appid
     * @return
     */
    Map<String, String> auth(String code, String appid);

    String getBusinessNo(String appId);

    /**
     * 处理成功给第三方的响应
     *
     * @return
     */
    String getRespSuccess();

    /**
     * 处理失败给第三方的响应
     *
     * @return
     */
    String getRespError();

    /**
     * 退款
     *
     * @return
     */
    boolean refund(RefundRecord refundOrder);

}
