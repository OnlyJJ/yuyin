package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class WXLoginReqVO extends BaseReqVO{

	@ParamCheck
	@DocFlag("微信返回的code")
	private String code;
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	
	
}
