package com.jiujun.voice.modules.apps.user.useraccount.schema;

import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;

@SuppressWarnings("serial")
public class UserAccountSchema extends UserAccount{

	
	private Integer isFirstLogin = 0;

	public Integer getIsFirstLogin() {
		return isFirstLogin;
	}

	public void setIsFirstLogin(Integer isFirstLogin) {
		this.isFirstLogin = isFirstLogin;
	}

}
