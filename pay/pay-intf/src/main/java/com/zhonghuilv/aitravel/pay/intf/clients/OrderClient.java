package com.zhonghuilv.aitravel.pay.intf.clients;

import com.zhonghuilv.aitravel.pay.intf.PayConstant;
import com.zhonghuilv.aitravel.pay.intf.pojo.YOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.YOrderDetail;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.YOrderDetailPo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author chenmin
 * @title: OrderClient
 * @projectName pay
 * @description: TODO
 * @date 2019-05-2417:28
 * @Version: 1.0
 * @JDK: 10
 */
@FeignClient(value = PayConstant.SERVICE_NAME)
@RequestMapping("/order")
public interface OrderClient{

    @RequestMapping(value = "/_query_order_by_id",method = RequestMethod.GET)
    @ApiOperation("根据订单id查询订单")
    YOrderDetailPo queryOrderById(@RequestParam("id") Long id);

    @RequestMapping(value = "/_query_order_by_order_num",method = RequestMethod.GET)
    @ApiOperation("根据订单编号查询订单")
    YOrder queryOrderByOrderNum(@RequestParam("orderNum")String orderNum);
}
