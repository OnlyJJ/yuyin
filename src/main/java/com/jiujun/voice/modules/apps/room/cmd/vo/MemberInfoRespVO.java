package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 房间成员的个人信息
 * @author Shao.x
 * @date 2018年12月6日
 */
@SuppressWarnings("serial")
public class MemberInfoRespVO extends BaseRespVO {
	@ParamCheck
	@DocFlag("用户id")
	private String userId;
	
	@ParamCheck
	@DocFlag("用户头像")
	private String icon;
	
	@ParamCheck
	@DocFlag("用户昵称")
	private String name;
	
	@DocFlag("年龄")
	private Integer age;
	
	@DocFlag("性别")
	private Integer sex;
	
	@DocFlag("个性签名")
	private String sign;
	
	@DocFlag("关系，-1自己 0陌生人 1好友 2黑名单")
	private Integer relation;
	
	@ParamCheck
	@DocFlag("房间角色，0-普通成员，1-房主，2-管理员")
	private int role;
	
	@DocFlag("财富等级")
	@ParamCheck
	private int expLevel;
	
	@DocFlag("魅力等级")
	@ParamCheck
	private int charmLevel;
	
	@DocFlag("发言状态，0-正常，1-禁言")
	@ParamCheck
	private int talkFlg;
	
	@DocFlag("上麦状态，0-正常，1-禁止上麦")
	@ParamCheck
	private int micFlag;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getRelation() {
		return relation;
	}

	public void setRelation(Integer relation) {
		this.relation = relation;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
}
