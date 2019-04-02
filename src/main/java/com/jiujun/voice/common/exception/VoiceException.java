package com.jiujun.voice.common.exception;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@SuppressWarnings("serial")
public class VoiceException extends RuntimeException{

	public VoiceException(){
		super();
	}
	
	public VoiceException(String msg){
		super(msg);
	}
	
	public VoiceException(String msg,Exception e){
		super(msg, e);
	}
}
