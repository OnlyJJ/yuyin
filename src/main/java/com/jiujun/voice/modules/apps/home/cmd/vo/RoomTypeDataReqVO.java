package com.jiujun.voice.modules.apps.home.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;


/**
 * 房间类型下的所有房间请求实体
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class RoomTypeDataReqVO extends BaseReqVO {
	
	@DocFlag("房间类型，如CJ")
	@ParamCheck
	private String type;
	
	@DocFlag("每页大小，默认64")
	private Integer pageSize = 64;
	/**
	 * 当前页，默认1
	 */
	@DocFlag("页码，默认第1页")
	private Integer pageNo = 1;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
