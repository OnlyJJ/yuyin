package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * 房间成员行为操作表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_room_member_behavior")
public class RoomMemberBehavior extends DBModel {
	
	private Integer id;
	/**
	 * 房间号
	 */
	private String roomId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 行为，行为，1-禁止上麦，2-禁止发言，3-踢出房间
	 */
	private Integer behavior;
	/**
	 * 当前禁止的行为状态，默认1-有效，0-无效
	 */
	private Integer status;
	/**
	 * 封禁的行为开始时间
	 */
	private Date beginTime;
	/**
	 * 封禁的行为结束时间
	 */
	private Date endTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	
	public Integer getId() {
		return id;
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
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getBehavior() {
		return this.behavior;
	}
	
	public void setBehavior(Integer behavior) {
		this.behavior = behavior;
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
}
