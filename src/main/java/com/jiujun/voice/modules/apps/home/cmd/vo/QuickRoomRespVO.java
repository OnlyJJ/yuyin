package com.jiujun.voice.modules.apps.home.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.home.cmd.schema.RoomInfoSchema;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class QuickRoomRespVO extends BaseRespVO {
	
	@DocFlag("房间列表")
	private List<RoomInfoSchema> data;

	public List<RoomInfoSchema> getData() {
		return data;
	}

	public void setData(List<RoomInfoSchema> data) {
		this.data = data;
	}


	
}
