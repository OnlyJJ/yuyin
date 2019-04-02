package com.jiujun.voice.modules.apps.home.cmd.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SearchResultSchema extends SchemaModel {
	
	@DocFlag("用户id")
	private String userId;
	
	@DocFlag("房间id")
	private String roomId;
	
	@DocFlag("用户头像")
	private String icon;
	
	@DocFlag("房间昵称")
	private String roomName;
	
	@DocFlag("用户昵称")
	private String nickName;
	
	@DocFlag("是否有锁，0-无，1-有")
	private Integer lockFlag;
	
	@DocFlag("是否满员，0-没有，1-已满")
	private int full;
	
	@DocFlag("房间激活时间")
	private long activeTime;
	
	@DocFlag("在线人数")
	private int online;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	public int getFull() {
		return full;
	}

	public void setFull(int full) {
		this.full = full;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}
	
}
