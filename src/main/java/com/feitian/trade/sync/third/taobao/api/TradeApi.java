package com.feitian.trade.sync.third.taobao.api;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feitian.trade.sync.model.TbUser;
import com.feitian.trade.sync.third.taobao.ClientFactory;
import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.RefundsReceiveGetRequest;
import com.taobao.api.request.TradeFullinfoGetRequest;
import com.taobao.api.request.TradeGetRequest;
import com.taobao.api.request.TradeMemoUpdateRequest;
import com.taobao.api.request.TradeReceivetimeDelayRequest;
import com.taobao.api.request.TraderatesGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.request.TradesSoldIncrementGetRequest;
import com.taobao.api.response.RefundsReceiveGetResponse;
import com.taobao.api.response.TradeFullinfoGetResponse;
import com.taobao.api.response.TradeGetResponse;
import com.taobao.api.response.TradeMemoUpdateResponse;
import com.taobao.api.response.TradeReceivetimeDelayResponse;
import com.taobao.api.response.TraderatesGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;
import com.taobao.api.response.TradesSoldIncrementGetResponse;

public class TradeApi extends BaseTbApi {
	Logger log=LoggerFactory.getLogger(TradeApi.class);
	
	public TradeApi(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	/**
	 * @info 搜索当前会话用户作为卖家已卖出的交易数据（只能获取到三个月以内的交易信息）
	 * @param request
	 * @param user
	 * @return
	 */
	public TradesSoldGetResponse tradesSoldGet(TradesSoldGetRequest request, TbUser user) {
		TaobaoClient client = clientFactory.getClient("json");
//		TradesSoldGetRequest req = new TradesSoldGetRequest();
		request.setType("fixed,auction,step,guarantee_trade,independent_simple_trade,independent_shop_trade,auto_delivery,ec,cod,fenxiao,game_equipment,shopex_trade,netcn_trade,external_trade,instant_trade,b2c_cod,hotel_trade,super_market_trade,super_market_cod_trade,taohua,waimai,nopaid,tmall_i18n,nopaid");
		System.out.println("TradesSoldGet param:"+new Gson().toJson(request));
		TradesSoldGetResponse resp = null;
		try {
			resp = client.execute(request, user.getSessionKey());
			System.out.println("TradesSoldGet result:"+new Gson().toJson(resp));
			//检查sessionKey是否过期
			this.sessionIsValid(resp, user);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * @info 增量搜索当前会话用户作为卖家已卖出的交易数据
	 * @param request
	 * @param user
	 * @return
	 */
	public TradesSoldIncrementGetResponse tradesSoldIncrementGet(TradesSoldIncrementGetRequest request, TbUser user) {
		TaobaoClient client = clientFactory.getClient("json");
		request.setType("fixed,auction,,step,guarantee_trade,independent_simple_trade,independent_shop_trade,auto_delivery,ec,cod,fenxiao,game_equipment,shopex_trade,netcn_trade,external_trade,instant_trade,b2c_cod,hotel_trade,super_market_trade,super_market_cod_trade,taohua,waimai,nopaid,tmall_i18n,nopaid");

		TradesSoldIncrementGetResponse resp = null;
		try {
			resp = client.execute(request, user.getSessionKey());
			//检查sessionKey是否过期
			this.sessionIsValid(resp, user);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resp;
	}

	public TradeMemoUpdateResponse updateTradeMemo(long tid, long flag, TbUser user) {
		TaobaoClient client = clientFactory.getClient("json");
		TradeMemoUpdateRequest req = new TradeMemoUpdateRequest();
		req.setTid(tid);
		req.setFlag(flag);

		TradeMemoUpdateResponse resp = null;
		try {
			resp = client.execute(req, user.getSessionKey());
			//检查sessionKey是否过期
			this.sessionIsValid(resp, user);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 获取单笔交易的部分信息（性能高）
	 */
	public TradeGetResponse getTradeGet(String fields, long tid, TbUser user) {
		TaobaoClient client = clientFactory.getClient("json");
		TradeGetRequest req = new TradeGetRequest();
		req.setFields(fields);
		req.setTid(tid);

		TradeGetResponse resp = null;
		try {
			resp = client.execute(req, user.getSessionKey());
			//检查sessionKey是否过期
			this.sessionIsValid(resp, user);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 获取单笔交易的详细信息
	 */
	public TradeFullinfoGetResponse getFullinfo(String fields, long tid, TbUser user) {
		TaobaoClient client = clientFactory.getClient("json");
		TradeFullinfoGetRequest req = new TradeFullinfoGetRequest();
		req.setFields(fields);
		req.setTid(tid);

		TradeFullinfoGetResponse resp = null;
		try {
			resp = client.execute(req, user.getSessionKey());
			//检查sessionKey是否过期
			this.sessionIsValid(resp, user);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 搜索评价信息
	 */
	public TraderatesGetResponse getTradeRate(String fields, String rateType, String role, Long tid, TbUser user) {
		TaobaoClient client = clientFactory.getClient("json");
		TraderatesGetRequest req = new TraderatesGetRequest();
		req.setFields(fields);
		req.setRateType(rateType);
		req.setRole(role);
		req.setTid(tid);
		TraderatesGetResponse resp = null;
		try {
			resp = client.execute(req, user.getSessionKey());
			//检查sessionKey是否过期
			this.sessionIsValid(resp, user);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * 延长交易收货时间
	 *
	 */
	public TradeReceivetimeDelayResponse tradeReceiveTimeDelay(long tid, long days) {
		TaobaoClient client = clientFactory.getClient("json");
		TradeReceivetimeDelayRequest req = new TradeReceivetimeDelayRequest();
		req.setTid(tid);
		req.setDays(days);
		req.setTimestamp(System.currentTimeMillis());

		TradeReceivetimeDelayResponse resp = null;
		try {
			resp = client.execute(req);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resp;
	}

	public RefundsReceiveGetResponse getRefundsReceive(String fields, String status, long pageSize, TbUser user) {
		TaobaoClient client = clientFactory.getClient("json");
		RefundsReceiveGetRequest req = new RefundsReceiveGetRequest();
		req.setFields(fields);
		req.setStatus(status);
		req.setPageNo(1L);
		req.setPageSize(pageSize);
		req.setUseHasNext(false);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.HOUR_OF_DAY, -1);
		req.setStartModified(cal.getTime());
		RefundsReceiveGetResponse resp = null;
		
		try {
			resp = client.execute(req, user.getSessionKey());
			//检查sessionKey是否过期
			this.sessionIsValid(resp, user);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return resp;
	}
}
