package com.jiujun.voice.common.utils.property.exception;

import com.jiujun.voice.common.exception.VoiceException;

@SuppressWarnings("serial")
public class UnsafeException extends VoiceException{
	public UnsafeException(){
		super();
	}
	
	public UnsafeException(String msg){
		super(msg);
	}
	
	public UnsafeException(String msg,Exception e){
		super(msg, e);
	}
}
