package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class LoadAlbumsReqVO extends BaseReqVO{

	@ParamCheck
	@DocFlag("用户(自己或他人)id")
	private String targetUserId;

	public String getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(String targetUserId) {
		this.targetUserId = targetUserId;
	}
	
	
}
