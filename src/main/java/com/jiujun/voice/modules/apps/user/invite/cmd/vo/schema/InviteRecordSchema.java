package com.jiujun.voice.modules.apps.user.invite.cmd.vo.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.user.invite.domain.InviteRecord;

@SuppressWarnings("serial")
public class InviteRecordSchema extends InviteRecord{

	@DocFlag("注册人昵称")
	private String registranterName;

	public String getRegistranterName() {
		return registranterName;
	}

	public void setRegistranterName(String registranterName) {
		this.registranterName = registranterName;
	}
	
	
	
}
