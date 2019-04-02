package com.jiujun.voice.common.utils.magic.iface;

public interface DynamicContainer {

	public <T> T get(String field);
	
	public boolean set(String field,Object value);
}
