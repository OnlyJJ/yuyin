package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.schema;

import java.util.Date;

import com.jiujun.voice.common.config.YmlConfigBuilder;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class UserGeneralSchema extends SchemaModel{

	
	/**
	 * 用户ID
	 */
	@DocFlag("用户ID")
	private String userId;
	/**
	 * 昵称
	 */
	@DocFlag("用户名")
	private String name;
	/**
	 * 头像
	 */
	@DocFlag("头像地址，相对路径")
	private String head;
	
	@DocFlag("头像地址，全路径")
	private String compleHead;
	/**
	 * 性别 0女 1男
	 */
	@DocFlag("性别，0女 1男")
	private Integer sex;
	/**
	 * 年龄
	 */
	@DocFlag("年龄")
	private Integer age;
	/**
	 * 生日 yyyy-MM-dd
	 */
	@DocFlag("生日 yyyy-MM-dd")
	private String birth;
	/**
	 * 个性签名、个人宣言
	 */
	@DocFlag("个人签名")
	private String sign;
	/**
	 * 角色 0普通用户 1主播
	 */
	@DocFlag("角色 0普通用户 1主播 ")
	private Integer role;
	
	/**
	 * 活跃时间
	 */
	@DocFlag("最近活跃时间")
	private Date activeTime;

	

	public String getCompleHead() {
		return YmlConfigBuilder.getProperty(Constants.FILE_DOMAIN) +  head;
	}

	public void setCompleHead(String compleHead) {
		this.compleHead = compleHead;
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

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
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

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}
	
	
}
