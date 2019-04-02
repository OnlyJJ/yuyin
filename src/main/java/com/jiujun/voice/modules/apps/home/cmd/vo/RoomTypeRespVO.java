package com.jiujun.voice.modules.apps.home.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.room.domain.RoomEnjoyType;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class RoomTypeRespVO extends BaseRespVO {
	
	@DocFlag("房间类型列表")
	private List<RoomEnjoyType> data;

	public List<RoomEnjoyType> getData() {
		return data;
	}

	public void setData(List<RoomEnjoyType> data) {
		this.data = data;
	}

	
}
