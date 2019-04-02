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
@DBTable("t_relation_queue")
public class RelationQueue extends DBModel{

	
	private Long id;
	private String sender;
	private String receiver;
	//0待处理 1已同意 2已拒绝 
	private Integer status;
	private Date createTime;
	private Date updateTime;
	
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
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
