package com.jiujun.voice.common.jdbc.exception;

import com.jiujun.voice.common.exception.VoiceException;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class JdbcException extends VoiceException{

	
	public JdbcException(String msg) {
		super(msg);
	}
	public JdbcException(String msg,Exception e) {
		super(msg,e);
	}
}
