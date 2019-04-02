package com.jiujun.voice.modules.apps.user.relation.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class DoRelationReqVO extends BaseReqVO{

	
	@DocFlag("消息发送者用户ID")
	@ParamCheck
	private String sender;
	
	@ParamCheck
	@DocFlag("变更状态， 1同意 2拒绝 ")
	private Integer status;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
