package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@SuppressWarnings("serial")
public class TokenRespVO extends BaseRespVO{

	@DocFlag("用户ID")
	private String userId;
	@DocFlag("用户令牌")
	private String token;
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
