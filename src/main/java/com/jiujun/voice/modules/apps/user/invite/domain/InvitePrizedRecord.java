package com.jiujun.voice.modules.apps.user.invite.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 邀请奖励累计表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_invite_prized_record")
public class InvitePrizedRecord extends DBModel{

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 累计元宝(银币)
	 */
	private Integer ingot;
	
	/**
	 * 累计金币
	 */
	private Integer gold;
	
	private Date createTime;
	
	private Date updateTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getIngot() {
		return ingot;
	}

	public void setIngot(Integer ingot) {
		this.ingot = ingot;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
