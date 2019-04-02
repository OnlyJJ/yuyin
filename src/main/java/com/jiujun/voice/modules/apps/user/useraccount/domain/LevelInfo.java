package com.jiujun.voice.modules.apps.user.useraccount.domain;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 等级表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_level_info")
public class LevelInfo extends DBModel {
	
	/**
	 * 类型，1-财富等级，2-魅力等级
	 */
	private Integer type;
	/**
	 * 等级名称
	 */
	private String name;
	/**
	 * 等级，默认从0开始
	 */
	private Integer level;
	/**
	 * 等级对应所需经验值
	 */
	private Long exp;
	
	public Integer getType() {
		return this.type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLevel() {
		return this.level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Long getExp() {
		return this.exp;
	}
	
	public void setExp(Long exp) {
		this.exp = exp;
	}
}
