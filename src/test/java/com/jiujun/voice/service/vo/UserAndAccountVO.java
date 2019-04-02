package com.jiujun.voice.service.vo;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class UserAndAccountVO extends SchemaModel{


	@DocFlag("用户ID")
	private Long id;
	@DocFlag("用户名")
	private String user;
	@DocFlag("密码")
	private String pwd;
	private Long userId;
	private Integer account;
	private Integer score;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
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
