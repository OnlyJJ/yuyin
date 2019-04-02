package com.jiujun.voice.modules.apps.user.useraccount.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.modules.apps.user.useraccount.domain.TradeRecord;

@Repository
public class TradeDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 变更金币
	 * 
	 * @param gold
	 *            变更的
	 * @return
	 */
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	@Transacted
	public Long changeGold(String userId, int gold, String remark) {
		if (gold == 0) {
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		String sql = "update t_user_account set gold = gold + ? where userId=? and gold + ?>0";
		Long code = jdbcHandle.update(sql, gold, userId, gold);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		/**
		 * 添加交易记录
		 */
		TradeRecord record = new TradeRecord();
		record.setCreateTime(new Date());
		record.setCurrencyType(0);
		record.setRemark(remark);
		record.setTradeType(gold < 0 ? 1 : 2);
		record.setTradeValue(Math.abs(gold));
		record.setUserId(userId);
		record.setDayCode(DateUtils.getDayCode(record.getCreateTime()));
		code = jdbcHandle.insert(record);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		return code;
	}

	/**
	 * 变更钻石
	 * 
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @return
	 */
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	@Transacted
	public Long changeJewel(String userId, int jewel, String remark) {
		if(jewel==0){
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		String sql = "update t_user_account set jewel = jewel + ? where userId=? and jewel + ?>0 ";
		Long code = jdbcHandle.update(sql, jewel, userId, jewel);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		/**
		 * 添加交易记录
		 */
		TradeRecord record = new TradeRecord();
		record.setCreateTime(new Date());
		record.setCurrencyType(0);
		record.setRemark(remark);
		record.setTradeType(jewel < 0 ? 1 : 2);
		record.setTradeValue(Math.abs(jewel));
		record.setUserId(userId);
		record.setDayCode(DateUtils.getDayCode(record.getCreateTime()));
		code = jdbcHandle.insert(record);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		return code;
	}

	/**
	 * 变更元宝
	 */
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	@Transacted
	public Long changeIngot(String userId, int ingot, String remark) {
		if(ingot==0){
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		String sql = "update t_user_account set ingot = ingot + ? where userId=? and ingot + ?>0";
		Long code = jdbcHandle.update(sql, ingot, userId, ingot);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		/**
		 * 添加交易记录
		 */
		TradeRecord record = new TradeRecord();
		record.setCreateTime(new Date());
		record.setCurrencyType(0);
		record.setRemark(remark);
		record.setTradeType(ingot < 0 ? 1 : 2);
		record.setTradeValue(Math.abs(ingot));
		record.setUserId(userId);
		record.setDayCode(DateUtils.getDayCode(record.getCreateTime()));
		code = jdbcHandle.insert(record);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1048);
		}
		return code;
	}

	/**
	 * 查询某日的交易记录
	 * 
	 * @param dayCode
	 *            日期编码 DateUtils.getDayCode()
	 * @param currencyType
	 *            币种类型
	 * @param tradeType
	 *            交易类型 1扣款 2加款
	 * @param line
	 *            查询行数
	 * @return
	 */
	public List<TradeRecord> getTradeRecord(String dayCode, Integer currencyType, Integer tradeType, Integer line) {
		String sql = "select * from t_trade_record where dayCode=? and currencyType=? and tradeType=? order by createTime desc limit ? ";
		return jdbcHandle.query(TradeRecord.class, sql, dayCode, currencyType, tradeType, line);
	}
}
