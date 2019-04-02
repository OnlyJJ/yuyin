package com.jiujun.voice.modules.apps.room.cmd.vo;


import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 *  用户所在的房间
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class UserStayRoomRespVO extends BaseRespVO {
	@DocFlag("房主用户id")
	private String userId;
	
	@DocFlag("房主头像")
	private String icon;
	
	@DocFlag("房间id")
	private String roomId;
	
	@DocFlag("房间名称")
	private String name;
	
	@DocFlag("是否上麦，0-否，1-是")
	private int micFlag;

	@DocFlag("用户所在麦位状态，0-正常，1-禁麦，2-锁麦")
	private int micStatus;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMicFlag() {
		return micFlag;
	}

	public void setMicFlag(int micFlag) {
		this.micFlag = micFlag;
	}

	public int getMicStatus() {
		return micStatus;
	}

	public void setMicStatus(int micStatus) {
		this.micStatus = micStatus;
	}
	
}
