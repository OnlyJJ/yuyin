package com.jiujun.voice.modules.apps.jewel.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 我的收益汇总表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_draw_jewel")
public class DrawJewel extends DBModel {
	
	private Integer id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 总共提取的钻石（总收益）
	 */
	private Integer totalJewel;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 创建时间user
	 */
	private Date createTime;
	
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
	public Integer getTotalJewel() {
		return this.totalJewel;
	}
	
	public void setTotalJewel(Integer totalJewel) {
		this.totalJewel = totalJewel;
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
