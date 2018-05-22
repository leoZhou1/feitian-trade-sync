package com.feitian.trade.sync.service;

import com.feitian.trade.sync.model.BaseThirdUser;
import com.feitian.trade.sync.model.TbOrder;

import java.util.Date;
import java.util.List;

public interface IImportOrderService {
    List<TbOrder> importByUser(BaseThirdUser user, Date start, Date end, boolean isInc);
    long getTotal(BaseThirdUser baseThirdUser, Date start, Date end, boolean isInc);
}
