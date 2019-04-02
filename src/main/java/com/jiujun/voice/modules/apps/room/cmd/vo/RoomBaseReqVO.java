package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 房间api请求参数，基础父类，有公共的可继承此类
 * @author Shao.x
 * @date 2018年11月30日
 */
@SuppressWarnings("serial")
public class RoomBaseReqVO extends BaseReqVO {
	
	@DocFlag("房间号")
	@ParamCheck
	private String roomId;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
}
