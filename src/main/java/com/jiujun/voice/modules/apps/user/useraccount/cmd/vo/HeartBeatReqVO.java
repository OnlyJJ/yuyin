package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

@SuppressWarnings("serial")
public class HeartBeatReqVO extends BaseReqVO{

	@DocFlag("当前房间(没有可不传)")
	private String roomId;
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
}
