package com.jiujun.voice.modules.apps.music.domain;

import java.util.Date;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.jdbc.annotation.DbFilting;
import com.jiujun.voice.common.model.DBModel;

@SuppressWarnings("serial")
@DBTable("t_music_info")
public class MusicInfo extends DBModel {

	@DocFlag("音乐ID")
	private Long id;
	@DocFlag("上传者ID")
	private String userId;
	@DocFlag("歌名")
	@DbFilting(error = "歌曲名称不能包含敏感词")
	private String title;
	@DocFlag("歌手")
	@DbFilting(error = "歌手不能包含敏感词")
	private String author;
	@DocFlag("类型，0原唱  1翻唱  2伴奏")
	private Integer type;
	@DocFlag("外链/下载地址")
	private String url;
	@DocFlag("文件大小，单位b")
	private Long size;
	@DocFlag("文件签名")
	private String fileSign;
	@DocFlag("下载次数")
	private Long downNum;
	@DocFlag("播放次数")
	private Long playNum;

	@DocFlag("上传时间")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDownNum() {
		return downNum;
	}

	public void setDownNum(Long downNum) {
		this.downNum = downNum;
	}

	public Long getPlayNum() {
		return playNum;
	}

	public void setPlayNum(Long playNum) {
		this.playNum = playNum;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
