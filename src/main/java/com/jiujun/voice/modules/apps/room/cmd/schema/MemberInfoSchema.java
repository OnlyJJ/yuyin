package com.jiujun.voice.modules.apps.room.cmd.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class MemberInfoSchema extends SchemaModel {
	 
	@DocFlag("用户头像")
	private String icon;
	
	@DocFlag("用户昵称")
	private String name;
	
	@DocFlag("房间角色，0-普通成员，1-房主，2-管理员")
	private int role;
	
	@DocFlag(" 财富等级")
	private int expLevel;
	
	@DocFlag("魅力等级")
	private int charmLevel;
	 
	@DocFlag("发言状态，0-正常，1-禁言")
	private int talkFlg;
	
	@DocFlag("上麦状态，0-正常，1-禁止上麦")
	private int micFlag;
	
	@DocFlag("用户所在房间")
	private String roomId;
	
	@DocFlag("用户自己的房间，没有不返回")
	private String myRoomId;
	
	@DocFlag("用户id")
	private String userId;
	@DocFlag("性别")
	private Integer sex; 

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public int getExpLevel() {
		return expLevel;
	}

	public void setExpLevel(int expLevel) {
		this.expLevel = expLevel;
	}

	public int getCharmLevel() {
		return charmLevel;
	}

	public void setCharmLevel(int charmLevel) {
		this.charmLevel = charmLevel;
	}

	public int getTalkFlg() {
		return talkFlg;
	}

	public void setTalkFlg(int talkFlg) {
		this.talkFlg = talkFlg;
	}

	public int getMicFlag() {
		return micFlag;
	}

	public void setMicFlag(int micFlag) {
		this.micFlag = micFlag;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMyRoomId() {
		return myRoomId;
	}

	public void setMyRoomId(String myRoomId) {
		this.myRoomId = myRoomId;
	}
	
	
}
