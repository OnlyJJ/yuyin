package com.jiujun.voice.modules.apps.room.cmd.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;

@SuppressWarnings("serial")
public class RoomMusicSchema extends SchemaModel{

	@DocFlag("持有者ID")
	private String userId;
	@DocFlag("歌名")
	private String title;
	@DocFlag("歌手")
	private String author;
	@DocFlag("类型，0原唱  1翻唱  2伴奏")
	private Integer type;
	@DocFlag("外链/下载地址")
	private String url;
	@DocFlag("文件大小，单位b")
	private Long size;
	@DocFlag("文件签名")
	private String fileSign;
	@DocFlag("音乐播放状态，0未播放  1正在播放 -1暂停")
	private Integer status;
	
	
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
		return fileSign;
	}
	public void setFileSign(String fileSign) {
		this.fileSign = fileSign;
	}
	
	
}
