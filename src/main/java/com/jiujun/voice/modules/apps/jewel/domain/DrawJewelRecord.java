package com.jiujun.voice.modules.apps.jewel.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 钻石提取流水记录表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_draw_jewel_record")
public class DrawJewelRecord extends DBModel {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 正在提取钻石数（每笔）
	 */
	private Integer drawJewel;
	/**
	 * 提取状态，0-待审核，1-成功，2-失败  3 待打款
	 */
	private Integer status;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 创建时间
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
	public Integer getDrawJewel() {
		return this.drawJewel;
	}
	
	public void setDrawJewel(Integer drawJewel) {
		this.drawJewel = drawJewel;
	}
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
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
