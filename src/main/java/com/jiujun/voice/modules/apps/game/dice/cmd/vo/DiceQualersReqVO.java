package com.jiujun.voice.modules.apps.game.dice.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class DiceQualersReqVO extends BaseReqVO{

	
	@ParamCheck
	@DocFlag("房间ID")
	private String roomId;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	
}
