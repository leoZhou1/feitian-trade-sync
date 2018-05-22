package com.feitian.trade.sync.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.feitian.trade.sync.model.BaseThirdUser;
import com.feitian.trade.sync.model.TbOrder;
import com.feitian.trade.sync.model.TbTrade;
import com.feitian.trade.sync.model.TbUser;
import com.feitian.trade.sync.service.IImportOrderService;
import com.feitian.trade.sync.service.ITbOrderService;
import com.feitian.trade.sync.third.taobao.TaobaoRespFieldsConfig;
import com.feitian.trade.sync.third.taobao.po.EOrderStatusCode;
import com.taobao.api.domain.Trade;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;

@Service
public class TbImportOrderService implements IImportOrderService {
    private static final long DEFAULT_PAGE_SIZE = 50;
    private final ITbOrderService orderService;

    @Autowired
    public TbImportOrderService(ITbOrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public List<TbOrder> importByUser(BaseThirdUser baseThirdUser, Date start, Date end, boolean isInc) {
        List<TbOrder> orders = new ArrayList<>();
        if (!(baseThirdUser instanceof TbUser)) {
            return orders;
        }
        TbUser user = (TbUser) baseThirdUser;
        try {
            long total = this.getTotal(user, start, end, isInc);
            if (total == 0) {
                return orders;
            }
            long totalPage = (total % DEFAULT_PAGE_SIZE)==0?total / DEFAULT_PAGE_SIZE:total / DEFAULT_PAGE_SIZE + 1;
            System.out.println("======"+totalPage);
            List<Trade> trades;
            if (isInc) {
                trades = this.getTradesIncrement(user, start, end, totalPage, total);
            } else {
                trades = this.getTrades(user, start, end, totalPage, total);
            }
            orders = this.transformTradeToOrder(trades, user);
        } catch (Exception e) {
            // TODO: Logger
        }
        return orders;
    }

    @Override
    public long getTotal(BaseThirdUser baseThirdUser, Date start, Date end, boolean isInc) {
        if (!(baseThirdUser instanceof TbUser)) {
            return 0L;
        }
        TbUser user = (TbUser) baseThirdUser;
        if (isInc) {
            TradesSoldIncrementGetRequest request = new TradesSoldIncrementGetRequest();
            request.setStartModified(start);
            request.setEndModified(end);
            request.setStatus(EOrderStatusCode.WAIT_BUYER_CONFIRM_GOODS.toString());
            request.setFields(TaobaoRespFieldsConfig.ORDER_ID);
            request.setPageSize(1L);
            request.setPageNo(1L);
            return orderService.getTaobaoIncrementOrderTotal(request, user);
        } else {
            TradesSoldGetRequest request = new TradesSoldGetRequest();
            request.setStartCreated(start);
            request.setEndCreated(end);
            request.setStatus(EOrderStatusCode.WAIT_BUYER_CONFIRM_GOODS.toString());
            request.setFields(TaobaoRespFieldsConfig.ORDER_ID);
            request.setPageSize(1L);
            request.setPageNo(1L);
            return orderService.getTaobaoOrderTotal(request, user);
        }
    }

    private List<Trade> getTradesIncrement(TbUser user, Date start, Date end, long totalPage, long total) {
        TradesSoldIncrementGetRequest request = new TradesSoldIncrementGetRequest();
        request.setStartModified(start);
        request.setEndModified(end);
        request.setStatus(EOrderStatusCode.WAIT_BUYER_CONFIRM_GOODS.toString());
        request.setFields(TaobaoRespFieldsConfig.ORDER_IMPORT_FIELDS);
        request.setPageSize(DEFAULT_PAGE_SIZE);

        List<Trade> trades = new ArrayList<>((int) total);
        while (totalPage >= 1) {
            request.setPageNo(totalPage);
            trades.addAll(orderService.getTradeIncrementList(request, user));
            totalPage--;
        }
        return trades;
    }

    private List<Trade> getTrades(TbUser user, Date start, Date end, long totalPage, long total) {
        TradesSoldGetRequest request = new TradesSoldGetRequest();
        request.setStartCreated(start);
        request.setEndCreated(end);
        request.setStatus(EOrderStatusCode.WAIT_BUYER_CONFIRM_GOODS.toString());
        request.setFields(TaobaoRespFieldsConfig.ORDER_IMPORT_FIELDS);
        request.setPageSize(DEFAULT_PAGE_SIZE);

        List<Trade> trades = new ArrayList<>((int) total);
        while (totalPage >= 1) {
            request.setPageNo(totalPage);
            trades.addAll(orderService.getTradeList(request, user));
            totalPage--;
        }
        return trades;
    }

    private List<TbOrder> transformTradeToOrder(List<Trade> trades, TbUser user) {
        List<TbOrder> orders = new ArrayList<>(trades.size());
        for (Trade trade : trades) {
        	List<com.taobao.api.domain.Order> list=trade.getOrders();
        	if(null!=list&&!list.isEmpty()) {
        		for (int i = 0; i < list.size(); i++) {
        			orders.add(createTbOrder(list.get(i)));
				}
        	}
        	
        	@SuppressWarnings("unused")
			TbTrade tbTrade=createTbTrade(trade);
        	//保存Trade对象
        }
        return orders;
    }
    
    
    private TbTrade createTbTrade(Trade trade) {
    	TbTrade tbTrade=new TbTrade();
    	tbTrade.setTid(trade.getTid());
    	tbTrade.setSellerNick(trade.getSellerNick());
    	tbTrade.setBuyerNick(trade.getBuyerNick());
    	tbTrade.setTitle(trade.getTitle());
    	tbTrade.setType(trade.getType());
//    	tbTrade.setRe(trade.getSellerNick());//退款状态
    	tbTrade.setCreated(trade.getCreated());
    	tbTrade.setIid(trade.getIid());
    	tbTrade.setPrice(trade.getPrice());
    	tbTrade.setPicPath(trade.getPicPath());
    	tbTrade.setNum(trade.getNum());
    	tbTrade.setBuyerMessage(trade.getBuyerMessage());
    	tbTrade.setSid(trade.getSid());
    	tbTrade.setShippingType(trade.getShippingType());
    	tbTrade.setAlipayNo(trade.getAlipayNo());
    	tbTrade.setPayment(trade.getPayment());
    	tbTrade.setDiscountFee(trade.getDiscountFee());
    	tbTrade.setAdjustFee(trade.getAdjustFee());
    	tbTrade.setSnapshotUrl(trade.getSnapshotUrl());
    	tbTrade.setStatus(trade.getStatus());
    	tbTrade.setSellerRate(trade.getSellerRate());
    	tbTrade.setBuyerMemo(trade.getBuyerMemo());
    	tbTrade.setSellerMemo(trade.getSellerMemo());
    	tbTrade.setPayTime(trade.getPayTime());
    	tbTrade.setEndTime(trade.getEndTime());
    	tbTrade.setModified(trade.getModified());
    	tbTrade.setBuyerObtainPointFee(trade.getBuyerObtainPointFee());
    	tbTrade.setPointFee(trade.getPointFee());
    	tbTrade.setRealPointFee(trade.getRealPointFee());
    	tbTrade.setTotalFee(trade.getTotalFee());
    	tbTrade.setPostFee(trade.getPostFee());
    	tbTrade.setBuyerAlipayNo(trade.getBuyerAlipayNo());
    	tbTrade.setReceiverName(trade.getReceiverName());
    	tbTrade.setReceiverState(trade.getReceiverState());
    	tbTrade.setReceiverCity(trade.getReceiverCity());
    	tbTrade.setReceiverDistrict(trade.getReceiverDistrict());
    	tbTrade.setReceiverAddress(trade.getReceiverAddress());
    	tbTrade.setReceiverZip(trade.getReceiverZip());
    	return tbTrade;
    }
    
    
    private TbOrder createTbOrder(com.taobao.api.domain.Order order) {
    	TbOrder tbOrder = new TbOrder();
    	tbOrder.setOid(order.getOid());
    	tbOrder.setAdjustFee(order.getAdjustFee());
    	tbOrder.setBuyerRate(order.getBuyerRate());
    	tbOrder.setCid(order.getCid());
    	tbOrder.setConsignTime(order.getConsignTime());
    	tbOrder.setDiscountFee(order.getDiscountFee());
    	tbOrder.setDivideOrderFee(order.getDivideOrderFee());
    	tbOrder.setEndTime(order.getEndTime());
    	tbOrder.setInvoiceNo(order.getInvoiceNo());
    	tbOrder.setIsDaixiao(order.getIsDaixiao());
    	tbOrder.setLogisticsCompany(order.getLogisticsCompany());
    	tbOrder.setNum(order.getNum());
    	tbOrder.setNumIid(order.getNumIid());
    	tbOrder.setOuterIid(order.getOuterIid());
    	tbOrder.setOuterSkuId(order.getOuterSkuId());
    	tbOrder.setPartMjzDiscount(order.getPartMjzDiscount());
    	tbOrder.setPayment(order.getPayment());
    	tbOrder.setPicPath(order.getPicPath());
    	tbOrder.setPrice(order.getPrice());
    	tbOrder.setRefundStatus(order.getRefundStatus());
    	tbOrder.setSellerRate(order.getSellerRate());
    	tbOrder.setSellerType(order.getSellerType());
    	tbOrder.setShippingType(order.getShippingType());
    	tbOrder.setSkuId(order.getSkuId());
    	tbOrder.setSkuPropertiesName(order.getSkuPropertiesName());
    	tbOrder.setStatus(order.getStatus());
    	tbOrder.setTitle(order.getTitle());
    	tbOrder.setTotalFee(order.getTotalFee());
    	tbOrder.setSellerNick(order.getSellerNick());
    	tbOrder.setBuyerNick(order.getBuyerNick());
    	return tbOrder;
    }
}
