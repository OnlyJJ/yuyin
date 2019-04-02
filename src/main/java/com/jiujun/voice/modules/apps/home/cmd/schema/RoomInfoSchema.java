package com.jiujun.voice.modules.apps.home.cmd.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class RoomInfoSchema extends SchemaModel {
	
	@ParamCheck
	@DocFlag("房主用户id")
	private String userId;
	
	@ParamCheck
	@DocFlag("房间id")
	private String roomId;
	
	@ParamCheck
	@DocFlag("用户头像")
	private String icon;
	
	@ParamCheck
	@DocFlag("房间昵称")
	private String name;
	
	@ParamCheck
	@DocFlag("在线人数")
	private int online;
	
	@ParamCheck
	@DocFlag("是否满员，0-否，1-已满员")
	private int full;
	
	@ParamCheck
	@DocFlag("是否上锁，0-没有，1-有锁")
	private int lockFlag;
	
	@ParamCheck
	@DocFlag("房间激活时间")
	private long activeTime;
	
	@ParamCheck
	@DocFlag("房间级别，0-普通房间，1-系统房间")
	private int grade;
	
	@DocFlag("拉流外链地址")
	private String url;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

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

	public int getOnline() {
		return online;
	}

	public void setOnline(int online) {
		this.online = online;
	}

	public int getFull() {
		return full;
	}

	public void setFull(int full) {
		this.full = full;
	}

	public int getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(int lockFlag) {
		this.lockFlag = lockFlag;
	}

	public long getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(long activeTime) {
		this.activeTime = activeTime;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
