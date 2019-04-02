package com.jiujun.voice.modules.apps.file.cmd.vo;

import java.util.Base64;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

@SuppressWarnings("serial")
public class UploadByWCRespVO extends BaseRespVO{

	@DocFlag("网宿文件上传Token")
	private String token;
	@DocFlag("上传成功后文件路径")
	private String url;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static void main(String[] args) {
		System.out.println(Base64.getDecoder().decode("Zm5hbWU9dGVtcC5qcGcmdXJsPWh0dHA6Ly9tdXNpYy53b3dvbGl2ZTk5LmNvbS90ZXN0L2ltYWdlLzY1ZDRlYjllYWNmMTQwOTY5NDY2N2I1ZmQwNTZmNjNhLmpwZyZoYXNoPUZrLWQ4Skl5VHZ0UGxEVFp5dVFlV1RMS0pWT0U="));
	}
}
