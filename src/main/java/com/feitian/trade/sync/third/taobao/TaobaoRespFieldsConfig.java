package com.feitian.trade.sync.third.taobao;

public class TaobaoRespFieldsConfig {
	public final static String ORDER_ID = "tid";
	public final static String ORDER_IMPORT_FIELDS =
		"oid,adjust_fee ,buyer_rate,cid ,consign_time,discount_fee,divide_order_fee ,end_time,invoice_no,is_daixiao ,logistics_company ,num,num_iid,outer_iid,outer_sku_id,part_mjz_discount ,payment ,pic_path,price,refund_status,seller_rate ,seller_type,shipping_type ,sku_id,sku_properties_name ,status,title,total_fee,seller_nick,buyer_nick,wholesalers_id";
	public final static String LOGISTICS_ORDER_FIELDS = "out_sid,company_name";
	public final static String ORDER_REQUEST_FIELDS = "tid, buyer_nick, created, receiver_mobile, num_iid, receiver_name";
	public final static String ORDER_CONTACTS_FIELDS = "tid, buyer_nick, created, payment, receiver_city, receiver_mobile, receiver_name, receiver_state, orders.title, orders.num_iid";
	public final static String ORDER_URGE_FIELDS = "tid, receiver_mobile, receiver_name, created, buyer_nick, payment";
}
