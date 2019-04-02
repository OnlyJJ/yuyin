package com.jiujun.voice.modules.apps.music.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

@SuppressWarnings("serial")
public class AddMusicReqVO extends BaseReqVO{

	@DocFlag("歌名")
	@ParamCheck
	private String title;
	@DocFlag("歌手")
	@ParamCheck
	private String author;
	@DocFlag("类型，0原唱  1翻唱  2伴奏")
	@ParamCheck
	private Integer type;
	@DocFlag("外链/下载地址")
	@ParamCheck
	private String url;
	@DocFlag("文件大小，单位b")
	@ParamCheck
	private Long size;
	@DocFlag("文件签名")
	private String fileSign;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getFileSign() {
		if(StringUtil.isNullOrEmpty(fileSign)){
			return JUUIDUtil.createUuid();
		}
		return fileSign;
	}
	public void setFileSign(String fileSign) {
		this.fileSign = fileSign;
	}
	
	
}
