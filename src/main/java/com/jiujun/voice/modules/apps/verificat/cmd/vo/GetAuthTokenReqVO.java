package com.jiujun.voice.modules.apps.verificat.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.constants.FormatConstants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class GetAuthTokenReqVO extends BaseReqVO{

	@ParamCheck(format={FormatConstants.MOBILE,FormatConstants.EMAIL})
	@DocFlag("手机&邮箱")
	private String account;
	
	@DocFlag("验证码")
	@ParamCheck(format=FormatConstants.POSITIVE_NUMBER)
	private String code;
	
	

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

}
