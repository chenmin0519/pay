package com.zhonghuilv.aitravel.pay.service.supports;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhonghuilv.aitravel.pay.intf.PayCallbackDTO;
import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.PayParams;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumMerchant;
import com.zhonghuilv.aitravel.pay.intf.enums.EnumOrderFrom;
import com.zhonghuilv.aitravel.pay.intf.enums.PayWayEnum;
import com.zhonghuilv.aitravel.pay.intf.enums.TradeStatusEnum;
import com.zhonghuilv.aitravel.pay.intf.pojo.RefundRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.TradePaymentRecord;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.ParseDTO;
import com.zhonghuilv.aitravel.pay.intf.util.PayCommonUtil;
import com.zhonghuilv.aitravel.pay.service.config.WechatPayConfig;
import com.zhonghuilv.aitravel.pay.service.service.TradePaymentManagerService;
import com.zhonghuilv.aitravel.pay.service.service.WechatMchService;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.client.WechatPayClient;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.exception.WechatPayException;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.WechatBillCheckModel;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.WechatPayPrePayModel;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.WechatPayQueryModel;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.model.WechatPayRefundModel;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.request.WechatPayDownloadBillRequest;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.request.WechatPayPrePayRequest;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.request.WechatPayQueryRequest;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.request.WechatPayRefundRequest;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.response.WechatPayPrePayResponse;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.response.WechatPayQueryResponse;
import com.zhonghuilv.aitravel.pay.service.supports.wechat.response.WechatPayRefundResponse;
import com.zhonghuilv.aitravel.pay.service.util.RandomUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Component("WEIXIN" + PayPolicy.BEAN_SUFFIX)
public class WeixinPolicy implements PayPolicy {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    RestTemplate restTemplate = new RestTemplate();
    /**
     * 微信生成预订单URL
     */
    private static final String URL_UNIFIEDORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    @Autowired
    private WechatPayConfig config;

    private static final String ENCODING = "UTF-8";

    private static final String SUCCESS = "SUCCESS";

    /**
     * 、APP--app支付
     */
    private static final String TRADE_TYPE_APP = "APP";

    private static final String WEIXIN_PARAM_PACKAGE = "Sign=WXPay";
    /**
     * NATIVE--原生扫码支付
     */
    private static final String TRADE_TYPE_NATIVE = "NATIVE";

    /**
     * NATIVE--原生扫码支付
     */
    private static final String TRADE_TYPE_MINIAPP = "JSAPI";

    /**
     * 微信支付状态 未支付 —转入退款
     */
    private static final String TRADE_STATE_NOTPAY = "NOTPAY";
    /**
     * 微信支付状态 —转入退款
     */
    private static final String TRADE_STATE_REFUND = "REFUND";
    /**
     * 微信支付状态 已关闭
     */
    private static final String TRADE_STATE_CLOSED = "CLOSED";
    /**
     * 微信支付状态 用户支付中
     */
    private static final String TRADE_STATE_USERPAYING = "USERPAYING";
    /**
     * 微信支付状态 支付失败(其他原因，如银行返回失败)
     */
    private static final String TRADE_STATE_PAYERROR = "PAYERROR";

    /**
     * 正确响应code值
     */
    static final String RESULT_CODE_SUCCESS = "SUCCESS";

    private static final String RESULT_CODE_FAIL = "FAIL";

    /**
     * 微信处理成功
     */
    public static final String ASYNC_RESP_WEIXIN_SUCCESS =
            "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml> ";

    /**
     * 微信处理失败
     */
    public static final String ASYNC_RESP_WEIXIN_ERROR =
            "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[失败]]></return_msg></xml> ";

    public String getPlatStatus(String code) {
        if (SUCCESS.equals(code)) {
            return TradeStatusEnum.SUCCESS.name();
        }
        return TradeStatusEnum.FAILED.name();
    }


    @Autowired
    TradePaymentManagerService tradePaymentManagerService;

    @Autowired
    WechatMchService wechatMchService;

    @Override
    public boolean support(String payWay) {
        return PayWayEnum.WEIXIN.name().equalsIgnoreCase(payWay);
    }

    @Override
    public String callback(ParseDTO parseDTO) {

        WechatPayClient wechatPayClient = wechatMchService.loadClient(parseDTO.getAppid());

        Map<String, String> params = parseDTO.getParams();
        // 读取参数
        SortedMap<String, String> packageParams = new TreeMap<>();
        params.forEach((key, value) -> packageParams.put(key, value == null ? value : value.trim()));
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, wechatPayClient.getApiKey())) {
            tradePaymentManagerService.tradeCallback(getPayCallback(params, parseDTO.getSourse()));
        }
        return ASYNC_RESP_WEIXIN_SUCCESS;
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

    //构造微信map
    private SortedMap<String, String> buildWXData(PayParams param, String trxNo) {
        String outTraderNo = param.getOrderNo();
        //订单的额外信息

        SortedMap<String, String> map = new TreeMap<>();
        //随机字符串生成
        map.put("appid", param.getAppid());
        map.put("mch_id", config.getMechIdByAppid(param.getAppid()));
        //终端设备号(门店号或收银设备ID)，默认请传"WEB"
        map.put("device_info", "WEB");

        map.put("nonce_str", RandomUtility.get32UUID());

        map.put("body", String.format("%s-%s", EnumMerchant.AI_TRAVAL.getName(), param.getProductName()));

        //额外信息传递 平台生成的支付流水号
        map.put("attach", outTraderNo);
        //平台订单号
        map.put("out_trade_no", trxNo);
        map.put("total_fee", param.getOrderPrice() + "");
        //todo edit config
        map.put("spbill_create_ip", param.getOrderIp());
        map.put("time_start", PayConstant.WEIXIN_DATE_TIME.format(param.getOrderTime()));
        map.put("time_expire", PayConstant.WEIXIN_DATE_TIME.format(param.getOrderTime().plusMinutes(param
                .getOrderPeriod())));

        map.put("notify_url", config.getNotifyUrl() + "/" + param.getAppid());
        if (EnumOrderFrom.MINI_APP.name().equals(param.getOrderFrom())) {
            map.put("trade_type", TRADE_TYPE_MINIAPP);
            map.put("openid", param.getOpendId());
        } else {
            map.put("trade_type", EnumOrderFrom.H5_PAY.name().equals(param.getOrderFrom()) ? TRADE_TYPE_NATIVE :
                    TRADE_TYPE_APP);
        }
        return map;
    }

    /**
     * 获取支付信息
     *
     * @param payParams 支付参数
     * @param trxNo     平台流水号
     * @return
     */
    @Override
    public String getPayInfo(PayParams payParams, String trxNo) {

        WechatPayClient wechatPayClient = wechatMchService.loadClient(payParams.getAppid());

        WechatPayPrePayModel.WechatPayPrePayModelBuilder prePayModelBuilder = WechatPayPrePayModel.builder()
                .totalFee(payParams.getOrderPrice())
                .timeStart(PayConstant.WEIXIN_DATE_TIME.format(payParams.getOrderTime()))
                .timeExpire(PayConstant.WEIXIN_DATE_TIME.format(payParams.getOrderTime().plusMinutes(payParams
                        .getOrderPeriod())))
                .outTradeNo(trxNo)
                .body(String.format("%s-%s", EnumMerchant.AI_TRAVAL.getName(), payParams.getProductName()))
                .deviceInfo("WEB")
                .attach(payParams.getOrderNo())
                .spbillCreateIp(payParams.getOrderIp())
                .notifyUrl(config.getNotifyUrl() + "/" + payParams.getAppid());

        if (EnumOrderFrom.MINI_APP.name().equals(payParams.getOrderFrom())) {
            prePayModelBuilder.tradeType(TRADE_TYPE_MINIAPP);
            if (wechatPayClient.isServer()) {
                prePayModelBuilder.subOpenid(payParams.getOpendId());
            } else {
                prePayModelBuilder.openId(payParams.getOpendId());
            }
        } else {
            prePayModelBuilder.tradeType(EnumOrderFrom.H5_PAY.name().equals(payParams.getOrderFrom()) ?
                    TRADE_TYPE_NATIVE :
                    TRADE_TYPE_APP);
        }
        WechatPayPrePayResponse execute = wechatPayClient.execute(
                new WechatPayPrePayRequest(prePayModelBuilder.build()));

        return toJsonStr(execute.buildAppInfo(payParams.getOrderFrom(), wechatPayClient.getApiKey()));
    }

    /**
     * 对账
     * @param appid 公众号appid
     * @param date  对账日期
     * @param type  账单类型 {@link com.zhonghuilv.aitravel.pay.intf.enums.EnumWeiXinBillType}
     */
    @Override
    public String getBill(String appid, String date, String type){
        WechatPayClient wechatPayClient = wechatMchService.loadClient(appid);
        WechatBillCheckModel.WechatBillCheckModelBuilder wechatBillCheckModelBuilder = WechatBillCheckModel.builder().billDate(date).billType(type).tarType(null);
        String s = wechatPayClient.executePost(new WechatPayDownloadBillRequest((wechatBillCheckModelBuilder.build())));
        return s;
    }

    @Override
    public PayCallbackDTO getPayCallback(Map<String, String> params, String bankReturnMsg) {
        if (SUCCESS.equals(params.get("result_code"))) {
            return buildSuccessDTO(params, bankReturnMsg);
        }
        PayCallbackDTO dto = new PayCallbackDTO();
        dto.setStatus(TradeStatusEnum.FAILED.name());
        dto.setRemark(params.get("err_code_des"));
        return dto;
    }

    private PayCallbackDTO buildSuccessDTO(Map<String, String> result, String bankReturnMsg) {
        PayCallbackDTO dto = new PayCallbackDTO();

        String platTrxNo = result.get("out_trade_no");

        String orderNo = result.get("attach");
        /**
         * 微信订单号
         */
        String bankTrxNo = result.get("transaction_id");
        //微信返回的金额是分 转换成元
        int totalAmount = convert2Int(result.get("total_fee"));

        int buyerPayAmount = convert2Int(result.get("cash_fee"));
        //支付金额
        int cashFee = convert2Int(result.get("cash_fee"));
        //计算实收金额并转换成元 支付金额-手续费

        int receiptAmount = cashFee - Double.valueOf(cashFee * config.getPayRate()).intValue();


        LocalDateTime payTime = LocalDateTime.parse(result.get("time_end"), DateTimeFormatter.ofPattern
                ("yyyyMMddHHmmss"));
        dto.setPayTime(payTime);


        dto.setAppid(result.get("appid"));
        dto.setStatus(TradeStatusEnum.SUCCESS.name());
        dto.setPlatTrxNo(platTrxNo);
        dto.setReceiptAmount(receiptAmount);
        dto.setTotalAmount(totalAmount);
        dto.setBuyerPayAmount(buyerPayAmount);
        dto.setOrderNo(orderNo);
        dto.setStatus(getPlatStatus(result.get("result_code")));
        dto.setBankTrxNo(bankTrxNo);

        dto.setBankReturnMsg(bankReturnMsg);
        return dto;
    }


    @Override
    public PayCallbackDTO orderQuery(TradePaymentOrder order, TradePaymentRecord record) {

        WechatPayClient wechatPayClient = wechatMchService.loadClient(order.getAppId());
        //build params map
        WechatPayQueryModel build = WechatPayQueryModel.builder().outTradeNo(record.getTrxNo()).build();
        WechatPayQueryRequest queryRequest = new WechatPayQueryRequest();
        queryRequest.setModel(build);

        WechatPayQueryResponse execute = wechatPayClient.execute(queryRequest);
        logger.info("微信返回数据为{}", execute);
        SortedMap<String, String> map = new TreeMap<>();

        if (execute.isSuccess()) {
            String weixinState = execute.getTradeState().name();
            if (SUCCESS.equals(weixinState)) {
                return buildSuccessDTO(execute);
            }
            PayCallbackDTO dto = new PayCallbackDTO();
            dto.setStatus(getPlatTradeState(weixinState));
            dto.setRemark("查询微信订单状态为：" + execute.getTradeStateDesc());
            dto.setBankReturnMsg(execute.toString());
            return dto;
        } else {
            logger.error("orderQuery call error--->{}", execute.toString());
        }
        return null;
    }


    private static final String info = "https://api.weixin.qq" +
            ".com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    /**
     * 第三方授权信息
     * code
     *
     * @param code
     * @param appid
     * @return
     */
    @Override
    public String auth2info(String code, String appid) {
        String url = String.format(info, appid, config.getAppSecret(), code);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);

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
        JSONObject jsonObject = JSONObject.parseObject(auth2info(code, appid));
        if (jsonObject.containsKey("openid")) {
            String uri = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN",
                    jsonObject
                            .getString("access_token"), jsonObject.getString("openid"));
            Map<String, String> map = new HashMap<>();


            JSONObject re = JSONObject.parseObject(restTemplate.getForObject(uri, String.class));
            map.put("account", jsonObject.getString("openid"));
            map.put("nickname", re.getString("nickname"));
            return map;

        }
        return null;
    }

    @Override
    public String getBusinessNo(String appId) {
        return null;
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
     *
     * @return
     */
    @Override
    public boolean refund(RefundRecord refundOrder) {
        WechatPayRefundModel refundModel = WechatPayRefundModel.builder()
                .transactionId(refundOrder.getBankOrderNo())
                .outTradeNo(refundOrder.getTrxNo())
                .refundFee(refundOrder.getRefundAmount())
                .totalFee(refundOrder.getTotalAmount())
                .outRefundNo(refundOrder.getRefundTrxNo())
                .refundDesc(refundOrder.getRefundReason())
                .build();

        WechatPayClient wechatPayClient = wechatMchService.loadClient(refundOrder.getMerchantId());

        WechatPayRefundRequest refundRequest = new WechatPayRefundRequest(refundModel);
        WechatPayRefundResponse response = wechatPayClient.execute(refundRequest);

        refundOrder.setRefundRequestTime(LocalDateTime.now());
        if (response.isSuccess()) {
            refundOrder.setBankRefundTrxNo(response.getRefundId());
            return true;
        } else {
            throw new WechatPayException(response.errormsg());
        }
    }

    private PayCallbackDTO buildSuccessDTO(WechatPayQueryResponse execute) {

        PayCallbackDTO dto = new PayCallbackDTO();

        String platTrxNo = execute.getOutTradeNo();

        String orderNo = execute.getAttach();
        /**
         * 微信订单号
         */
        String bankTrxNo = execute.getTransactionId();
        //微信返回的金额是分 转换成元
        int totalAmount = convert2Int(execute.getTotalFee());

        int buyerPayAmount = convert2Int(execute.getCashFee());
        //支付金额
        int cashFee = convert2Int(execute.getCashFee());
        //计算实收金额并转换成元 支付金额-手续费

        int receiptAmount = cashFee - Double.valueOf(cashFee * config.getPayRate()).intValue();


        LocalDateTime payTime = LocalDateTime.parse(execute.getTimeEnd(), DateTimeFormatter.ofPattern
                ("yyyyMMddHHmmss"));
        dto.setPayTime(payTime);


        dto.setAppid(execute.getAppId());
        dto.setStatus(TradeStatusEnum.SUCCESS.name());
        dto.setPlatTrxNo(platTrxNo);
        dto.setReceiptAmount(receiptAmount);
        dto.setTotalAmount(totalAmount);
        dto.setBuyerPayAmount(buyerPayAmount);
        dto.setOrderNo(orderNo);

        dto.setStatus(getPlatStatus(execute.getResultCode()));
        dto.setBankTrxNo(bankTrxNo);

        dto.setBankReturnMsg(execute.toString());
        return dto;
    }

    private boolean isSuccess(Map<String, String> result) {
        return SUCCESS.equals(result.get("result_code"));
    }

    private String toJsonStr(Map<String, String> params) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            logger.warn("tojson error:{}", params);
        }
        return null;
    }

    /**
     * 将支付金额转换成int
     *
     * @return
     */
    public Integer convert2Int(String amountString) {
        return Integer.valueOf(amountString);
    }

    private String getPlatTradeState(String weixinState) {

        switch (weixinState) {
            case TRADE_STATE_USERPAYING:

            case TRADE_STATE_NOTPAY:
                return TradeStatusEnum.WAITING_PAYMENT.name();
            case TRADE_STATE_CLOSED:
                return TradeStatusEnum.TIME_OUT.name();
            case TRADE_STATE_REFUND:
            case TRADE_STATE_PAYERROR:
                return TradeStatusEnum.FAILED.name();
        }
        throw new IllegalArgumentException("微信状态不详：" + weixinState);
    }
}
