package com.jiujun.voice.modules.apps.room.domain;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

@DBTable("t_room_music")
@SuppressWarnings("serial")
public class RoomMusic extends DBModel{

	private String userId;
	private String roomId;
	private String musicsContext;
	
	
	
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
	public String getMusicsContext() {
		return musicsContext;
	}
	public void setMusicsContext(String musicsContext) {
		this.musicsContext = musicsContext;
	}
	
	

}
