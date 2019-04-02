package com.jiujun.voice.modules.im.rongcloud.entity;

import java.util.Map;

import com.jiujun.voice.common.model.EntityModel;
import com.jiujun.voice.modules.im.msg.ImMessage;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class RongCloudMessageEntity extends EntityModel{

	private String url;
	
	private Map<String, Object> data;
	
	private ImMessage message;
	
	
	
	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public ImMessage getMessage() {
		return message;
	}

	public void setMessage(ImMessage message) {
		this.message = message;
	}

		
}
