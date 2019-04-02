package com.jiujun.voice.common.jdbc.exception;

import com.jiujun.voice.common.exception.VoiceException;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@SuppressWarnings("serial")
public class TableNotFoundException extends VoiceException{

	public static final String MATCHER="Table '([0-9a-zA-Z._\\-])+' doesn't exist";
	
	public TableNotFoundException(String msg) {
		super(msg);
	}
}
