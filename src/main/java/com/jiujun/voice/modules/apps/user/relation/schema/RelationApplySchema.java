package com.jiujun.voice.modules.apps.user.relation.schema;

import java.util.Date;

import com.jiujun.voice.common.config.YmlConfigBuilder;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class RelationApplySchema extends SchemaModel{

	@DocFlag("用户ID")
	private String userId;
	@DocFlag("用户名称")
	private String name;
	@DocFlag("用户头像")
	private String head;
	@DocFlag("头像地址，全路径")
	private String compleHead;
	@DocFlag("消息状态,0待处理 1已同意 2已拒绝 ")
	private Integer status;
	@DocFlag("申请时间")
	private Date time;
	
	
	
	public String getCompleHead() {
		return YmlConfigBuilder.getProperty(Constants.FILE_DOMAIN) +  head;
	}
	public void setCompleHead(String compleHead) {
		this.compleHead = compleHead;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	
	
}
