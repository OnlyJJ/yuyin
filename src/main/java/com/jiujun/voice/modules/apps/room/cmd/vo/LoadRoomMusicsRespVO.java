package com.jiujun.voice.modules.apps.room.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
import com.jiujun.voice.modules.apps.room.cmd.schema.RoomMusicSchema;

@SuppressWarnings("serial")
public class LoadRoomMusicsRespVO extends BaseRespVO{

	@DocFlag("音乐列表")
	@ParamCheck
	private List<RoomMusicSchema> musics;

	public List<RoomMusicSchema> getMusics() {
		return musics;
	}

	public void setMusics(List<RoomMusicSchema> musics) {
		this.musics = musics;
	}
	
	
	
	
}
