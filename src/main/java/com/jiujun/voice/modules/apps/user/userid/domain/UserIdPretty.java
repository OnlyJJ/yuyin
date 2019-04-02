package com.jiujun.voice.modules.apps.user.userid.domain;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_user_id_pretty")
public class UserIdPretty extends DBModel{

	private String code;
	private String format;
	private Integer status;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	
}
