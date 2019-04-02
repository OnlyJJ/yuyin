package com.jiujun.voice.modules.apps.user.useraccount.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.modules.apps.user.useraccount.dao.UserLoginRecordDao;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserLoginRecord;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserLoginRecordService;

/**
 * 
 * @author Coody
 *
 */
@Service
public class UserLoginRecordServiceImpl implements UserLoginRecordService{

	@Resource
	UserLoginRecordDao userLoginRecordDao;
	/**
	 * 插入登录记录
	 * @param record
	 * @return
	 */
	@Override
	public Long insertLoginRecord(UserLoginRecord record){
	return	userLoginRecordDao.insertLoginRecord(record);
	}
	
	/**
	 * 获取用户最后一次登录信息
	 * @param userInfo
	 * @return
	 */
	@Override
	public UserLoginRecord getLastLoginInfo(String userId){
		return userLoginRecordDao.getLastLoginInfo(userId);
	}
	
	/**
	 * 获取设备最后一次登录信息
	 */
	@Override
	@CacheWrite(key=CacheConstants.USER_LOGIN_RECORD,time=72000,fields="clientId")
	public UserLoginRecord getLastLoginInfoByClient(String clientId){
		return userLoginRecordDao.getLastLoginInfoByClient(clientId);
	}
}
