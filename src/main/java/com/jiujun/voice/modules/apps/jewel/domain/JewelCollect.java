package com.jiujun.voice.modules.apps.jewel.domain;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 用户钻石收入信息采集表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_jewel_collect")
public class JewelCollect extends DBModel {
	
	private Integer id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 日期，yyyyMMdd
	 */
	private String dayTime;
	/**
	 * 钻石收益
	 */
	private Integer jewel;
	/**
	 * 更新时间
	 */
	private java.sql.Timestamp updateTime;
	/**
	 * 创建时间
	 */
	private java.sql.Timestamp createTime;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDayTime() {
		return this.dayTime;
	}
	
	public void setDayTime(String dayTime) {
		this.dayTime = dayTime;
	}
	public Integer getJewel() {
		return this.jewel;
	}
	
	public void setJewel(Integer jewel) {
		this.jewel = jewel;
	}
	public java.sql.Timestamp getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.sql.Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public java.sql.Timestamp getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.sql.Timestamp createTime) {
		this.createTime = createTime;
	}
}
