package com.jiujun.voice.modules.apps.user.robot.dao;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.robot.domain.UserRobot;
/**
 * @author Coody
 *
 */
@Repository
public class UserRobotDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public UserRobot getByUserId(String userId) {
		String sql = "select * from t_user_robot where userId = ? limit 1";
		return jdbcHandle.queryFirst(UserRobot.class, sql, userId);
	}
}