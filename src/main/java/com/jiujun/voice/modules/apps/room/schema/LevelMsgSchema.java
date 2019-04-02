package com.jiujun.voice.modules.apps.room.schema;

import com.jiujun.voice.common.model.SchemaModel;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class LevelMsgSchema extends SchemaModel {
	/**
	 * 房间
	 */
	private String roomId;
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 用户昵称
	 */
	private String userName;
	/**
	 * 等级
	 */
	private Integer level;
	
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	
}
