package com.jiujun.voice.modules.apps.user.userinfo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheTimeConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userinfo.dao.UserInfoDao;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
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
public class UserInfoServiceImpl implements UserInfoService {

	@Resource
	UserInfoDao userInfoDao;
	@Resource
	UserAccountService userAccountService;

	@Override
	public UserInfo initUserInfo(String userId, String name) {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId(userId);
		userInfo.setName(name);
		userInfoDao.insertUserInfo(userInfo);
		return userInfo;
	}

	@Override
	@Transacted
	public Long saveUserInfo(UserInfo userInfo) {
		Long code = userInfoDao.saveUserInfo(userInfo);
		return code;
	}

	@Override
	public UserInfo getUserInfo(String userId) {
		return userInfoDao.getUserInfo(userId);
	}

	@Override
	public UserSchema getUserSchema(String userId) {
		UserInfo userInfo = getUserInfo(userId);
		if (userInfo == null) {
			throw ErrorCode.ERROR_1009.builderException();
		}
		UserAccount userAccount = userAccountService.getUserAccountByUserId(userId);
		UserSchema schema = new UserSchema();
		BeanUtils.copyProperties(userInfo, schema);
		BeanUtils.copyProperties(userAccount, schema);

		return schema;
	}

	@Override
	public List<UserInfo> getUserInfos(String... userIds) {
		return userInfoDao.getUserInfos(userIds);
	}

	@Override
	@CacheWrite(time = CacheTimeConstants.TIME_5M)
	public List<UserSchema> searchByKeyWord(String keyWord) {
		if (StringUtil.checkSearchStr(keyWord)) {
			return null;
		}
		keyWord = StringUtil.replaceBlank(keyWord);
		if (StringUtil.isNullOrEmpty(keyWord)) {
			return null;
		}
		List<UserInfo> users = userInfoDao.searchByKeyWord(keyWord);
		if (StringUtil.isNullOrEmpty(users)) {
			return null;
		}
		return PropertUtil.getNewList(users, UserSchema.class);
	}

	@Override
	public List<UserInfo> getUserInfoByNickName(String name) {
		return userInfoDao.getUserInfoByNickName(name);
	}

	@Override
	public String createNickName(String name, String userId) {
		List<UserInfo> users = getUserInfoByNickName(name);
		if (StringUtil.isNullOrEmpty(users)) {
			return name;
		}
		name = name + userId;
		users = getUserInfoByNickName(name);
		if (StringUtil.isNullOrEmpty(users)) {
			return name;
		}
		for (int i = 0; i < 10; i++) {
			Integer random = StringUtil.getRanDom(1, 9999);
			String nameTemp = name + random;
			users = getUserInfoByNickName(nameTemp);
			if (StringUtil.isNullOrEmpty(users)) {
				return nameTemp;
			}
		}
		throw new CmdException(ErrorCode.ERROR_1004);
	}

}
