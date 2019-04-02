package com.jiujun.voice.modules.apps.user.useraccount.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserLoginRecord;
/**
 * @author Coody
 *
 */
@Repository
public class UserLoginRecordDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 插入登录记录
	 * @param record
	 * @return
	 */
	@CacheWipe(key=CacheConstants.USER_LOGIN_RECORD,fields="clientId")
	@CacheWipe(key=CacheConstants.USER_LOGIN_RECORD,fields="userId")
	public Long insertLoginRecord(UserLoginRecord record){
		return jdbcHandle.insert(record);
	}
	
	/**
	 * 获取用户最后一次登录信息
	 * @param userInfo
	 * @return
	 */
	@CacheWrite(key=CacheConstants.USER_LOGIN_RECORD,time=72000,fields="userId")
	public UserLoginRecord getLastLoginInfo(String userId){
		String sql="select * from t_user_login_record where userId=? order by id desc limit 1";
		return jdbcHandle.queryFirst(UserLoginRecord.class, sql, userId);
	}
	
	/**
	 * 获取设备最后一次登录信息
	 */
	@CacheWrite(key=CacheConstants.USER_LOGIN_RECORD,time=72000,fields="clientId")
	public UserLoginRecord getLastLoginInfoByClient(String clientId){
		String sql="select * from t_user_login_record where clientId=? order by id desc limit 1";
		return jdbcHandle.queryFirst(UserLoginRecord.class, sql, clientId);
	}
}
