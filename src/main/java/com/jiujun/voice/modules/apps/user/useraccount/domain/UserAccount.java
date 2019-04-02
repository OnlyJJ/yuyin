package com.jiujun.voice.modules.apps.user.useraccount.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 注意：本表不允许save。需要更新内容，请在dao层编写update语句
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable(value = "t_user_account", onekeyWrite = false)
public class UserAccount extends DBModel {

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 财富等级
	 */
	private Integer expLevel;
	/**
	 * 魅力等级
	 */
	private Integer charmLevel;
	/**
	 * 经验
	 */
	private Long exp;
	/**
	 * 魅力值
	 */
	private Long charm;
	/**
	 * 金币数
	 */
	private Integer gold;
	/**
	 * 钻石数
	 */
	private Integer jewel;
	/**
	 * 元宝数
	 */
	private Integer ingot;
	/**
	 * 客户端ID
	 */
	private String clientId;
	/**
	 * 第三方登录唯一ID
	 */
	private String unionId;
	/**
	 * 第三方拉取授权的秘钥
	 */
	private String accessToken;
	/**
	 * 来源0手机 1邮箱 2QQ 3微信 4微博 5游客
	 */
	private Integer referrer;

	/**
	 * 用户状态 0正常 1已封号
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;

	
	
	
	public Integer getIngot() {
		return ingot;
	}

	public void setIngot(Integer ingot) {
		this.ingot = ingot;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getExpLevel() {
		return expLevel;
	}

	public void setExpLevel(Integer expLevel) {
		this.expLevel = expLevel;
	}

	public Integer getCharmLevel() {
		return charmLevel;
	}

	public void setCharmLevel(Integer charmLevel) {
		this.charmLevel = charmLevel;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public Integer getReferrer() {
		return referrer;
	}

	public void setReferrer(Integer referrer) {
		this.referrer = referrer;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
