package com.zhonghuilv.aitravel.pay.service.supports.wechat.service;

import org.junit.Test;

import java.util.stream.Stream;

/**
 * Create By zhengjing on 2018/7/5 09:44
 */
public class WechatRefoundServiceTest {

    @Test
    public void test() {

        String wechatrsp = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg" +
                "><appid><![CDATA[wx91e10e94eedbaa81]]></appid><mch_id><![CDATA[1499217242]]></mch_id><nonce_str" +
                "><![CDATA[SZ6PhS90QDOJXwwI]]></nonce_str><sign><![CDATA[80D567113DA2451165A67573A44EFC68]]></sign" +
                "><result_code><![CDATA[SUCCESS]]></result_code><transaction_id><![CDATA[4200000118201807030897836909" +
                "]]></transaction_id><out_trade_no><![CDATA[77772018070310000632]]></out_trade_no><out_refund_no" +
                "><![CDATA[REFUND2018070510000002]]></out_refund_no><refund_id><![CDATA[50000507142018070505268465131" +
                "]]></refund_id><refund_channel><![CDATA[]]></refund_channel><refund_fee>1</refund_fee" +
                "><coupon_refund_fee>0</coupon_refund_fee><total_fee>1</total_fee><cash_fee>1</cash_fee" +
                "><coupon_refund_count>0</coupon_refund_count><cash_refund_fee>1</cash_refund_fee></xml>";
    }


    public void rx() {

        String str = "";

        Stream.of("")
                .map(String::toLowerCase);
    }
}
