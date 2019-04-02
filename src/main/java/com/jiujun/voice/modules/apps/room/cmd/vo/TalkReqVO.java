package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 房间禁言
 * @author Shao.x
 * @date 2018年11月30日
 */
@SuppressWarnings("serial")
public class TalkReqVO extends BehaviorBaseReqVO {
	
	@DocFlag("操作类型，1-禁言，2-解禁")
	@ParamCheck
	private Integer operate;

	public Integer getOperate() {
		return operate;
	}

	public void setOperate(Integer operate) {
		this.operate = operate;
	}

	
}
