package com.jiujun.voice.modules.apps.user.relation.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationQueue;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class RelationQueueDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 加载我收到的消息列表
	 * 
	 * @param receiver
	 *            消息接受者用户ID
	 * @return
	 */
	@CacheWrite(key = CacheConstants.RELATION_QUEUE, time = 72000, fields = "receiver")
	public List<RelationQueue> getQueues(String receiver) {
		String sql = "select * from t_relation_queue where receiver=? and status=0 order by createTime desc";
		return jdbcHandle.query(RelationQueue.class, sql, receiver);
	}

	/**
	 * 查询好友申请
	 * 
	 * @param sender
	 *            发起者用户ID
	 * @param receiver
	 *            接受者用户ID
	 * @return
	 */
	public RelationQueue getQueue(String sender, String receiver) {
		String sql = "select * from t_relation_queue where sender=? and receiver=? limit 1";
		return jdbcHandle.queryFirst(RelationQueue.class, sql, sender, receiver);
	}

	/**
	 * 处理好友申请
	 * 
	 * @param sender
	 *            发起者用户ID
	 * @param receiver
	 *            接受者用户ID
	 * @param status
	 *            状态 0待处理 1已同意 2已拒绝
	 * @return
	 */
	@CacheWipe(key = CacheConstants.RELATION_QUEUE, fields = "receiver")
	public Long updateQueue(String sender, String receiver, Integer status) {
		String sql = "update t_relation_queue set status=? where status=0 and sender=? and receiver=? limit 1";
		return jdbcHandle.update(sql, status,  sender, receiver);
	}

	/**
	 * 发起申请
	 */
	@CacheWipe(key = CacheConstants.RELATION_QUEUE, fields = "receiver")
	public Long addQueue(String sender, String receiver) {
		RelationQueue queue = new RelationQueue();
		queue.setReceiver(receiver);
		queue.setSender(sender);
		queue.setStatus(0);
		queue.setUpdateTime(new Date());
		return jdbcHandle.saveOrUpdate(queue);
	}

}
