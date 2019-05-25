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
package com.zhonghuilv.aitravel.pay.intf.pojo;

import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.BasePO;
import lombok.Data;

/**
 * 此实体没有关联的表，只作用于序列查找时传参使用
 */
@Data
public class SeqBuild extends BasePO {
    private static final long serialVersionUID = 1L;
    /**
     * 序列名称
     **/
    private String seqName;
}