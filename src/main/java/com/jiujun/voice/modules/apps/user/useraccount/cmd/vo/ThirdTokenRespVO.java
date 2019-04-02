package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@SuppressWarnings("serial")
public class ThirdTokenRespVO extends TokenRespVO {

	@DocFlag("第三方登录授权秘钥，临时密码")
	private String authToken;
	@DocFlag("是否首次登陆,0否，1是")
	private Integer isFirstLogin = 0;

	public Integer getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Integer isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

}
