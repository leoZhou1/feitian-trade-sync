package com.feitian.trade.sync.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feitian.trade.sync.dao.OrderMapper;
import com.feitian.trade.sync.model.TbOrder;

@Service
public class LocalOrderService {
    private final OrderMapper orderMapper;

    @Autowired
    public LocalOrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public void saveOrders(List<TbOrder> orderList) {
        orderMapper.saveAll(orderList);
    }
}
