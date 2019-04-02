package com.jiujun.voice.modules.apps.user.relation.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_relation_record")
public class RelationRecord extends DBModel{

	
	private Long id;
	private String userId;
	private String targeUserId;
	private Integer status;
	private Date createTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTargeUserId() {
		return targeUserId;
	}
	public void setTargeUserId(String targeUserId) {
		this.targeUserId = targeUserId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	

}
