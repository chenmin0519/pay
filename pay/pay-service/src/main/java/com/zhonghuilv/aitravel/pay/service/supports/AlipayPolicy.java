package com.zhonghuilv.aitravel.pay.service.supports;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.zhonghuilv.aitravel.pay.intf.PayCallbackDTO;
import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.PayParams;
import com.zhonghuilv.aitravel.pay.intf.enums.*;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;
import com.zhonghuilv.aitravel.pay.service.config.AlipayConfig;
import com.zhonghuilv.aitravel.pay.service.exception.OrderException;
import com.zhonghuilv.aitravel.pay.service.exception.PayException;
import com.zhonghuilv.aitravel.pay.service.service.TradePaymentManagerService;
import com.zhonghuilv.aitravel.pay.service.util.aliauth.AlipayCore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Component("ALIPAY" + PayPolicy.BEAN_SUFFIX)
public class AlipayPolicy implements PayPolicy {

    /**
     * 异步消息处理失败时返回的参数
     */
    public static final String ASYNC_RESO_ERROR = "error";

    private static final BigDecimal ALIPAY_ACCURACY = new BigDecimal(100);

    @Autowired
    private AlipayConfig config;

    private Map<String, AlipayClient> clientMap = new HashMap<>();

    private Set<String> appids;

    private AlipayClient alipayClient;

    @Autowired
    TradePaymentManagerService tradePaymentManagerService;
    protected String appid;

    @Override
    public boolean support(String payWay) {
        return PayWayEnum.ALIPAY.name().equalsIgnoreCase(payWay);
    }


    @Override
    public String callback(ParseDTO parseDTO) {

        Map<String, String> params = parseDTO.getParams();
        try {
            //进行支付宝参数验签--》判断请求是否合法
            boolean flag = AlipaySignature.rsaCheckV1(params, config.getAlipayPublicKey(), PayConstant.CHARSET,
                    config.getSignType());
            if (flag) {
                tradePaymentManagerService.tradeCallback(getPayCallback(params, parseDTO.getSourse()));
            } else {
                //支付宝验签失败
                logger.error("支付宝回调非法！");
                return ASYNC_RESO_ERROR;
            }
        } catch (AlipayApiException e) {
            logger.error("支付宝回调验签出错", e);
            return ASYNC_RESO_ERROR;
        } catch (PayException p) {
            if (OrderException.ORDER_CLOSED.getErrcode().equals(p.getErrcode())) {
                //如果订单已处理 返回成功给支付宝
                return config.getRespSuccess();
            }
        }
        // 通知支付宝 成功处理结果
        return config.getRespSuccess();
    }

    /**
     * 获取对账文件下载地址
     *
     * @param temporal
     * @return
     */
    @Override
    public String getReconDownloadUrl(Date temporal) {
        return null;
    }

    @Override
    public String getPayInfo(PayParams payParams, String trxNo) {

        if (EnumOrderFrom.H5_PAY.name().equals(payParams.getOrderFrom())) {
//
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

            request.setNotifyUrl(config.getNotifyUrl());
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            model.setOutTradeNo(trxNo);
            model.setSubject(payParams.getProductName());

            model.setTotalAmount(new BigDecimal(payParams.getOrderPrice()).divide(new BigDecimal(100)).setScale(2,
                    BigDecimal.ROUND_UP).toString());
            request.setBizModel(model);
            try {
                AlipayTradePrecreateResponse response = getAlipayClient().execute(request);
                if (response.isSuccess()) {
                    return response.getQrCode();
                } else {
                    throw new PayException(response.getSubCode() + ":" + response.getSubMsg());
                }
            } catch (AlipayApiException e) {
                throw new PayException(e.getMessage());
            }
        }

        return getAppPayInfo(payParams, trxNo);
    }

    @Override
    public String getBill(String appid, String date , String type) {
        return null;
    }

    @Override
    public PayCallbackDTO getPayCallback(Map<String, String> params, String bankReturnMsg) {

        logger.info("获取支付回调对象--->{}", params);
        Objects.requireNonNull(params, "支付宝参数为Null");

        String status = getPlatStatusByAliStatus(params.get("trade_status"));
        PayCallbackDTO dto = new PayCallbackDTO();

        String platTrxNo = params.get("out_trade_no");

        String bizNo = params.get("out_biz_no");
        String bankTrxNo = params.get("trade_no");
        String orderNo = params.get("passback_params");

        if (TradeStatusEnum.SUCCESS.name().equals(status)) {
            int totalAmount = new BigDecimal(params.get("total_amount")).multiply(ALIPAY_ACCURACY).intValue();
            int receiptAmount = new BigDecimal(params.get("receipt_amount")).multiply(ALIPAY_ACCURACY).intValue();
            int buyerPayAmount = new BigDecimal(params.get("buyer_pay_amount")).multiply(ALIPAY_ACCURACY).intValue();
            dto.setReceiptAmount(receiptAmount);
            dto.setTotalAmount(totalAmount);
            dto.setBuyerPayAmount(buyerPayAmount);
            LocalDateTime payTime = LocalDateTime.parse(params.get("gmt_payment"), PayConstant.LOCAL_DATE_TIME);
            dto.setPayTime(payTime);
        }

        dto.setPlatTrxNo(platTrxNo);
        dto.setAppid(params.get("app_id"));
        dto.setOrderNo(orderNo);
        dto.setBizNo(bizNo);
        dto.setStatus(status);
        dto.setBizNo(params.get("out_biz_no"));
        dto.setBankTrxNo(bankTrxNo);

        dto.setBankReturnMsg(bankReturnMsg);
        return dto;
    }

    @Override
    public PayCallbackDTO orderQuery(TradePaymentOrder order, TradePaymentRecord record) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(record.getTrxNo());
        request.setBizModel(model);
        PayCallbackDTO dto = new PayCallbackDTO();
        try {
            AlipayTradeQueryResponse resp = getAlipayClient().execute(request);
            if (resp.isSuccess()) {
                String platStatus = getPlatStatusByAliStatus(resp.getTradeStatus());
                if (TradeStatusEnum.SUCCESS.name().equals(platStatus)) {
                    dto.setBuyerPayAmount(new BigDecimal(resp.getBuyerPayAmount())
                            .multiply(ALIPAY_ACCURACY).intValue());
                    dto.setReceiptAmount(new BigDecimal(resp.getReceiptAmount()).multiply(ALIPAY_ACCURACY).intValue());
                    dto.setPayTime(resp.getSendPayDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                    dto.setBankTrxNo(resp.getTradeNo());
                    dto.setTotalAmount(new BigDecimal(resp.getTotalAmount()).multiply(ALIPAY_ACCURACY).intValue());
                    dto.setRemark("支付宝主动查询");
                }
                dto.setPlatTrxNo(resp.getOutTradeNo());
                dto.setStatus(platStatus);
            } else {
                dto.setRemark("调用支付宝主动查询失败");
            }

        } catch (AlipayApiException e) {
            dto.setRemark("调用支付宝主动查询出现异常：" + e.getErrMsg());
            logger.error("调用阿里sdk失败---》{}", order);
        }
        return dto;
    }

    @Override
    public String auth2info(String code, String appid) {
        AlipaySystemOauthTokenResponse authresp = authresp(code, appid);
        if (authresp == null) return null;
        return authresp.getBody();
    }

    private AlipaySystemOauthTokenResponse authresp(String code, String appid) {
        this.appid = appid;
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();//创建API对应的request类
        request.setGrantType("authorization_code");
        request.setCode(code);
        try {
            return getAlipayClient().execute(request);
        } catch (Exception e) {
            logger.error("调用支付宝授权异常", e);
        }
        return null;
    }

    /**
     * 第三方授权信息
     *
     * @param code
     * @param appid
     * @return
     */
    @Override
    public Map<String, String> auth(String code, String appid) {
        AtomicReference<AlipaySystemOauthTokenResponse> authresp = new AtomicReference<>(authresp(code, appid));
        try {
            Map<String, String> map = new HashMap<>();
            AlipayUserUserinfoShareResponse response = getAlipayClient().execute(new
                    AlipayUserUserinfoShareRequest(), authresp.get().getAccessToken());
            if (response.isSuccess()) {
                map.put("account", authresp.get().getUserId());
                map.put("nickname", response.getNickName());
                return map;
            } else {
                logger.error(response.getSubMsg());
            }

        } catch (AlipayApiException e) {
            logger.error(e.getErrMsg());
        }
        return null;
    }

    @Override
    public String getBusinessNo(String appId) {
        return config.getBusinessNo();
    }

    /**
     * 处理成功给第三方的响应
     *
     * @return
     */
    @Override
    public String getRespSuccess() {
        return config.getRespSuccess();
    }

    /**
     * 处理失败给第三方的响应
     *
     * @return
     */
    @Override
    public String getRespError() {
        return config.getRespError();
    }

    /**
     * 退款
     */
    @Override
    public boolean refund(RefundRecord refundRecord) {

        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(refundRecord.getTrxNo()); //与预授权转支付商户订单号相同，代表对该笔交易退款
        model.setRefundAmount(new BigDecimal(refundRecord.getRefundAmount())
                .divide(ALIPAY_ACCURACY, 2, BigDecimal.ROUND_UP).doubleValue() + "");
        model.setRefundReason(refundRecord.getRefundReason());
        model.setOutRequestNo(refundRecord.getRefundTrxNo());//标识一次退款请求，同一笔交易多次退款需要保证唯一，如部分退款则此参数必传。
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        try {
            AlipayTradeRefundResponse response = getAlipayClient().execute(request);
            logger.info("third refund {} alipay respose :{}", refundRecord.getRefundTrxNo(), response.getBody());
            if (response.isSuccess()) {
                return true;
            }
        } catch (AlipayApiException e) {
            logger.error(refundRecord + " 支付宝退款失败", e);
            throw new PayException(e.getMessage());
        }
        return false;
    }


    private static final String OAUTH_APINAME = "com.alipay.account.auth";
    private static final String OAUTH_METHOD = "alipay.open.auth.sdk.code.get";

    /**
     * getAuthInfoStr
     * 这个方法支付宝没有提供sdk
     *
     * @return
     */
    public String getAuthInfoStr(String appid, String userId) {
        Map<String, String> map = new HashMap<>();
        map.put("apiname", OAUTH_APINAME);
        map.put("method", OAUTH_METHOD);
        map.put("app_id", appid);
        map.put("app_name", "mc");
        map.put("biz_type", "openservice");
        map.put("pid", config.getSellerId());
        map.put("product_id", "APP_FAST_LOGIN");
        map.put("scope", "kuaijie");
        map.put("target_id", userId);
        map.put("auth_type", "AUTHACCOUNT");

        String linkString = AlipayCore.createLinkString(map);
        String result = "";
        try {
            String sign = AlipaySignature.rsa256Sign(linkString, config.getAppPrivateKey(), DEFAULT_CHARSET);
            result = linkString + "&sign=" + URLEncoder.encode(sign, DEFAULT_CHARSET) + "&sign_type=RSA2";
        } catch (Exception e) {
            logger.error("调用支付宝加密失败", e);
        }
        return result;
    }

    @PostConstruct
    public AlipayClient getAlipayClient() {
        if (alipayClient == null) {
            alipayClient = new DefaultAlipayClient(config.getAlipayGateway(), config.getAppId(), config
                    .getAppPrivateKey(), FORMAT,
                    DEFAULT_CHARSET, config.getAlipayPublicKey(), config.getSignType());
        }
        return alipayClient;
    }

    @Autowired
    public void setConfig(AlipayConfig config) {
        this.config = config;
        String ids = config.getAppId();

        String[] split = StringUtils.split(ids, ",");
        for (String s : Arrays.asList(split)) {
            clientMap.put(s, new DefaultAlipayClient(config.getAlipayGateway(), s, config.getAppPrivateKey(),
                    FORMAT, DEFAULT_CHARSET,
                    config.getAlipayPublicKey(), config.getSignType()));
        }
        appids = clientMap.keySet();

    }

    /**
     * 获取配置中的所有appids
     *
     * @return
     */
    public Set<String> getAppids() {
        return appids;
    }

    private static final String FORMAT = "json";
    private static final String DEFAULT_CHARSET = "utf-8";

    private static final String RECON_BILL_TYPE_TRADE = "trade";

    Logger logger = LoggerFactory.getLogger(this.getClass());

    protected String getWapPayInfo(PayParams payParams, String trxNo) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        request.setNotifyUrl(config.getNotifyUrl());
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(trxNo);
        model.setSubject(EnumMerchant.AI_TRAVAL.name());
        model.setPassbackParams(payParams.getOrderNo());
        model.setTimeoutExpress(payParams.getOrderPeriod() + "m");
        model.setSellerId(config.getSellerId());
        model.setTotalAmount(new BigDecimal(payParams.getOrderPrice()).divide(ALIPAY_ACCURACY).setScale(2, BigDecimal
                .ROUND_UP).toString());
        model.setProductCode("QUICK_WAP_PAY");
        request.setReturnUrl(payParams.getReturnUrl());
        request.setBizModel(model);
        String body = null;
        try {
            body = getAlipayClient().pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            logger.error("生成支付表单失败：", e);
        }
        return body;
    }

    private String getAppPayInfo(PayParams payParams, String trxNo) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        model.setOutTradeNo(trxNo);
        model.setBody(payParams.getProductName());
        model.setSubject(EnumMerchant.AI_TRAVAL.name());
        model.setTotalAmount(new BigDecimal(payParams.getOrderPrice()).divide(ALIPAY_ACCURACY, 2, BigDecimal.ROUND_UP)
                .doubleValue() + "");
        //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        model.setProductCode(config.getProductCode());
        //回传参数 支付流水号
        try {
            model.setPassbackParams(URLEncoder.encode(payParams.getOrderNo(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            //not happen
        }

        return generatePayParams(model, payParams.getAppid());
    }

    public String getPayFrom(TradePaymentRecord record, String appId) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();

        request.setNotifyUrl(config.getNotifyUrl());
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        model.setOutTradeNo(record.getMerchantOrderNo());
        model.setSubject(EnumMerchant.AI_TRAVAL.getName());
        model.setPassbackParams(record.getTrxNo());
        model.setTimeoutExpress("5m");
        model.setSellerId(config.getSellerId());
        model.setTotalAmount(record.getOrderAmount().toString());
        model.setProductCode("QUICK_WAP_PAY");
        request.setReturnUrl(config.getNotifyUrl());
        request.setBizModel(model);
        String body = null;
        try {
            body = getAlipayClient().pageExecute(request).getBody();
        } catch (AlipayApiException e) {
            logger.error("生成支付表单失败：", e);
        }
        return body;
    }

    /**
     * 加签方法
     *
     * @param content
     * @return
     */
    public String getSign(String content) {
        try {
            return com.alipay.api.internal.util.AlipaySignature.rsaSign(content, config.getAppPrivateKey(),
                    DEFAULT_CHARSET, config.getSignType());
        } catch (AlipayApiException e) {
            logger.error("获取签名失败：" + content, e);
        }
        return null;
    }

    /**
     * 此方法会去掉sign_type做验签，暂时除生活号（原服务窗）激活开发者模式外都使用V1。
     *
     * @param params 参数列表(包括待验签参数和签名值sign) key-参数名称 value-参数值
     **/
    boolean rsaCheckV1(Map<String, String> params) {

        try {
            return AlipaySignature.rsaCheckV1(params, config.getAlipayPublicKey(), DEFAULT_CHARSET, config
                    .getSignType());
        } catch (AlipayApiException e) {
            logger.error("验证签名失败：" + params, e);
            return false;
        }
    }

    /**
     * 根据额外参数获取系统支付流水号（平台生成）
     *
     * @param passbackParams
     * @return
     */
    public String getTrxNo4PassbackParams(String passbackParams) {
        return passbackParams;
    }

    public AlipayTradeAppPayModel buildAlipayTradeAppPayModel(TradePaymentRecord record) {
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();

        model.setOutTradeNo(record.getMerchantOrderNo());
        model.setBody(record.getProductName());
        EnumMerchant enumMerchant = EnumMerchant.valueOf(record.getMerchantOrderNo());
        model.setSubject(enumMerchant.getName());
        model.setTotalAmount(record.getOrderAmount().toString());
        //销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
        model.setProductCode(config.getProductCode());
        //回传参数 支付流水号
        try {
            model.setPassbackParams(URLEncoder.encode(record.getTrxNo(), PayConstant.CHARSET));
        } catch (UnsupportedEncodingException e) {
            //not happen
        }
        return model;
    }

    public String getAppPayUrl(TradePaymentRecord record, String appId) {

        return generatePayParams(buildAlipayTradeAppPayModel(record), appId);
    }

    /**
     * 通过支付宝订单状态获取平台订单状态
     *
     * @param aliStatus
     * @return
     */
    public String getPlatStatusByAliStatus(String aliStatus) {
        String orderStatus = null;
        if (AlipayStateEnum.TRADE_SUCCESS.name().equals(aliStatus)) {
            orderStatus = TradeStatusEnum.SUCCESS.name();
        } else if (AlipayStateEnum.TRADE_CLOSED.name().equals(aliStatus)) {
            orderStatus = TradeStatusEnum.FAILED.name();
        } else if (AlipayStateEnum.TRADE_FINISHED.name().equals(aliStatus)) {//平台已处理
            orderStatus = TradeStatusEnum.SUCCESS.name();
        } else if (AlipayStateEnum.WAIT_BUYER_PAY.name().equals(aliStatus)) {
            orderStatus = TradeStatusEnum.CREATED.name();
        }
        return orderStatus;
    }

    /**
     * 生成预订单数据
     *
     * @param model
     * @return
     */
    public String generatePayParams(AlipayTradeAppPayModel model, String appId) {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setBizModel(model);
        request.setNotifyUrl(config.getNotifyUrl() + "/" + config.getAppId());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = getAlipayClient().sdkExecute(request);
            return response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            logger.error("生成订单数据失败！", e);
        }
        return null;
    }

    public String getReconDownloadUrl(Date temporal, String appId) {
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new
                AlipayDataDataserviceBillDownloadurlQueryRequest();
        AlipayDataDataserviceBillDownloadurlQueryModel model = new AlipayDataDataserviceBillDownloadurlQueryModel();
        model.setBillType(RECON_BILL_TYPE_TRADE);
        model.setBillDate(DateFormatUtils.format(temporal, "yyyy-MM-dd"));
        try {
            AlipayDataDataserviceBillDownloadurlQueryResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                return response.getBillDownloadUrl();
            } else {
                throw new PayException(response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            return null;
        }
    }

}
