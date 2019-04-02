package com.jiujun.voice.common.exception;

/**
 * 
 * @author Coody
 * @date 2018年11月16日
 */
@SuppressWarnings("serial")
public class GetAwayTaskException extends VoiceException{


	public GetAwayTaskException(){
		super();
	}
	
	public GetAwayTaskException(String msg){
		super(msg);
	}
	
	public GetAwayTaskException(String msg,Exception e){
		super(msg, e);
	}
	
}
