package com.jiujun.voice.common.cmd.vo;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.model.CmdModel;
import com.jiujun.voice.common.utils.StringUtil;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@SuppressWarnings("serial")
public class BaseRespVO extends CmdModel {

	@DocFlag("响应码")
	private int code;
	@DocFlag("响应描述")
	private String msg;
	@DocFlag("错误详情，不展示给用户")
	private String error;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public BaseRespVO(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	@SuppressWarnings("unchecked")
	public <T> T pushErrorCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
		return (T) this;
	}
	@SuppressWarnings("unchecked")
	public <T> T pushErrorCode(Integer code, String msg,String error) {
		this.code = code;
		this.msg = msg;
		this.error=error;
		if(!StringUtil.isNullOrEmpty(error)) {
			error=error.replace(",", "，");
		}
		return (T) this;
	}
	@SuppressWarnings("unchecked")
	public <T> T pushErrorCode(ErrorCode error) {
		this.code = error.getCode();
		this.msg = error.getMsg();
		return (T) this;
	}

	public BaseRespVO() {
		pushErrorCode(ErrorCode.SUCCESS_0);
	}

	public String toJson() {
		return JSON.toJSONString(this);
	}
}
