package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.constants.FormatConstants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@SuppressWarnings("serial")
public class LoginReqVO extends BaseReqVO{

	@DocFlag("用户ID&手机&邮箱")
	@ParamCheck(format= {FormatConstants.MOBILE,FormatConstants.EMAIL,FormatConstants.POSITIVE_NUMBER},errorMsg="手机或邮箱格式不正确")
	private String account;
	@DocFlag("密码")
	@ParamCheck
	private String pwd;


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	
}
