package com.jiujun.voice.common.cmd.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.entity.Pager;

/**
 * 
 * @author Coody
 * @date 2018年11月16日
 */
@SuppressWarnings("serial")
public class PagerReqVO extends BaseReqVO{

	/**
	 * 页大小，默认10
	 */
	@DocFlag("页大小")
	private Integer pageSize = 10;
	/**
	 * 当前页，默认1
	 */
	@DocFlag("页码牌")
	private Integer pageNo = 1;
	
	@JSONField(serialize=false)  
	public Pager toPager() {
		Pager pager=new Pager(pageNo, pageSize);
		return pager;
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
