package com.jiujun.voice.modules.apps.user.robot.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.jiujun.voice.modules.apps.user.robot.dao.UserRobotDao;
import com.jiujun.voice.modules.apps.user.robot.domain.UserRobot;
import com.jiujun.voice.modules.apps.user.robot.service.UserRobotService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class UserRobotServiceImpl implements UserRobotService{
	@Resource
	UserRobotDao userRobotDao;

	@Override
	public UserRobot getUserRobot(String userId) {
		return userRobotDao.getByUserId(userId);
	}
	
}
