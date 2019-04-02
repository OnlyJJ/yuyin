package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
* t_room_active
* 激活房间记录表（搜索和首先显示）
* @author Coody
*/
@SuppressWarnings("serial")
@DBTable("t_room_active")
public class RoomActive extends DBModel {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 房主用户id
	 */
	private String userId;
	/**
	 * 房间id
	 */
	private String roomId;
	/**
	 * 房间名称（修改房间时，同步修改此名称）
	 */
	private String roomName;
	/**
	 * 房间偏好，关联t_room_info的enjoyType
	 */
	private String enjoyType;
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
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return this.roomName;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getEnjoyType() {
		return enjoyType;
	}

	public void setEnjoyType(String enjoyType) {
		this.enjoyType = enjoyType;
	}
	
}
