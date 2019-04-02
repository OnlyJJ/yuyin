package com.jiujun.voice.modules.apps.user.relation.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationRecord;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class RelationRecordDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 加载好友列表
	 */
	@CacheWrite(key = CacheConstants.RELATION_LIST, time = 72000, fields = { "userId" })
	public List<String> getRelations(String userId) {
		String sql = "select targeUserId from t_relation_record where userId=?";
		return jdbcHandle.query(String.class, sql, userId);
	}

	/**
	 * 查询好友关系
	 * 
	 * @param userId
	 * @param targeUserId
	 * @return
	 */
	@CacheWrite(key = CacheConstants.RELATION_INFO, time = 72000, fields = { "userId", "targeUserId" })
	public RelationRecord getRelation(String userId, String targeUserId) {
		String sql = "select * from t_relation_record where userId=? and targeUserId=? limit 1";
		return jdbcHandle.queryFirst(RelationRecord.class, sql, userId, targeUserId);
	}

	/**
	 * 删除好友
	 * 
	 * @param userId
	 * @param targeUserId
	 * @return
	 */
	@CacheWipe(key = CacheConstants.RELATION_INFO, fields = { "userId", "targeUserId" })
	@CacheWipe(key = CacheConstants.RELATION_LIST, fields = { "userId" })
	public Long delRelation(String userId, String targeUserId) {
		String sql = "delete from t_relation_record where userId=? and targeUserId=? limit 1";
		return jdbcHandle.update(sql, userId, targeUserId);
	}

	/**
	 * 拉黑好友
	 * 
	 * @param userId
	 * @param targeUserId
	 * @return
	 */
	@CacheWipe(key = CacheConstants.RELATION_INFO, fields = { "userId", "targeUserId" })
	@CacheWipe(key = CacheConstants.RELATION_LIST, fields = { "userId" })
	public Long ignoreRelation(String userId, String targeUserId) {
		String sql = "update t_relation_record set status=-1 where userId=? and targeUserId=? limit 1";
		return jdbcHandle.update(sql, userId, targeUserId);
	}

	/**
	 * 添加好友
	 * 
	 * @param userId
	 * @param targeUserId
	 * @return
	 */
	@CacheWipe(key = CacheConstants.RELATION_LIST, fields = { "userId" })
	public Long addRelation(String userId, String targeUserId) {
		RelationRecord record = new RelationRecord();
		record.setCreateTime(new Date());
		record.setStatus(1);
		record.setUserId(userId);
		record.setTargeUserId(targeUserId);
		return jdbcHandle.saveOrUpdate(record);
	}
}
