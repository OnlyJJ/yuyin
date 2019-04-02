package com.jiujun.voice.modules.redis.entity;

import com.jiujun.voice.common.model.EntityModel;

@SuppressWarnings("serial")
public class RedisSuberEntity extends EntityModel{

	/**
	 * 指令
	 */
	private String action;
	
	private String data;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
