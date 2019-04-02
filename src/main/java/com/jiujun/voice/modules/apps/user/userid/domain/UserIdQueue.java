package com.jiujun.voice.modules.apps.user.userid.domain;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_user_id_queue")
public class UserIdQueue extends DBModel{

	private String code;
	private Integer seq;
	private String uuid;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	

}
