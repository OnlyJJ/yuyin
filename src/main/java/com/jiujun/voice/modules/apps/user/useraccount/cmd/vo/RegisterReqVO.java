package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

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
public class RegisterReqVO extends BaseReqVO{

	@DocFlag("手机&邮箱")
	@ParamCheck(format= {FormatConstants.MOBILE,FormatConstants.EMAIL},errorMsg="手机或邮箱格式不正确")
	private String account;
	@DocFlag("用户密码")
	@ParamCheck(format=FormatConstants.USER_PWD)
	private String password;
	@DocFlag("邀请码")
	private String inviteCode;
	@DocFlag("验证码")
	@ParamCheck(format=FormatConstants.POSITIVE_NUMBER)
	private String code;

	
	
	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
	
}
