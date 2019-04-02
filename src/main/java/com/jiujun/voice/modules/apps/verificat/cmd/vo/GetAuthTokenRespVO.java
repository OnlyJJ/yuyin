package com.jiujun.voice.modules.apps.verificat.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class GetAuthTokenRespVO extends BaseRespVO{

	@DocFlag("临时授权令牌")
	private String authToken;

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	
	
}
