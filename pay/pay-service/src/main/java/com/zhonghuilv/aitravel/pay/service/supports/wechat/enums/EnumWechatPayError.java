package com.zhonghuilv.aitravel.pay.service.supports.wechat.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
    * @Description: 微信错误
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@AllArgsConstructor
@Getter
public enum EnumWechatPayError {
    SYSTEMERROR("接口返回错误", "系统超时", "请不要更换商户退款单号，请使用相同参数再次调用API。"),
    BIZERR_NEED_RETRY("退款业务流程错误，需要商户触发重试来解决 ", "并发情况下，业务被拒绝，商户重试即可解决", "请不要更换商户退款单号，请使用相同参数再次调用API。"),
    TRADE_OVERDUE("订单已经超过退款期限", "订单已经超过可退款的最大期限(支付后一年内可退款)", "请选择其他方式自行退款"),
    ERROR("业务错误", "申请退款业务发生错误", "该错误都会返回具体的错误原因，请根据实际返回做相应处理。"),
    USER_ACCOUNT_ABNORMAL("退款请求失败", "用户帐号注销", "此状态代表退款申请失败，商户可自行处理退款。"),
    INVALID_REQ_TOO_MUCH("无效请求过多", "连续错误请求数过多被系统短暂屏蔽 ", "请检查业务是否正常，确认业务正常后请在1分钟后再来重试"),
    NOTENOUGH("余额不足", "商户可用退款余额不足", "此状态代表退款申请失败，商户可根据具体的错误提示做相应的处理。"),
    INVALID_TRANSACTIONID("无效transaction_id", "请求参数未按指引进行填写", "请求参数错误，检查原交易号是否存在或发起支付交易接口返回失败"),
    PARAM_ERROR("参数错误", "请求参数未按指引进行填写", "请求参数错误，请重新检查再调用退款申请"),
    APPID_NOT_EXIST("APPID不存在", "参数中缺少APPID", "请检查APPID是否正确"),
    MCHID_NOT_EXIST("MCHID不存在", "参数中缺少MCHID", "请检查MCHID是否正确"),
    REQUIRE_POST_METHOD("请使用post方法", "未使用post传递参数 ", "请检查请求参数是否通过post方法提交"),
    SIGNERROR("签名错误", "参数签名结果不正确", "请检查签名参数和方法是否都符合签名算法要求"),
    XML_FORMAT_ERROR("XML格式错误", "XML格式错误", "请检查XML参数格式是否正确"),
    FREQUENCY_LIMITED("频率限制", "2个月之前的订单申请退款有频率限制 ", "该笔退款未受理，请降低频率后重试");

    private String desc;
    private String reason;
    private String solution;


}
