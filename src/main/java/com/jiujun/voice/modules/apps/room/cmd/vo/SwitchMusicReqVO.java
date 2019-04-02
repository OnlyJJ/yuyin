package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

@SuppressWarnings("serial")
public class SwitchMusicReqVO extends BaseReqVO{

	@DocFlag("文件唯一标识")
	@ParamCheck
	private String fileSign;

	public String getFileSign() {
		return fileSign;
	}

	public void setFileSign(String fileSign) {
		this.fileSign = fileSign;
	}
	
	
}
