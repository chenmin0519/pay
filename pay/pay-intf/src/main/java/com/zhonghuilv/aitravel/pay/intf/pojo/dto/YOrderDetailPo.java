package com.zhonghuilv.aitravel.pay.intf.pojo.dto;

import com.zhonghuilv.aitravel.pay.intf.pojo.YOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.YOrderDetail;

import java.util.List;

/**
 * @author chenmin
 * @title: YOrderDetailPo
 * @projectName shop-springboot
 * @description: TODO
 * @date 2019-05-1917:36
 * @Version: 1.0
 * @JDK: 10
 */
public class YOrderDetailPo extends YOrder {
    private List<YOrderDetail> yOrderDetails;

    public List<YOrderDetail> getyOrderDetails() {
        return yOrderDetails;
    }

    public void setyOrderDetails(List<YOrderDetail> yOrderDetails) {
        this.yOrderDetails = yOrderDetails;
    }
}
