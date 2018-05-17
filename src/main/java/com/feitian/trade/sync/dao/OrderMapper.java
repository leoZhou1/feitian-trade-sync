package com.feitian.trade.sync.dao;

import com.feitian.trade.sync.dao.provider.OrderDaoProvider;
import com.feitian.trade.sync.model.Order;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    @InsertProvider(type = OrderDaoProvider.class, method = "saveAll")
    void saveAll(@Param("list") List<Order> orders);
}
