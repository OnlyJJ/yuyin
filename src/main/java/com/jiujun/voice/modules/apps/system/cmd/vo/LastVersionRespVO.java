package com.jiujun.voice.modules.apps.system.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.system.cmd.vo.schema.ClientAppSchema;

@SuppressWarnings("serial")
public class LastVersionRespVO extends BaseRespVO{

	@DocFlag("安卓最新包信息")
	private ClientAppSchema android;
	@DocFlag("ios最新包信息")
	private ClientAppSchema ios;

	public ClientAppSchema getAndroid() {
		return android;
	}

	public void setAndroid(ClientAppSchema android) {
		this.android = android;
	}

	public ClientAppSchema getIos() {
		return ios;
	}

	public void setIos(ClientAppSchema ios) {
		this.ios = ios;
	}
	
	
}
