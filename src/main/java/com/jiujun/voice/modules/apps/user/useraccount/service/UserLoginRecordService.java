package com.jiujun.voice.modules.apps.user.useraccount.service;

import com.jiujun.voice.modules.apps.user.useraccount.domain.UserLoginRecord;
/**
 * 
 * @author Coody
 *
 */
public interface UserLoginRecordService {

	/**
	 * 插入登录记录
	 * @param record
	 * @return
	 */
	public Long insertLoginRecord(UserLoginRecord record);
	
	/**
	 * 获取用户最后一次登录信息
	 * @param userInfo
	 * @return
	 */
	public UserLoginRecord getLastLoginInfo(String userId);
	/**
	 * 获取设备最后一次登录信息
	 */
	public UserLoginRecord getLastLoginInfoByClient(String clientId);
}
