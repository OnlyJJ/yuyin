package com.jiujun.voice.modules.apps.jewel.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class DrawJewelPageReqVO extends BaseReqVO {
	
	@DocFlag(value="每页大小，默认64",docFieldValue="64")
	private Integer pageSize = 64;
	/**
	 * 当前页，默认1
	 */
	@DocFlag(value="页码，默认第1页",docFieldValue="1")
	private Integer pageNo = 1;
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		if(pageNo != null && pageNo > 100) {
			return 100;
		}
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

}
