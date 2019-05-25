package com.zhonghuilv.aitravel.common.util;


import com.zhonghuilv.aitravel.pay.intf.common.pojo.po.MainPO;

import java.time.LocalDateTime;
import java.util.Objects;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class MainPOUtil {
    private MainPOUtil(){}

    public static void initAdd(MainPO po, Long userId) {

        if (Objects.isNull(po.getCreateTime()))
            po.setCreateTime(LocalDateTime.now());
        if (Objects.isNull(po.getCreator()))
            po.setCreator(userId);
        if (Objects.isNull(po.getEditTime()))
            po.setEditTime(LocalDateTime.now());
        if (Objects.isNull(po.getEditor()))
            po.setEditor(userId);
    }

    public static void initUpdate(MainPO po, Long userId) {
        if (Objects.isNull(po.getEditTime()))
            po.setEditTime(LocalDateTime.now());
        if (Objects.isNull(po.getEditor()))
            po.setEditor(userId);
    }
}
