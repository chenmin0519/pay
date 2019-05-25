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
 * 积分变动方向
 */
public enum AccountFundDirectionEnum {

    ADD("增加"),

    SUB("减少");

    /**
     * 描述
     */
    private String desc;

    AccountFundDirectionEnum(String label) {
        this.desc = label;
    }

    public String getDesc() {
        return desc;
    }

    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = new ArrayList<>();
        AccountFundDirectionEnum[] val = AccountFundDirectionEnum.values();
        for (AccountFundDirectionEnum e : val) {
            Map<String, Object> map = new HashMap<>();
            map.put("desc", e.getDesc());
            map.put("name", e.name());
            list.add(map);
        }
        return list;
    }

    public static AccountFundDirectionEnum getEnum(String name) {
        AccountFundDirectionEnum resultEnum = null;
        AccountFundDirectionEnum[] enumAry = AccountFundDirectionEnum.values();
        for (int i = 0; i < enumAry.length; i++) {
            if (enumAry[i].name().equals(name)) {
                resultEnum = enumAry[i];
                break;
            }
        }
        return resultEnum;
    }

}
