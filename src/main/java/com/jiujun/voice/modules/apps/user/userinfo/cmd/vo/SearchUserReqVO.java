package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SearchUserReqVO extends BaseReqVO{

	@DocFlag("关键词")
	@ParamCheck
	private String keyWord;

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	
	
}
