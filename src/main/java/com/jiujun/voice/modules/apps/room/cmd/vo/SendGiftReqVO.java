package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.constants.FormatConstants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SendGiftReqVO extends BaseReqVO {
	@ParamCheck
	@DocFlag("房间号")
	private String roomId;
	
	@ParamCheck
	@DocFlag("收礼用户id")
	private String toUserId;
	
	@ParamCheck
	@DocFlag("礼物id")
	private Integer giftId;
	
	@ParamCheck(format=FormatConstants.POSITIVE_NUMBER)
	@DocFlag("礼物数量")
	private Integer num;
	

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

}
