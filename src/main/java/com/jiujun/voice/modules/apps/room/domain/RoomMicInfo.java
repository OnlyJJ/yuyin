package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
* 房间麦位信息表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_room_mic_info")
public class RoomMicInfo extends DBModel {
	
	private Integer id;
	
	@DocFlag("房间id")
	private String roomId;
	
	@DocFlag("占麦用户id")
	private String userId;
	
	@DocFlag("麦位，顺序为：1~9")
	private Integer seat;
	
	@DocFlag("麦位类型，默认0-普通，1-主麦位")
	private Integer seatType;
	
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
	public Integer getSeat() {
		return this.seat;
	}
	
	public void setSeat(Integer seat) {
		this.seat = seat;
	}
	public Integer getSeatType() {
		return this.seatType;
	}
	
	public void setSeatType(Integer seatType) {
		this.seatType = seatType;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
