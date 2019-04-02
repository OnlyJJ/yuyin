package com.jiujun.voice.modules.apps.im.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SendRoomMsgReqVO extends BaseReqVO{

	
	
	@ParamCheck
	@DocFlag("房间ID")
	private String roomId;
	
	@ParamCheck
	@DocFlag("消息内容")
	private String msg;



	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
