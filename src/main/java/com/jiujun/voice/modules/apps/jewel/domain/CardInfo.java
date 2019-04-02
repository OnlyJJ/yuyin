package com.jiujun.voice.modules.apps.jewel.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * 绑定银行卡信息
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_card_info")
public class CardInfo extends DBModel {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * userId
	 */
	private String userId;
	/**
	 * 银行卡号
	 */
	private String cardNO;
	/**
	 * 开户人
	 */
	private String owner;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 开户行
	 */
	private String bank;
	/**
	 * 支行
	 */
	private String subbranch;
	/**
	 * 地址
	 */
	private String address;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	 
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardNO() {
		return this.cardNO;
	}
	
	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getSubbranch() {
		return subbranch;
	}

	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
