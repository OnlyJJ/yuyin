package com.jiujun.voice.modules.apps.music.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

@SuppressWarnings("serial")
public class GetCdnUrlReqVO extends BaseReqVO{

	@DocFlag("文件摘要，MD5")
	private String fileSign;
	@DocFlag("文件大小,精确到b")
	private Long size;
	public String getFileSign() {
		return fileSign;
	}
	public void setFileSign(String fileSign) {
		this.fileSign = fileSign;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	
	
}
