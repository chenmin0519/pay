/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhonghuilv.aitravel.pay.intf.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能说明:订单状态枚举类</b>
 */
public enum TradeStatusEnum {

    /**
     * 交易成功
     */
    SUCCESS("交易成功"),

    /**
     * 交易失败
     */
    FAILED("交易失败"),

    /**
     * 订单超时
     */
    TIME_OUT("订单超时"),
    /**
     * 订单已创建
     */
    CREATED("订单已创建"),

    /**
     * 订单已取消
     */
    CANCELED("订单已取消"),

    /**
     * 等待支付
     */
    WAITING_PAYMENT("等待支付"),

    REFUND_AUDIT("退款审核中"),
    REFUND_SUCCESS("退款成功"),
    REFUND_ERROR("退款失败");

    /**
     * 描述
     */
    private String desc;

    private TradeStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, Map<String, Object>> toMap() {
        TradeStatusEnum[] ary = TradeStatusEnum.values();
        Map<String, Map<String, Object>> enumMap = new HashMap<>();
        for (int num = 0; num < ary.length; num++) {
            Map<String, Object> map = new HashMap<>();
            String key = ary[num].name();
            map.put("desc", ary[num].getDesc());
            enumMap.put(key, map);
        }
        return enumMap;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List toList() {
        TradeStatusEnum[] ary = TradeStatusEnum.values();
        List list = new ArrayList();
        for (int i = 0; i < ary.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("desc", ary[i].getDesc());
            map.put("name", ary[i].name());
            list.add(map);
        }
        return list;
    }

}
