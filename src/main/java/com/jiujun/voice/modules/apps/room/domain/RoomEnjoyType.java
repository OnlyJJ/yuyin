package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.config.YmlConfigBuilder;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.jdbc.annotation.DbFilting;
import com.jiujun.voice.common.model.DBModel;

/**
 * t_room_enjoy_type 房间偏好设置表
 * 
 * @author Coody
 */
@SuppressWarnings("serial")
@DBTable("t_room_enjoy_type")
public class RoomEnjoyType extends DBModel {
	@DocFlag("主键id")
	private Integer id;

	@DocFlag("类型（唯一），建议使用首字母，如CJ(吃鸡)")
	private String type;

	@DocFlag("类型名称，如吃鸡游戏")
	@DbFilting(error = "房间类型名称不能包含敏感词")
	private String name;

	@DocFlag("图标")
	private String icon;

	@DocFlag("状态，默认1-有效，0-无效")
	private Integer status;

	@DocFlag("排序")
	private Integer sort;

	@DocFlag("创建时间")
	private Date createTime;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIcon() {
		return YmlConfigBuilder.getProperty(Constants.FILE_DOMAIN) + icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

}
