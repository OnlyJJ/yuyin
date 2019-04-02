package com.jiujun.voice.modules.apps.user.robot.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 机器人用户表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_user_robot")
public class UserRobot extends DBModel {
	
	private Integer id;
	/**
	 * 用户id
	 */
	private String userId;
	 
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
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
