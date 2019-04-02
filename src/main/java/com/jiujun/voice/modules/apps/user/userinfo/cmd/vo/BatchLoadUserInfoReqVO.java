package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class BatchLoadUserInfoReqVO extends BaseReqVO{

	@ParamCheck
	@DocFlag("用户ID列表")
	private List<String> userIds;

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	
	
	
}
