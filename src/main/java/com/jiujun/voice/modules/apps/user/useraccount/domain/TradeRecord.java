package com.jiujun.voice.modules.apps.user.useraccount.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

@SuppressWarnings("serial")
@DBTable("t_trade_record")
public class TradeRecord extends DBModel{

	private String userId;
	private Integer tradeValue;
	/**
	 * 交易类型  1扣款 2加款
	 */
	private Integer tradeType;
	/**
	 * 币种类型  0金币  1钻石  2元宝  
	 */
	private Integer currencyType;
	/**
	 * 日期
	 */
	private String dayCode;
	
	private String remark;
	private Date createTime;
	
	
	public String getDayCode() {
		return dayCode;
	}
	public void setDayCode(String dayCode) {
		this.dayCode = dayCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getTradeValue() {
		return tradeValue;
	}
	public void setTradeValue(Integer tradeValue) {
		this.tradeValue = tradeValue;
	}
	public Integer getTradeType() {
		return tradeType;
	}
	public void setTradeType(Integer tradeType) {
		this.tradeType = tradeType;
	}
	public Integer getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
