package com.jiujun.voice.common.utils.magic.exceptation;

import com.jiujun.voice.common.exception.VoiceException;

@SuppressWarnings("serial")
public class NoMethodException extends VoiceException{


	public NoMethodException(Class<?> clazz) {
		super("未找到接口方法 >>" + clazz.getName());
	}


	public NoMethodException(Class<?> clazz, Exception e) {
		super("未找到接口方法 >>" + clazz.getName(), e);
	}
}
