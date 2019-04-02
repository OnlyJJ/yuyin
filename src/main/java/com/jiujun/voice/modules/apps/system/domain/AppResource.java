package com.jiujun.voice.modules.apps.system.domain;

import java.util.Date;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

@DBTable("t_app_resource")
@SuppressWarnings("serial")
public class AppResource extends DBModel{

	@DocFlag("资源ID")
	private Integer resId;
	@DocFlag("标题")
	private String title;
	@DocFlag("文件类型,0、帧动画，1、webp或GIF")
	private Integer fileType;
	@DocFlag("动画类型,0、礼物特效，1、座驾特效，2、活动特效")
	private Integer specialType;
	@DocFlag("下载环境，0立即下载 1等待wifi下载")
	private Integer downModel;
	@DocFlag("下载地址")
	private String downPath;
	@DocFlag("单帧播放时长")
	private Integer frameSpacingTime;
	@DocFlag("播放帧序列串")
	private String frameRule;
	@DocFlag("播放区域")
	private Integer playArea;
	@DocFlag("资源包签名")
	private String fileSign;
	@DocFlag("更新时间")
	private Date updateTime;
	
	
	public Integer getResId() {
		return resId;
	}
	public void setResId(Integer resId) {
		this.resId = resId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getFileType() {
		return fileType;
	}
	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}
	public Integer getSpecialType() {
		return specialType;
	}
	public void setSpecialType(Integer specialType) {
		this.specialType = specialType;
	}
	public Integer getDownModel() {
		return downModel;
	}
	public void setDownModel(Integer downModel) {
		this.downModel = downModel;
	}
	public String getDownPath() {
		return downPath;
	}
	public void setDownPath(String downPath) {
		this.downPath = downPath;
	}
	public Integer getFrameSpacingTime() {
		return frameSpacingTime;
	}
	public void setFrameSpacingTime(Integer frameSpacingTime) {
		this.frameSpacingTime = frameSpacingTime;
	}
	public String getFrameRule() {
		return frameRule;
	}
	public void setFrameRule(String frameRule) {
		this.frameRule = frameRule;
	}
	public Integer getPlayArea() {
		return playArea;
	}
	public void setPlayArea(Integer playArea) {
		this.playArea = playArea;
	}
	public String getFileSign() {
		return fileSign;
	}
	public void setFileSign(String fileSign) {
		this.fileSign = fileSign;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	

}
