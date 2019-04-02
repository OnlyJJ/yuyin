package com.jiujun.voice.modules.apps.user.userid.service;

import java.util.List;

import com.jiujun.voice.modules.apps.user.userid.domain.UserIdQueue;

/**
 * 用户ID序列
 * @author Coody
 *
 */
public interface UserIdQueueService {

	/**
	 * 获取一个未使用的用户ID
	 */
	public UserIdQueue getUserId();
	
	/**
	 * 清除一个已使用的ID
	 */
	public Long removeByUuid(String uuid);
	
	/**
	 * 查询池中还有多少未使用的ID
	 */
	public Integer getCount();
	
	/**
	 * 增加一个用户ID
	 */
	public Integer insertUserId(List<UserIdQueue> userIdQueues);
	
}
