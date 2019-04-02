package com.jiujun.voice.modules.im.msg;

import com.jiujun.voice.common.model.EntityModel;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;

/**
 * IM消息容器
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ImMessage extends EntityModel{

	/**
	 * 发送者
	 */
	private UserSchema sender;
	/**
	 * 接受者
	 */
	private UserSchema receiver;
	
	/**
	 * 消息类型
	 */
	private Integer type;
	
	/**
	 * 消息内容，json
	 */
	private String content;

	public UserSchema getSender() {
		return sender;
	}

	public void setSender(UserSchema sender) {
		this.sender = sender;
	}

	public UserSchema getReceiver() {
		return receiver;
	}

	public void setReceiver(UserSchema receiver) {
		this.receiver = receiver;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	


}
