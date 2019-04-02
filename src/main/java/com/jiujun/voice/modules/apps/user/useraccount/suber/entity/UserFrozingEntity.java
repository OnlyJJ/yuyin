package com.jiujun.voice.modules.apps.user.useraccount.suber.entity;

import com.jiujun.voice.common.model.EntityModel;

@SuppressWarnings("serial")
public class UserFrozingEntity extends EntityModel{

	private String userId;
	
	private Integer status;
	
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
