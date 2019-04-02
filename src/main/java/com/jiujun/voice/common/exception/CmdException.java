package com.jiujun.voice.common.exception;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.enums.ErrorCode;

/**
 * 接口响应相关异常
 * @author Coody
 * @date 2018年10月31日
 */
@SuppressWarnings("serial")
public class CmdException extends VoiceException{
	
	public CmdException(Integer code,String msg) {
		this.code=code;
		this.msg=msg;
	}
	
	public CmdException(ErrorCode error) {
		super(error.getMsg());
		this.code=error.getCode();
		this.msg=error.getMsg();
	}
	
	private Integer code;
	
	private String msg;
	
	private String error;

	
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

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
	
	public BaseRespVO builderResponse() {
		BaseRespVO resp=new BaseRespVO().pushErrorCode(code, msg);
		return resp;
	}
}
