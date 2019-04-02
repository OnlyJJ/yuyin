package com.jiujun.voice.modules.apps.room.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
import com.jiujun.voice.modules.apps.room.domain.RoomEnjoyType;

/**
 * 房间信息响应实体
 * @author Shao.x
 * @date 2018年12月6日
 */
@SuppressWarnings("serial")
public class RoomInfoRespVO extends BaseRespVO {
	@ParamCheck
	@DocFlag("房主用户id")
	private String userId;
	
	@ParamCheck
	@DocFlag("房主头像")
	private String icon;
	
	@ParamCheck
	@DocFlag("房间id")
	private String roomId;
	
	@ParamCheck
	@DocFlag("房间名称")
	private String name;
	
	@DocFlag("房间话题，限制12字")
	private String theme;
	
	@ParamCheck
	@DocFlag("房间类型")
	private Integer roomType;
	
	@ParamCheck
	@DocFlag("上锁，0-否，1-是")
	private Integer lockFlag;
	
	@DocFlag("房间密码")
	private String password;
	
	@ParamCheck
	@DocFlag("成员限制，最低10人，最高1000（默认）")
	private Integer memberLimit;
	
	@ParamCheck
	@DocFlag("公开or私密，0-公开，1-私密")
	private Integer feature;
	
	@DocFlag("房间偏好")
	private List<RoomEnjoyType> data;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public Integer getRoomType() {
		return roomType;
	}

	public void setRoomType(Integer roomType) {
		this.roomType = roomType;
	}

	public Integer getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
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

	public List<RoomEnjoyType> getData() {
		return data;
	}

	public void setData(List<RoomEnjoyType> data) {
		this.data = data;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
