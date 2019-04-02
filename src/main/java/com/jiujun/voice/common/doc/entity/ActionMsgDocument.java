package com.jiujun.voice.common.doc.entity;

import com.jiujun.voice.common.model.EntityModel;

/**
 * action报文
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ActionMsgDocument extends EntityModel{

	private String fingerprint;
	
	private String msg;



	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
