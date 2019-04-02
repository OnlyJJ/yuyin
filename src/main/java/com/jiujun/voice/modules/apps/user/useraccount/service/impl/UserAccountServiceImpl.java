package com.jiujun.voice.modules.apps.user.useraccount.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.EncryptUtil;
import com.jiujun.voice.modules.apps.user.useraccount.dao.UserAccountDao;
import com.jiujun.voice.modules.apps.user.useraccount.domain.LevelInfo;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.CharmLevelHisService;
import com.jiujun.voice.modules.apps.user.useraccount.service.LevelInfoService;
import com.jiujun.voice.modules.apps.user.useraccount.service.RichLevelHisService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userid.domain.UserIdQueue;
import com.jiujun.voice.modules.apps.user.userid.service.UserIdQueueService;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;

/**
 * 
 * @author Coody
 *
 *         2018年12月14日
 * 
 * @blog 54sb.org
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

	@Resource
	UserAccountDao userAccountDao;
	@Resource
	UserInfoService userInfoService;
	@Resource
	UserIdQueueService userIdQueueService;
	@Resource
	LevelInfoService levelInfoService;
	@Resource
	CharmLevelHisService charmLevelHisService;
	@Resource
	RichLevelHisService richLevelHisService;
	@Resource
	RedisCache redisCache;

	@Override
	public UserAccount getUserAccount(String userIdOrMobileOrEmail) {
		return userAccountDao.getUserAccount(userIdOrMobileOrEmail);
	}

	@Override
	@Transacted
	public UserAccount initUserAccount(String nickName, String mobile, String email, String unionId, String clientId,
			String password, Integer referrer) {
		UserAccount account = new UserAccount();
		account.setEmail(email);
		account.setMobile(mobile);
		account.setUnionId(unionId);
		account.setClientId(clientId);
		account.setReferrer(referrer);
		// 获取用户ID
		UserIdQueue userIdQueue = userIdQueueService.getUserId();
		if (userIdQueue == null) {
			throw new CmdException(ErrorCode.ERROR_1004);
		}
		account.setUserId(userIdQueue.getCode());
		account.setPassword(EncryptUtil.encryptUserPassword(account.getUserId(), password));
		// 插入用户账务信息
		userAccountDao.insertUserAccount(account);
		if(nickName!=null&&nickName.length()>10){
			nickName=nickName.substring(0,10);
		}
		// 初始化用户基础信息
		nickName = userInfoService.createNickName(nickName,userIdQueue.getCode());
		userInfoService.initUserInfo(userIdQueue.getCode(),nickName);
		userIdQueueService.removeByUuid(userIdQueue.getUuid());
		return account;
	}

	@Override
	@CacheWrite(key = CacheConstants.USER_ACCOUNT, time = 600, fields = "userId")
	public UserAccount getUserAccountByUserId(String userId) {
		return userAccountDao.getUserAccountByUserId(userId);
	}

	@Override
	public Long updateUserAccount(UserAccount account, String upField) {
		return userAccountDao.updateUserAccount(account, upField);
	}


	@Override
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	public Long addExp(String userId, int exp) {
		return userAccountDao.addExp(userId, exp);
	}

	@Override
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	public Long addCharm(String userId, int charm) {
		return userAccountDao.addCharm(userId, charm);
	}



	@Override
	public Long upUserLevel(String userId) {
		StringBuilder log = new StringBuilder();
		log.append("## 处理用户财富升级：userId=").append(userId);
		LogUtil.logger.info(log.toString());
		Long code = -1L;
		UserAccount account = userAccountDao.getUserAccountByUserId(userId);
		if (account == null) {
			return code;
		}
		long nowExp = account.getExp(); // 当前财富经验
		int level = account.getExpLevel(); // 当前等级
		// 获取下一级所需的经验值
		long nextExp = levelInfoService.getNextRichExp(level);
		log.append("，当前等级：level：").append(level).append("，当前经验：").append(nowExp).append("，下一等级所需经验：").append(nextExp);
		LogUtil.logger.info(log.toString());
		if (nextExp == -1) { // 已经是最高级，不再处理升级
			return code;
		}
		// 获取当前经验对应的等级
		LevelInfo levelInfo = levelInfoService.getLevel(2, nowExp);
		int lastLevel = levelInfo.getLevel();
		if(lastLevel > level) { // 实际等级大于当前等级，处理升级
			for(int i=level+1; i<= lastLevel; i++) {
				// 升级记录
				richLevelHisService.save(userId, i);
			}
			// 升级
			code = userAccountDao.upRichLevel(userId, lastLevel); // 升级，则返回1，处理发升级消息业务
			// 删除缓存
			String key = CacheConstants.USER_ACCOUNT + userId;
			redisCache.delCache(key);
			log.append("，升级，升级后等级为：").append(lastLevel);
			LogUtil.logger.info(log.toString());
		}
		return code;
	}

	@Override
	public Long upCharmLevel(String userId) {
		StringBuilder log = new StringBuilder();
		log.append("## 处理用户魅力升级：userId=").append(userId);
		LogUtil.logger.info(log.toString());
		Long code = -1L;
		UserAccount account = userAccountDao.getUserAccountByUserId(userId);
		if (account == null) {
			return code;
		}
		long nowCharm = account.getCharm(); // 当前魅力经验
		int level = account.getCharmLevel(); // 当前等级
		// 获取下一级所需的经验值
		long nextCharm = levelInfoService.getNextCharmExp(level);
		log.append("，当前等级：level：").append(level).append("，当前经验：").append(nowCharm).append("，下一等级所需经验：")
				.append(nextCharm);
		LogUtil.logger.info(log.toString());
		if (nextCharm == -1) { // 已经是最高级，不再处理升级
			return code;
		}
		// 获取当前经验对应的等级
		LevelInfo levelInfo = levelInfoService.getLevel(2, nowCharm);
		int lastLevel = levelInfo.getLevel();
		if(lastLevel > level) { // 实际等级大于当前等级，处理升级
			for(int i=level+1; i<= lastLevel; i++) {
				// 升级记录
				charmLevelHisService.save(userId, i);
			}
			// 升级
			code = userAccountDao.upCharmLevel(userId, lastLevel); // 升级，则返回1，处理发升级消息业务
			// 删除缓存
			String key = CacheConstants.USER_ACCOUNT + userId;
			redisCache.delCache(key);
			log.append("，升级，升级后等级为：").append(lastLevel);
			LogUtil.logger.info(log.toString());
		}
		return code;
	}

	@Override
	public UserAccount getUserAccount(String unionId, Integer referrer) {
		return userAccountDao.getUserAccount(unionId, referrer);
	}

	@Override
	public UserAccount getUserAccountByClientId(String clientId, Integer referrer) {
		return userAccountDao.getUserAccountByClientId(clientId, referrer);
	}

	public static void main(String[] args) {
		System.out.println("123456789123".substring(0,10));
	}


	@Override
	public Long changeAuthToken(UserAccount userAccount) {
		return userAccountDao.writeAccessToken(userAccount);
	}

	@Override
	public Long writeLoginTime(String userId, String loginIp) {
		return userAccountDao.writeLoginTime(userId, loginIp);
	}

	@Override
	public List<UserAccount> getUserAccounts(String clientId) {
		return userAccountDao.getUserAccounts(clientId);
	}

}
