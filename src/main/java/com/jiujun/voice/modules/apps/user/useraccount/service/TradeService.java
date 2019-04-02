package com.jiujun.voice.modules.apps.user.useraccount.service;

import java.util.List;

import com.jiujun.voice.modules.apps.user.useraccount.domain.TradeRecord;

public interface TradeService {

	/**
	 * 变更金币
	 * @param userId
	 * @param gold 需要增加的金币  扣款传负数
	 * @param remark 交易描述
	 * @return 大于0，成功，否则失败
	 */
	Long changeGold(String userId, int gold, String remark);

	/**
	 * 变更钻石
	 * @param userId
	 * @param jewel 需要增加的钻石  扣款传负数
	 * @param remark 交易描述
	 * @return
	 */
	Long changeJewel(String userId, int jewel, String remark);
	
	/**
	 * 变更元宝
	 * @param userId
	 * @param ingot 需要增加的元宝  扣款传负数
	 * @param remark 交易描述
	 * @return
	 */
	Long changeIngot(String userId, int ingot, String remark);
	
	/**
	 * 查询某日的交易记录
	 * @param dayCode 日期编码  DateUtils.getDayCode()
	 * @param currencyType 币种类型
	 * @param tradeType 交易类型   1扣款 2加款
	 * @param line 查询行数
	 * @return
	 */
	List<TradeRecord> getTradeRecord(String dayCode,Integer currencyType,Integer tradeType,Integer line);
}
