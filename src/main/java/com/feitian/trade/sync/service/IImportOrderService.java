package com.feitian.trade.sync.service;

import com.feitian.trade.sync.model.BaseThirdUser;
import com.feitian.trade.sync.model.Order;

import java.util.Date;
import java.util.List;

public interface IImportOrderService {
    List<Order> importByUser(BaseThirdUser user, Date start, Date end, boolean isInc);
    long getTotal(BaseThirdUser baseThirdUser, Date start, Date end, boolean isInc);
}
