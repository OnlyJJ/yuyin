package com.jiujun.voice.modules.apps.verificat.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.model.EntityModel;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.common.utils.StringUtil;
/**
 * 
 * @author Coody
 *
 */
@Service
public class VerificatService {

	@Resource
	RedisCache redisCache;

	/**
	 * 创建验证码到验证池
	 * 
	 * @param account
	 * @return
	 */
	public Integer createVerificat(String account) {
		VerificatInfo verificat = getCurrentGlobalInfo(account);
		if (verificat != null) {
			if (System.currentTimeMillis() - verificat.getCreateTime().getTime() < 1000 * 60) {
				throw new CmdException(ErrorCode.ERROR_1013);
			}
		}
		if (verificat == null) {
			Integer code = StringUtil.getRanDom(100000, 999999);
			verificat = new VerificatInfo();
			verificat.setCode(code);
		}
		verificat.setCreateTime(new Date());
		String key = CacheConstants.USER_VERCODE + ":" + account;
		redisCache.setCache(key, verificat, 60 * 10);
		return verificat.getCode();
	}

	/**
	 * 获取池中验证码信息
	 * 
	 * @param account
	 * @return
	 */
	public VerificatInfo getCurrentGlobalInfo(String account) {
		String key = CacheConstants.USER_VERCODE + ":" + account;
		VerificatInfo verificat = redisCache.getCache(key);
		return verificat;
	}

	/**
	 * 校验验证码
	 * 
	 * @param account 用户名、手机号、邮箱
	 * @param code    传入验证码值
	 * @return
	 */
	public boolean doVerification(String account, Integer code) {
		if (StringUtil.isNullOrEmpty(code)) {
			return false;
		}
		String key = CacheConstants.USER_VERCODE + ":" + account;
		VerificatInfo verificat = redisCache.getCache(key);
		if (verificat == null) {
			return false;
		}
		// 验证码错误三次直接销毁
		if (verificat.getCheckNum() > 3) {
			redisCache.delCache(key);
			return false;
		}
		if (code != verificat.getCode().intValue()) {
			verificat.setCheckNum(verificat.getCheckNum() + 1);
			redisCache.setCache(key, verificat);
			return false;
		}
		redisCache.delCache(key);
		return true;
	}

	public String createAuthToken(String account) {
		String authKey = JUUIDUtil.createUuid();
		String key = CacheConstants.USER_VERCODE_AUTHTOKEN + ":" + authKey;
		redisCache.setCache(key, account, 60 * 60);
		return authKey;
	}

	public String getAccount(String authKey) {
		String key = CacheConstants.USER_VERCODE_AUTHTOKEN + ":" + authKey;
		return redisCache.getCache(key);
	}

	@SuppressWarnings("serial")
	public static class VerificatInfo extends EntityModel {

		/**
		 * 验证码
		 */
		private Integer code;
		/**
		 * 创建时间
		 */
		private Date createTime = new Date();
		/**
		 * 校验次数
		 */
		private Integer checkNum = 0;

		public Integer getCode() {
			return code;
		}

		public void setCode(Integer code) {
			this.code = code;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public Integer getCheckNum() {
			return checkNum;
		}

		public void setCheckNum(Integer checkNum) {
			this.checkNum = checkNum;
		}

	}
}
