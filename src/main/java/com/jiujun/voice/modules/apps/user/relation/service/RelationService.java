package com.jiujun.voice.modules.apps.user.relation.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.user.relation.domain.RelationQueue;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationRecord;
import com.jiujun.voice.modules.apps.user.relation.schema.RelationApplySchema;
/**
 * 
 * @author Coody
 *
 */
@Service
public interface RelationService {
	
	
	
	/**
	 * 加载好友关系
	 * @param userId
	 * @param targeUserId
	 * @return
	 */
	public RelationRecord getRelation(String userId, String targeUserId) ;
	
	/**
	 * 加好友
	 * @param userId  当前用户
	 * @param targeUserId 对方用户
	 * @return
	 */
	public void addRelation(String userId,String targeUserId);
	
	/**
	 * 删好友
	 * @param userId 当前用户
	 * @param targeUserId 对方用户
	 * @return
	 */
	public Long unFollow(String userId,String targeUserId);
	
	
	/**
	 * 查询加好友信息
	 * @param sender 消息发送者
	 * @param receiver 消息接受者
	 * @return
	 */
	public RelationQueue getRelationQueue(String sender,String receiver);
	
	/**
	 * 处理好友申请
	 * @param sender  申请者
	 * @param receiver 接受者
	 * @param status 1同意 2拒绝
	 * @return
	 */
	public void doRelation(String sender,String receiver,Integer status);
	
	/**
	 * 拉黑好友
	 * @param userId  当前用户ID
	 * @param targeUserId 好友用户ID
	 */
	public Long ignoreUser(String userId,String targeUserId);
	
	
	/**
	 * 加载消息通知列表
	 */

	public List<RelationApplySchema> getApplyQueues(String userId);
	
	/**
	 * 加载好友列表
	 * @param userId
	 * @return
	 */
	public List<String> getRelations(String userId) ;
}
