package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

@SuppressWarnings("serial")
public class SendFromBackPackReqVO extends BaseReqVO {

	@DocFlag("对方用户ID")
	@ParamCheck
	String toUserId;
	@ParamCheck
	@DocFlag("房间ID")
	String roomId;
	@ParamCheck
	@DocFlag("物品源ID")
	String correId;
	@ParamCheck
	@DocFlag("物品类型 1礼物 2道具")
	Integer type;
	@ParamCheck
	@DocFlag("赠送数量")
	Integer num;
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getCorreId() {
		return correId;
	}
	public void setCorreId(String correId) {
		this.correId = correId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}

	
	
}
