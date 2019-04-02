package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class QQLoginReqVO extends BaseReqVO{

	@ParamCheck
	@DocFlag("openId")
	private String openId;
	
	@ParamCheck
	@DocFlag("accessToken")
	private String accessToken;




	

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}



	
	
}
