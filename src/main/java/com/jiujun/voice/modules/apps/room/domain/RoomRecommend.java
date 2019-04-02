package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 房间推荐表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_room_recommend")
public class RoomRecommend extends DBModel {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 房间id
	 */
	private String roomId;
	/**
	 * 排序，越大越靠前
	 */
	private Integer sort;
	/**
	 * 状态，0-无效，1-有效
	 */
	private Integer status;
	/**
	 * 开始时间
	 */
	private Date beginTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 拉流外链地址
	 */
	private String url;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Integer getSort() {
		return this.sort;
	}
	
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getBeginTime() {
		return this.beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
