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
public class SendCodeReqVO extends BaseReqVO{

	@ParamCheck(format={FormatConstants.MOBILE,FormatConstants.EMAIL},errorMsg="手机或邮箱格式不正确")
	@DocFlag("手机&邮箱")
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
}
