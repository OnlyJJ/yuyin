package com.jiujun.voice.modules.apps.user.relation.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class FollowReqVO extends BaseReqVO{

	@ParamCheck
	@DocFlag("对方用户ID")
	private String targeUserId;

	public String getTargeUserId() {
		return targeUserId;
	}

	public void setTargeUserId(String targeUserId) {
		this.targeUserId = targeUserId;
	}
	
	
	
}
