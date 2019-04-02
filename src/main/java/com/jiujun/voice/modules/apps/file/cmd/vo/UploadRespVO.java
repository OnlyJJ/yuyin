package com.jiujun.voice.modules.apps.file.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class UploadRespVO extends BaseRespVO{

	@DocFlag("完整Http路径")
	private String url;
	@DocFlag("相对路径")
	private String uri;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
