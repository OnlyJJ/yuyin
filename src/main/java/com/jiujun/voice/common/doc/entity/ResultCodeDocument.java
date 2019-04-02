package com.jiujun.voice.common.doc.entity;

import com.jiujun.voice.common.model.EntityModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ResultCodeDocument extends EntityModel{

	private Integer code;
	
	private String msg;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
