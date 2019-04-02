package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 *  话题修改记录表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_room_theme_record")
public class RoomThemeRecord extends DBModel {
	
	private Integer id;
	/**
	 * 房间id
	 */
	private String roomId;
	/**
	 * 话题
	 */
	private String theme;
	/**
	 * 修改话题的用户id
	 */
	private String userId;
	/**
	 * 修改话题的用户身份，0-管理员，1-房主
	 */
	private Integer identity;
	/**
	 * 状态，默认1-正常，2-关闭
	 */
	private Integer status;
	/**
	 * 修改时间
	 */
	private Date createTime;
	
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
	public String getTheme() {
		return this.theme;
	}
	
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getIdentity() {
		return this.identity;
	}
	
	public void setIdentity(Integer identity) {
		this.identity = identity;
	}
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
