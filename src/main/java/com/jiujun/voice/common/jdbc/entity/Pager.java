package com.jiujun.voice.common.jdbc.entity;

import java.util.List;

import com.jiujun.voice.common.model.EntityModel;
/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@SuppressWarnings("serial")
public class Pager extends EntityModel {

	/**
	 * 总条数
	 */
	private Integer count =0;
	/**
	 * 默认页大小
	 */
	private Integer pageSize = 10;
	/**
	 * 当前页码
	 */
	private Integer pageNo;
	/**
	 * 总页数
	 */
	private Integer pageCount=1;
	/**
	 * 数据
	 */
	private List<?> data;

	


	public Pager(Integer pageNo) {
		super();
		this.pageSize = 1;
		this.pageNo = pageNo;
	}


	public Pager(Integer pageNo, Integer pageSize) {
		super();
		if(pageSize==null||pageSize<1){
			pageSize=1;
		}
		if(pageNo==null||pageNo>100){
			pageNo=20;
		}
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}


	public Pager() {
		this.pageSize = 1;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getData() {
		return (List<T>) data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if(pageSize==null||pageSize<1){
			pageSize=1;
		}
		this.pageSize = pageSize;
		builderPageCount();
	}


	public void setPageNo(Integer pageNo) {
		if(pageNo==null){
			pageNo=20;
		}
		if(pageNo>100){
			pageNo=100;
		}
		this.pageNo = pageNo;
	}


	public Integer getPageNo() {
		return pageNo;
	}


	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
		builderPageCount();
	}
	
	private void builderPageCount(){
		try {
			if(count==null){
				return;
			}
			if(pageSize==null||pageSize==0){
				return;
			}
			this.pageCount = count / pageSize;
			Integer mod = count % pageSize;
			if (mod > 0) {
				this.pageCount++;
			}
			if (this.pageCount == 0) {
				this.pageCount = 1;
			}
			if (this.pageSize == 0 || this.pageSize < 0) {
				pageSize = 1;
			}
		} catch (Exception e) {
		}
	}
}
