package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 房间个人信息请求实体
 * @author Shao.x
 * @date 2018年11月30日
 */
@SuppressWarnings("serial")
public class MemberInfoReqVO extends BaseReqVO {
	
	@DocFlag("房间id")
	@ParamCheck
	private String roomId;
	
	@DocFlag("查询对象id")
	@ParamCheck
	private String targetUserId;
	
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

	
}
