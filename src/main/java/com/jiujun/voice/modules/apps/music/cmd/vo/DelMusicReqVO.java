package com.jiujun.voice.modules.apps.music.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

@SuppressWarnings("serial")
public class DelMusicReqVO extends BaseReqVO{

	@DocFlag("音乐ID，即我的音乐列表接口的id字段")
	@ParamCheck
	private Long musicId;

	public Long getMusicId() {
		return musicId;
	}

	public void setMusicId(Long musicId) {
		this.musicId = musicId;
	}
	
	
	
}
