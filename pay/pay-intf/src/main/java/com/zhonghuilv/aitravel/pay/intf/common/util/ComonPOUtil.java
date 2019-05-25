package com.zhonghuilv.aitravel.pay.intf.common.util;


import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.ComonPo;

import java.time.LocalDateTime;
import java.util.Objects;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class ComonPOUtil {


    public static void initAdd(ComonPo po, Long userId) {

        if (Objects.isNull(po.getCreateTime()))
            po.setCreateTime(LocalDateTime.now());
        if (Objects.isNull(po.getCreator()))
            po.setCreator(userId);
        if (Objects.isNull(po.getEditTime()))
            po.setEditTime(LocalDateTime.now());
        if (Objects.isNull(po.getEditor()))
            po.setEditor(userId);
    }

    public static void initUpdate(ComonPo po, Long userId) {
        if (Objects.isNull(po.getEditTime()))
            po.setEditTime(LocalDateTime.now());
        if (Objects.isNull(po.getEditor()))
            po.setEditor(userId);
    }
}
