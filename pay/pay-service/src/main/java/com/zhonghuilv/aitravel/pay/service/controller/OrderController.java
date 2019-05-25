package com.zhonghuilv.aitravel.pay.service.controller;

import com.zhonghuilv.aitravel.pay.intf.clients.OrderClient;
import com.zhonghuilv.aitravel.pay.intf.pojo.YOrder;
import com.zhonghuilv.aitravel.pay.intf.pojo.YOrderDetail;
import com.zhonghuilv.aitravel.pay.intf.pojo.dto.YOrderDetailPo;
import com.zhonghuilv.aitravel.pay.service.mapper.OrderDetailMapper;
import com.zhonghuilv.aitravel.pay.service.mapper.OrderMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenmin
 * @title: OrderController
 * @projectName pay
 * @description: TODO
 * @date 2019-05-2417:20
 * @Version: 1.0
 * @JDK: 10
 */
@RestController
@RequestMapping("/order")
@Api(value = "OrderController", tags = "订单")
@Slf4j
public class OrderController implements OrderClient {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    @RequestMapping(value = "/_query_order_by_id",method = RequestMethod.GET)
    @ApiOperation("根据订单id查询订单")
    public YOrderDetailPo queryOrderById(Long id) {
        YOrderDetailPo result = new YOrderDetailPo();
        YOrder par = new YOrder();
        par.setId(id);
        YOrder yOrder = orderMapper.selectOne(par);
        BeanUtils.copyProperties(yOrder,result);
        YOrderDetail yOrderDetail = new YOrderDetail();
        yOrderDetail.setOrderId(id);
        List<YOrderDetail> yOrderDetails = orderDetailMapper.select(yOrderDetail);
        result.setyOrderDetails(yOrderDetails);
        return result;
    }

    @Override
    @RequestMapping(value = "/_query_order_by_order_num",method = RequestMethod.GET)
    @ApiOperation("根据订单id查询订单")
    public YOrder queryOrderByOrderNum(String orderNum) {
        YOrder order = new YOrder();
        order.setOrderNum(orderNum);
        return orderMapper.selectOne(order);
    }
}
