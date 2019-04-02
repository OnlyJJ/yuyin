package com.jiujun.voice.modules.apps.pay.service;

import java.util.Map;

import com.jiujun.voice.modules.apps.pay.domain.PayAppleProduct;
import com.jiujun.voice.modules.apps.pay.domain.PayConfig;
import com.jiujun.voice.modules.apps.pay.domain.PayRecord;
import com.jiujun.voice.modules.apps.pay.schema.AliPaySchema;
import com.jiujun.voice.modules.apps.pay.schema.WxPaySchema;

/**
 * 
 * @author Coody
 *
 */
public interface PayService {

	/**
	 * 
	 * @param type
	 *            1微信 2支付宝
	 * @return
	 */
	public PayConfig getPayConfig(Integer type);
	
	
	/**
	 * 创建支付宝订单
	 */
	public AliPaySchema createAlipayOrder(String userId,Double price,String domain);
	
	/**
	 * 微信订单
	 */
	public WxPaySchema createWxOrder(String userId,Double price,String tradeType,String domain,String clientIp);
	
	/**
	 * 苹果订单
	 */
	public PayRecord createAppletOrder(String userId,Double price);
	
	/**
	 * 处理支付内容 
	 * @param payId  本系统支付ID
	 * @param tradeNo 第三方交易号
	 * @param price 金额
	 * @return
	 */
	public Long doTrade(Long payId,String tradeNo,Double price,Integer gold);
	
	/**
	 * 获取交易信息
	 */
	public PayRecord getPayRecord(Long payId);
	
	/**
	 * 获取交易信息
	 * @param transNo
	 * @param type 1微信 2支付宝 3苹果支付
	 * @return
	 */
	public PayRecord getPayRecordByTransNo(String transNo,Integer type);
	
	/**
	 * 獲取蘋果支付產品列表
	 */
	public Map<String, PayAppleProduct> getPayAppleProducts() ;
	
	/**
	 * 根据用户Id获取充值记录
	 * @param userId
	 * @return
	 */
	public PayRecord getPayRecordByUserId(String userId);
	
}
