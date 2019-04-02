package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 创建房间请求实体
 * @author Shao.x
 * @date 2018年12月3日
 */
@SuppressWarnings("serial")
public class NewRoomReqVO extends BaseReqVO {
	
	@ParamCheck
	@DocFlag("房间名称，长度限制：12字")
	private String name;
	
	@DocFlag("是否上锁，是：1")
	private Integer lockFlag;
	
	@DocFlag("上锁密码，格式:字母加数字，base64")
	private String password;
	
	@DocFlag("成员限制")
	private Integer memberLimit;
	
	@DocFlag("公开or私密，0-公开，1-私密")
	private Integer feature;
	
	@DocFlag("房间类型，1-开黑房（此字段保留扩展）")
	private Integer roomType;
	
	@DocFlag("房间偏好类型，多个用逗号隔开，如CJ（吃鸡）")
	private String enjoyType;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public String getEnjoyType() {
		return enjoyType;
	}

	public void setEnjoyType(String enjoyType) {
		this.enjoyType = enjoyType;
	}
	
}
