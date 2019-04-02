package com.jiujun.voice.modules.apps.home.domain;

import java.util.Date;

import com.jiujun.voice.common.config.YmlConfigBuilder;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_banner_info")
public class BannerInfo extends DBModel {
	
	/**
	 * 主键
	 */
	private Integer id;
	
	@DocFlag("标题")
	private String title;
	 
	@DocFlag("跳转地址")
	private String url;
	 
	@DocFlag("显示的图片")
	private String showImg;
	 
	@DocFlag(" 状态：0：停用 1：启用")
	private Integer status;
	 
	@DocFlag("启用开始时间")
	private Date beginTime;
 
	@DocFlag("结束时间")
	private Date endTime;
	 
	@DocFlag("创建时间")
	private Date createTime;
	
	@DocFlag("展示位置，1-首页，2-发现，3-充值中心")
	private Integer showPlace;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return this.url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	public String getShowImg() {
		return YmlConfigBuilder.getProperty(Constants.FILE_DOMAIN) +  this.showImg;
	}
	
	public void setShowImg(String showImg) {
		this.showImg = showImg;
	}
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getBeginTime() {
		return this.beginTime;
	}
	
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return this.endTime;
	}
	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getShowPlace() {
		return showPlace;
	}

	public void setShowPlace(Integer showPlace) {
		this.showPlace = showPlace;
	}
	
}
