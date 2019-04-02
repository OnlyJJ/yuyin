package com.jiujun.voice.modules.apps.user.userid.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.modules.apps.user.userid.domain.UserIdQueue;
/**
 * @author Coody
 *
 */
@Repository
public class UserIdQueueDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 查询一个未使用的ID
	 * @return
	 */
	public UserIdQueue getUserId() {
		String uuid=JUUIDUtil.createUuid();
		String sql="update t_user_id_queue set uuid=? where uuid='' order by seq limit 1";
		Long code=jdbcHandle.update(sql,uuid);
		if(code<1) {
			return null;
		}
		sql="select * from t_user_id_queue where uuid=? limit 1";
		UserIdQueue queue= jdbcHandle.queryFirst(UserIdQueue.class, sql, uuid);
		queue.setUuid(uuid);
		return queue;
	}
	
	/**
	 * 移除一个已使用的ID
	 */
	public Long removeByUuid(String uuid) {
		String sql="delete from t_user_id_queue where uuid=? limit 1";
		return jdbcHandle.update(sql,uuid);
	}
	/**
	 * 查询池中还有多少未使用的ID
	 */
	public Integer getCount() {
		String sql="select count(*) from t_user_id_queue where uuid=''";
		return jdbcHandle.queryFirst(Integer.class, sql);
	}
	
	/**
	 * 增加一个用户ID
	 */
	public Integer insertUserId(List<UserIdQueue>  userIdQueues) {
		return jdbcHandle.batchSaveOrUpdate(userIdQueues);
	}
}
