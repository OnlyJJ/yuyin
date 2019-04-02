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
public class MicManageReqVO extends BaseReqVO {
	@ParamCheck
	@DocFlag("房间号")
	private String roomId;
	
	@ParamCheck
	@DocFlag("麦位，顺序：1~9")
	private Integer seat;
	
	@ParamCheck
	@DocFlag("麦位类型，0-普通，1-主麦")
	private Integer seatType;
	
	@DocFlag("操作类型，1-锁麦，2-解锁麦位，3-禁麦，4-解禁麦位")
	private Integer opearte;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
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
