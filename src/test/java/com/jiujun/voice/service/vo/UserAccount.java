package com.jiujun.voice.service.vo;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.SchemaModel;

@SuppressWarnings("serial")
@DBTable("t_user_account")
public class UserAccount extends SchemaModel {

	private Long userId;
	private Integer account;
	private Integer score;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getAccount() {
		return account;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
	
}
