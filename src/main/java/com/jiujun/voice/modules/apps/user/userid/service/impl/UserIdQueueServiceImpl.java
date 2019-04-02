package com.jiujun.voice.modules.apps.user.userid.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.user.userid.dao.UserIdQueueDao;
import com.jiujun.voice.modules.apps.user.userid.domain.UserIdQueue;
import com.jiujun.voice.modules.apps.user.userid.service.UserIdQueueService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class UserIdQueueServiceImpl implements UserIdQueueService{

	@Resource
	UserIdQueueDao userIdQueueDao;

	@Override
	public UserIdQueue getUserId() {
		return userIdQueueDao.getUserId();
	}

	@Override
	public Long removeByUuid(String uuid) {
		return userIdQueueDao.removeByUuid(uuid);
	}

	@Override
	public Integer getCount() {
		return userIdQueueDao.getCount();
	}

	@Override
	public Integer insertUserId(List<UserIdQueue> userIdQueues) {
		return userIdQueueDao.insertUserId(userIdQueues);
	}
}
