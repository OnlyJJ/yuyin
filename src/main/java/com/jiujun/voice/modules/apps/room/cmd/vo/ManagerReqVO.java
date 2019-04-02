package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 房间管理
 * @author Shao.x
 * @date 2018年11月30日
 */
@SuppressWarnings("serial")
public class ManagerReqVO extends BehaviorBaseReqVO {
	
	@DocFlag("操作类型，1-上管理，2-下管理")
	@ParamCheck
	private Integer operate;

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

}
