package com.feitian.trade.sync.dao;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import com.feitian.trade.sync.dao.provider.OrderDaoProvider;
import com.feitian.trade.sync.model.TbOrder;

public interface OrderMapper {
    @InsertProvider(type = OrderDaoProvider.class, method = "saveAll")
    void saveAll(@Param("list") List<TbOrder> orders);
}
