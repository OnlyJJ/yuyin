package com.jiujun.voice.modules.apps.room.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.room.domain.RoomEnjoyType;

/**
 * 偏好配置信息响应实体
 * @author Shao.x
 * @date 2018年11月30日
 */
@SuppressWarnings("serial")
public class EnjoyConfRespVO extends BaseRespVO {
	@DocFlag("偏好类型配置列表")
	private List<RoomEnjoyType> data;

	public List<RoomEnjoyType> getData() {
		return data;
	}

	public void setData(List<RoomEnjoyType> data) {
		this.data = data;
	}

}
