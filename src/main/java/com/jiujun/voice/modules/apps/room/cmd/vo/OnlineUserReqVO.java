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
public class OnlineUserReqVO extends BaseReqVO {
	
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

}
