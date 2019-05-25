package com.zhonghuilv.aitravel.pay.service.mapper;

import com.zhonghuilv.aitravel.pay.intf.pojo.YOrder;
import com.zhonghuilv.aitravel.service.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenmin
 * @title: OrderMapper
 * @projectName pay
 * @description: TODO
 * @date 2019-05-2417:19
 * @Version: 1.0
 * @JDK: 10
 */
@Mapper
public interface OrderMapper extends CommonMapper<YOrder> {
}
