package com.jiujun.voice.modules.apps.user.userinfo.domain;

import java.util.Calendar;
import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.jdbc.annotation.DbFilting;
import com.jiujun.voice.common.model.DBModel;
import com.jiujun.voice.common.utils.DateUtils;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@SuppressWarnings("serial")
@DBTable("t_user_info")
public class UserInfo extends DBModel {

	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 昵称
	 */
	@DbFilting(error="用户昵称不能包含敏感词")
	private String name;
	/**
	 * 头像
	 */
	private String head;
	/**
	 * 性别 0女 1男
	 */
	private Integer sex;
	/**
	 * 年龄
	 */
	private Integer age;
	/**
	 * 生日 yyyy-MM-dd
	 */
	private String birth;
	/**
	 * 个性签名、个人宣言
	 */
	@DbFilting(error="用户签名不能包含敏感词")
	private String sign;
	/**
	 * 角色 0普通用户 1主播
	 */
	private Integer role;

	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 所在城市
	 */
	private String city;
	/**
	 * 所在省份
	 * @return
	 */
	private String province;
	
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
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
		return this.head;
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
		if (age == null) {
			this.age = age;
		}
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
		if (birth != null) {
			this.age = getAge(DateUtils.toDate(birth));
		}
	}

	public int getAge(Date birthDay) {
		if (birthDay == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthDay)) {
			return 0;
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
