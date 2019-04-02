package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.jdbc.annotation.DbFilting;
import com.jiujun.voice.common.model.DBModel;

/**
* t_room_info
* 房间信息表
* @author Coody
*/
@SuppressWarnings("serial")
@DBTable("t_room_info")
public class RoomInfo extends DBModel {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 房间拥有者用户id
	 */
	private String userId;
	/**
	 * 房间id（目前与userId一致，保留以后扩展）
	 */
	private String roomId;
	/**
	 * 房间名称
	 */
	@DbFilting(error="房间名称不能包含敏感词")
	private String name;
	/**
	 * 是否上锁，默认0-否，1-是
	 */
	private Integer lockFlag;
	/**
	 * 房间密码（lockFlag为1时，不为空）
	 */
	private String password;
	/**
	 * 特征，默认0-公开，1-私密（首页不可见）
	 */
	private Integer feature;
	/**
	 * 成员限制数，默认1000
	 */
	private Integer memberLimit;
	/**
	 * 房间类型，关联t_room_type的type
	 */
	private Integer roomType;
	/**
	 * 偏爱类型，关联t_room_enjoy_type的type，可多个，用逗号隔开，最多10个
	 */
	private String enjoyType;
	/**
	 * 使用状态，默认1-正常，2-违规封停
	 */
	private Integer status;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 房间级别归类，0-普通房间，1-系统房间
	 */
	private Integer grade;
	
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
	public String getRoomId() {
		return this.roomId;
	}
	
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Integer getLockFlag() {
		return this.lockFlag;
	}
	
	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getFeature() {
		return this.feature;
	}
	
	public void setFeature(Integer feature) {
		this.feature = feature;
	}
	public Integer getMemberLimit() {
		return this.memberLimit;
	}
	
	public void setMemberLimit(Integer memberLimit) {
		this.memberLimit = memberLimit;
	}
	public String getEnjoyType() {
		return this.enjoyType;
	}
	
	public void setEnjoyType(String enjoyType) {
		this.enjoyType = enjoyType;
	}
	public Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}
	
}
