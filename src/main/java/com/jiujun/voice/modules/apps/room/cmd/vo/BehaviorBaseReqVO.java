package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 房间行为基础父类
 * @author Shao.x
 * @date 2018年11月30日
 */
@SuppressWarnings("serial")
public class BehaviorBaseReqVO extends BaseReqVO {
	
	@DocFlag("房间id")
	@ParamCheck
	private String roomId;
	
	@DocFlag("被操作用户Id")
	@ParamCheck
	private String toUserId;

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

	
}
