package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class RoomGIftRecordReqVO extends BaseReqVO {
	
	@ParamCheck
	@DocFlag("房间号")
	private String roomId;
	
	@DocFlag("每页大小，默认64")
	private Integer pageSize = 64;
	/**
	 * 当前页，默认1
	 */
	@DocFlag("页码，默认第1页")
	private Integer pageNo = 1;
	
	@ParamCheck
	@DocFlag("类型，1-日记录，2-周记录（周一00:00:00至周日23:59:59）")
	private Integer type;


	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
