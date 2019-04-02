package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;


/**
 *  房间角色信息表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_room_role")
public class RoomRole extends DBModel {
	
	private Integer id;
	/**
	 * 房间
	 */
	private String roomId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 房间身份，1-房主，2-管理员
	 */
	private int identity;
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
	public int getIdentity() {
		return this.identity;
	}
	
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
