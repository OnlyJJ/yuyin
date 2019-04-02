package com.jiujun.voice.modules.apps.room.cmd.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class MicInfoSchema extends SchemaModel {
	
	@DocFlag("房间id")
	private String roomId;
	
	@DocFlag("占麦用户id")
	private String userId;
	
	@DocFlag("占麦用户昵称")
	private String name;
	
	@DocFlag("占麦用户房间角色")
	private Integer role;
	
	@DocFlag("占麦用户头像")
	private String icon;
	
	@DocFlag("麦位，顺序为：1~9")
	private Integer seat;
	
	@DocFlag("麦位类型，默认0-普通，1-主麦位")
	private Integer seatType;
	
	@DocFlag("麦位状态，0-正常，1-禁麦，2-锁麦")
	private Integer status;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getSeat() {
		return seat;
	}

	public void setSeat(Integer seat) {
		this.seat = seat;
	}

	public Integer getSeatType() {
		return seatType;
	}

	public void setSeatType(Integer seatType) {
		this.seatType = seatType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
