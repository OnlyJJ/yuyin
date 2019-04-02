package com.jiujun.voice.modules.apps.room.cmd.vo;


import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class MicReqVO extends BaseReqVO {
	@ParamCheck
	@DocFlag("房间号")
	private String roomId;
	
	@ParamCheck
	@DocFlag("操作对象用户id")
	private String targetUserId;
	
	@ParamCheck
	@DocFlag("麦位，顺序：1~9")
	private Integer seat;
	
	@ParamCheck
	@DocFlag("麦位类型，0-普通，1-主麦")
	private Integer seatType;
	
	@DocFlag("操作类型，1-上麦，2-下麦")
	private Integer opearte;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
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

	public Integer getOpearte() {
		return opearte;
	}

	public void setOpearte(Integer opearte) {
		this.opearte = opearte;
	}
	
}
