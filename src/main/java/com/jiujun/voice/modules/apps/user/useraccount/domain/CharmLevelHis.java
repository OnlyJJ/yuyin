package com.jiujun.voice.modules.apps.user.useraccount.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_charmLevel_his")
public class CharmLevelHis extends DBModel {
 
	/** 
	 * userId
	 */
	private String userId;
	/** 
	 * 魅力等级
	 */
	private Integer charmLevel;
	/** 
	 * 排名
	 */
	private int sort;
	/** 
	 * 记录时间
	 */
	private Date creatTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getCharmLevel() {
		return charmLevel;
	}
	public void setCharmLevel(Integer charmLevel) {
		this.charmLevel = charmLevel;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	 
	
}