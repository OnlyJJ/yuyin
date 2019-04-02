package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 
 * @author Coody
 * @date 2018年11月16日
 */
@SuppressWarnings("serial")
public class LoadUserInfoRespVO extends BaseRespVO{

	@DocFlag("用户ID")
	private String userId;
	@DocFlag("昵称")
	private String name;
	@DocFlag("头像地址，相对路径")
	private String head;
	@DocFlag("头像地址，全路径")
	private String compleHead;
	@DocFlag("性别，1男 0女")
	private Integer sex;
	@DocFlag("生日，yyyy-MM-dd")
	private String birth;
	@DocFlag("个性签名")
	private String sign;
	@DocFlag("魅力值")
	private Long charm;
	@DocFlag("下一级魅力值")
	private Long nextLevelCharm;
	@DocFlag("财富值")
	private Long exp;
	@DocFlag("下一级财富值")
	private Long nextLevelExp;
	@DocFlag("魅力等级")
	private Integer charmLevel;
	@DocFlag("财富等级")
	private Integer expLevel;
	@DocFlag("年龄")
	private Integer age;
	@DocFlag("关系，-1自己 0陌生人 1好友 2黑名单")
	private Integer relation;
	@DocFlag("金币余额")
	private Integer gold;
	@DocFlag("钻石余额")
	private Integer jewel;
	@DocFlag("元宝余额")
	private Integer ingot;
	@DocFlag("用户的房间")
	private String roomId;
	@DocFlag("房间是否有锁，0-没有，1-有")
	private Integer lockFlag;
	
	
	
	
	public Integer getIngot() {
		return ingot;
	}
	public void setIngot(Integer ingot) {
		this.ingot = ingot;
	}
	public Long getNextLevelCharm() {
		return nextLevelCharm;
	}
	public void setNextLevelCharm(Long nextLevelCharm) {
		this.nextLevelCharm = nextLevelCharm;
	}
	public Long getNextLevelExp() {
		return nextLevelExp;
	}
	public void setNextLevelExp(Long nextLevelExp) {
		this.nextLevelExp = nextLevelExp;
	}
	public void setCharmLevel(Integer charmLevel) {
		this.charmLevel = charmLevel;
	}
	public void setExpLevel(Integer expLevel) {
		this.expLevel = expLevel;
	}
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	public Integer getJewel() {
		return jewel;
	}
	public void setJewel(Integer jewel) {
		this.jewel = jewel;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Long getCharm() {
		return charm;
	}
	public void setCharm(Long charm) {
		this.charm = charm;
	}
	public Long getExp() {
		return exp;
	}
	public void setExp(Long exp) {
		this.exp = exp;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getCompleHead() {
		return compleHead;
	}
	public void setCompleHead(String compleHead) {
		this.compleHead = compleHead;
	}
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	public Integer getCharmLevel() {
		return charmLevel;
	}
	public Integer getExpLevel() {
		return expLevel;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public Integer getLockFlag() {
		return lockFlag;
	}
	public void setLockFlag(Integer lockFlag) {
		this.lockFlag = lockFlag;
	}
		
}
