package com.jiujun.voice.modules.apps.jewel.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.constants.FormatConstants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

@SuppressWarnings("serial")
public class BindMobileReqVO extends BaseReqVO{

	
	@ParamCheck(format=FormatConstants.MOBILE, errorMsg="手机格式不正确")
	@DocFlag("手机号")
	private String mobile;
	@DocFlag("临时授权令牌")
	@ParamCheck
	private String authToken;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
}
