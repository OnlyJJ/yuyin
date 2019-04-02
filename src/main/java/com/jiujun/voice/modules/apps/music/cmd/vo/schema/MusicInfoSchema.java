package com.jiujun.voice.modules.apps.music.cmd.vo.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.music.domain.MusicInfo;

@SuppressWarnings("serial")
public class MusicInfoSchema extends MusicInfo{

	@DocFlag("用户名")
	private String userName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
