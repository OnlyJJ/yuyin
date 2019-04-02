package com.jiujun.voice.common.cmd.entity;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.model.EntityModel;

/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class CmdFlushEntity extends EntityModel{
	
	
	private RootCmd instance;
	
	private Header header;
	
	private String body;


	public RootCmd getInstance() {
		return instance;
	}

	public void setInstance(RootCmd instance) {
		this.instance = instance;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	
}
