package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 进房间请求实体
 * @author Shao.x
 * @date 2018年11月30日
 */
@SuppressWarnings("serial")
public class InRoomReqVO extends BaseReqVO {
	
	@DocFlag("房间id")
	@ParamCheck
	private String roomId;
	
	@DocFlag("房间密码（进入有密码房间时），格式：base64")
	private String password;
	
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
