package com.jiujun.voice.common.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.entity.Pager;

/**
 * 
 * @author Coody
 * @date 2018年11月16日
 */
@SuppressWarnings("serial")
public class PagerRespVO<T> extends PagerRespVOParent<T> {

	@DocFlag("页大小")
	private Integer pageSize = 10;
	@DocFlag("页码")
	private Integer pageNo = 1;
	@DocFlag("总页数")
	private Integer pageCount = 1;
	@DocFlag("总条数")
	private Integer count;
	@DocFlag("数据结果集")
	private List<T> data;

	public PagerRespVO() {

	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T fromPager(Pager pager) {
		this.setData(pager.getData());
		this.setPageNo(pager.getPageNo());
		this.setPageSize(pager.getPageSize());
		this.setPageCount(pager.getPageCount());
		this.setCount(pager.getCount());
		return (T) this;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	
	

}


