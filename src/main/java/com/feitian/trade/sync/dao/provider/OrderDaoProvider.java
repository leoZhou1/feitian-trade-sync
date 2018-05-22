package com.feitian.trade.sync.dao.provider;

import java.util.List;
import java.util.Map;

import com.feitian.trade.sync.model.TbOrder;

public class OrderDaoProvider {
    public String saveAll(Map map) {
        List<TbOrder> configs = (List<TbOrder>) map.get("list");
        StringBuilder sb = new StringBuilder();
        sb.append("insert into `tb_order` ");
        sb.append("(oid ,adjust_fee ,buyer_rate,cid ,consign_time,discount_fee,"
        		+ "divide_order_fee ,end_time,invoice_no,is_daixiao ,logistics_company ,"
        		+ "num,num_iid,outer_iid,outer_sku_id,part_mjz_discount ,payment ,pic_path,"
        		+ "price,refund_status,seller_rate ,seller_type,shipping_type ,sku_id,"
        		+ "sku_properties_name ,status,title,total_fee,seller_nick,buyer_nick,wholesalers_id)");
        sb.append(" values ");
        String columns = "(#{list[{index}].oid}, #{list[{index}].adjustFee}, #{list[{index}].buyerRate}, "
        		+ "#{list[{index}].cid}, #{list[{index}].consignTime}, #{list[{index}].discountFee}, #{list[{index}].divideOrderFee}," +
                "#{list[{index}].endTime}, #{list[{index}].invoiceNo}, #{list[{index}].isDaixiao}, #{list[{index}].logisticsCompany},"
                + " #{list[{index}].num}, #{list[{index}].numIid}, #{list[{index}].outerIid}," 
                + " #{list[{index}].outerSkuId}, #{list[{index}].partMjzDiscount}, #{list[{index}].payment}," 
                + " #{list[{index}].picPath}, #{list[{index}].price}, #{list[{index}].refundStatus}," 
                + " #{list[{index}].sellerRate}, #{list[{index}].sellerType}, #{list[{index}].shippingType}," 
                + " #{list[{index}].skuId}, #{list[{index}].skuPropertiesName}, #{list[{index}].status}," 
                + " #{list[{index}].title}, #{list[{index}].totalFee}, #{list[{index}].sellerNick}," 
                +", #{list[{index}].buyerNick}, #{list[{index}].wholesalersd})";
        for (int i = 0; i < configs.size(); i++) {
            sb.append(columns.replaceAll("\\{index}", String.valueOf(i)));
            if (i < configs.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
}
