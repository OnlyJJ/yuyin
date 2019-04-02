package com.jiujun.voice.modules.caller.exception;

import com.jiujun.voice.common.exception.VoiceException;

/**
 * 调度器执行异常
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class CallerException extends VoiceException{
	
	public CallerException(){
		super();
	}
	
	public CallerException(String msg){
		super(msg);
	}
	
	public CallerException(String msg,Exception e){
		super(msg, e);
	}
}
