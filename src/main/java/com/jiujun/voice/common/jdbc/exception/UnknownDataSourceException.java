package com.jiujun.voice.common.jdbc.exception;

import com.jiujun.voice.common.exception.VoiceException;

@SuppressWarnings("serial")
public class UnknownDataSourceException  extends VoiceException{

	public UnknownDataSourceException(String msg) {
		super(msg);
	}
}
