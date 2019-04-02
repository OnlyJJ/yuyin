package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.config.YmlConfigBuilder;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 房间类型配置表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_room_type")
public class RoomType extends DBModel {
	
	private Integer id;
	/**
	 * 房间类型，1-组队，2-公会
	 */
	private Integer type;
	/**
	 * 图标
	 */
	private String icon;
	/**
	 * 类型名称，组队，公会
	 */
	private String name;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getIcon() {
		return YmlConfigBuilder.getProperty(Constants.FILE_DOMAIN) 	 + icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
