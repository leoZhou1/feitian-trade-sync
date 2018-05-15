package com.feitian.trade.sync.mapper;

import com.feitian.trade.sync.model.Trade;

public interface TradeMapper {
    int deleteByPrimaryKey(String tid);

    int insert(Trade record);

    int insertSelective(Trade record);

    Trade selectByPrimaryKey(String tid);

    int updateByPrimaryKeySelective(Trade record);

    int updateByPrimaryKeyWithBLOBs(Trade record);

    int updateByPrimaryKey(Trade record);
}