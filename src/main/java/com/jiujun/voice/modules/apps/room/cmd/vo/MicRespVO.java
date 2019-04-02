package com.jiujun.voice.modules.apps.room.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.room.cmd.schema.MicInfoSchema;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class MicRespVO extends BaseRespVO {
	
	@DocFlag("房间麦位信息")
	private List<MicInfoSchema> data;

	public List<MicInfoSchema> getData() {
		return data;
	}

	public void setData(List<MicInfoSchema> data) {
		this.data = data;
	}
	
}
