package com.jiujun.voice.modules.apps.im.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SendGeneralMsgReqVO extends BaseReqVO{

	
	
	@ParamCheck
	@DocFlag("对方userId")
	private String targeUserId;
	
	@ParamCheck
	@DocFlag("消息内容")
	private String msg;

	public String getTargeUserId() {
		return targeUserId;
	}

	public void setTargeUserId(String targeUserId) {
		this.targeUserId = targeUserId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
