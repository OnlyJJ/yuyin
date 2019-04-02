package com.jiujun.voice.modules.apps.room.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.room.cmd.schema.RoomMusicSchema;

@SuppressWarnings("serial")
public class SubmitMusicsReqVO extends BaseReqVO{
	

	@DocFlag("音乐列表")
	private List<RoomMusicSchema> musics;

	public List<RoomMusicSchema> getMusics() {
		return musics;
	}

	public void setMusics(List<RoomMusicSchema> musics) {
		this.musics = musics;
	}
	
	
}
