package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * t_room_active
 * 精灵表
 * @author tangys
 *
 */
@SuppressWarnings("serial")
@DBTable("t_fairy_info")
public class FairyInfo extends DBModel{
	/**
	 * 主键
	 */
	@DocFlag("主键id")
	private Integer id;
	/**
	 * 活动名称
	 */
	@DocFlag("活动名称")
	private String activityName;
	/**
	 * 显示图片
	 */
	@DocFlag("显示图片")
	private String showImg;
	
	/**
	 * 状态，默认 1启用-  0：停用 1：启用
	 */
	@DocFlag("状态，默认 1启用-  0：停用 1：启用")
	private Integer status;
	/**
	 * 开始时间
	 */
	@DocFlag("开始时间")
	private Date beginTime;
	/**
	 * 结束时间
	 */
	@DocFlag("结束时间")
	private Date endTime;
	
	/**
	 * 修改时间
	 */
	@DocFlag("修改时间")
	private Date updateTime;
	/**
	 * 创建时间
	 */
	@DocFlag("创建时间")
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getShowImg() {
		return showImg;
	}
	public void setShowImg(String showImg) {
		this.showImg = showImg;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
}
