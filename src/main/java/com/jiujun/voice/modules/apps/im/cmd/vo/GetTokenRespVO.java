package com.jiujun.voice.modules.apps.im.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class GetTokenRespVO extends BaseRespVO{

	
	@DocFlag("用户ID")
	private String userId;
	
	@DocFlag("融云的token")
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
