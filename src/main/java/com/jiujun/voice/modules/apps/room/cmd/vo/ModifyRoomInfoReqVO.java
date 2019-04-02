package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 修改房间信息请求实体
 * @author Shao.x
 * @date 2018年12月3日
 */
@SuppressWarnings("serial")
public class ModifyRoomInfoReqVO extends RoomBaseReqVO {
	
	@DocFlag("房间名称，长度限制：12字")
	private String name;
	
	@DocFlag("成员上限")
	private Integer memberLimit;
	
	@DocFlag("公开or私密，0-公开，1-私密")
	private Integer feature;
	
	@DocFlag("上锁，0-否，1-是")
	private Integer lockFlag;
	
	@DocFlag("上锁密码，格式:字母加数字，base64")
	private String password;
	
	@DocFlag("偏好，个数限制：10个，多个时用逗号隔开")
	private String enjoyType;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMemberLimit() {
		return memberLimit;
	}

	public void setMemberLimit(Integer memberLimit) {
		this.memberLimit = memberLimit;
	}

	public Integer getFeature() {
		return feature;
	}

	public void setFeature(Integer feature) {
		this.feature = feature;
	}

	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEnjoyType() {
		return enjoyType;
	}

	public void setEnjoyType(String enjoyType) {
		this.enjoyType = enjoyType;
	}
	
	
}
