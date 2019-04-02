package com.jiujun.voice.modules.apps.user.userid.dao;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.userid.domain.UserIdPretty;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
/**
 * @author Coody
 *
 */
@Repository
public class UserIdPrettyDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 插入一个靓号
	 * @param pretty
	 * @return
	 */
	public Integer insertUserIdPretty(List<UserIdPretty> prettys) {
		return jdbcHandle.batchSaveOrUpdate(prettys);
	}
	
	
}
