package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.constants.FormatConstants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ResetPwdReqVO extends BaseReqVO{
	
	@ParamCheck(format={FormatConstants.MOBILE,FormatConstants.EMAIL},errorMsg="手机或邮箱格式不正确")
	@DocFlag("手机号或邮箱")
	private String account;
	
	@ParamCheck(format=FormatConstants.NUMBER)
	@DocFlag("验证码")
	private String code;
	
	@ParamCheck(format=FormatConstants.USER_PWD)
	@DocFlag("新密码")
	private String newPwd;


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	
	
}
