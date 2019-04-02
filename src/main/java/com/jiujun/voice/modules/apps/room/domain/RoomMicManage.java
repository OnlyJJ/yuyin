package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_room_mic_manage")
public class RoomMicManage extends DBModel {
	
	private Integer id;
	/**
	 * 房间id
	 */
	private String roomId;
	/**
	 * 麦位（主位为1，其他依次升序,1~9）
	 */
	private Integer seat;
	/**
	 * 麦位状态，1-禁麦，2-锁麦
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 麦位类型，默认0-普通，1-主麦位
	 */
	private Integer seatType;
	
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
	public Integer getSeat() {
		return this.seat;
	}
	
	public void setSeat(Integer seat) {
		this.seat = seat;
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

	public Integer getSeatType() {
		return seatType;
	}

	public void setSeatType(Integer seatType) {
		this.seatType = seatType;
	}
	
}
