package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class LoadUserInfoReqVO extends BaseReqVO{

	private String targeUserId;

	public String getTargeUserId() {
		return targeUserId;
	}

	public void setTargeUserId(String targeUserId) {
		this.targeUserId = targeUserId;
	}
	
	
}
