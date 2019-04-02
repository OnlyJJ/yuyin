package com.jiujun.voice.common.service;

import java.io.Serializable;

/**
 * 服务接口返回结果，所有接口必须统一使用<br>
 * 字段说明：<br>
 * succeed 处理结果，true/false<br>
 * resultCode 结果code，该code由各自业务模块自行维护，但需保证唯一<br>
 * resultMsg 结果说明
 * @author shao.x
 * @date 2018-08-25
 * @param <T>
 */
public final class ServiceResult<T> implements Serializable {
	private static final long serialVersionUID = -9100629359484097704L;

	private boolean succeed = true;
	private int code = -1;
	private String msg;
	private int pageNum;
	private int pageSize;
	private int total;
	private T data;

	public ServiceResult() {}

	public ServiceResult(T data) {
		this.data = data;
	}
	public ServiceResult(boolean succeed, int code, String msg) {
		this.succeed = succeed;
		this.code = code;
		this.msg = msg;
	}

	public ServiceResult(boolean succeed, T data, String msg) {
		this.succeed = succeed;
		this.data = data;
		this.msg = msg;
	}

	public ServiceResult(boolean succeed, T data, int code,
			String msg) {
		this.succeed = succeed;
		this.data = data;
		this.code = code;
		this.msg = msg;
	}

	public ServiceResult(boolean succeed, String msg) {
		this.succeed = succeed;
		this.msg = msg;
	}

	public boolean isSucceed() {
		return succeed;
	}

	public void setSucceed(boolean succeed) {
		this.succeed = succeed;
	}

	public int getResultCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
