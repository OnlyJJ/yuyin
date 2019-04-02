package com.jiujun.voice.modules.apps.jewel.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.constants.FormatConstants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 校验手机验证码
 * @author Shao.x
 * @date 2019年1月14日
 */
@SuppressWarnings("serial")
public class CheckMobileReqVO extends BaseReqVO {
	@ParamCheck(format=FormatConstants.MOBILE, errorMsg="手机格式不正确")
	@DocFlag("手机号")
	private String mobile;
	
	@ParamCheck
	@DocFlag("验证码")
	private Integer code;
 
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
 
}
