package com.jiujun.voice.modules.apps.user.useraccount.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 背包详情表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_backpack")
public class BackpackInfo extends DBModel {

	private String userId;
	/**
	 * 1礼物 2道具
	 */
	private Integer type;
	/**
	 * 关联值，如：礼物ID、道具ID
	 */
	private String correId;
	private Integer num;
	private Date createTime;
	private Date updateTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getCorreId() {
		return correId;
	}
	public void setCorreId(String correId) {
		this.correId = correId;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
