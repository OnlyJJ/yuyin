package com.jiujun.voice.modules.apps.user.relation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.user.relation.dao.RelationQueueDao;
import com.jiujun.voice.modules.apps.user.relation.dao.RelationRecordDao;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationQueue;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationRecord;
import com.jiujun.voice.modules.apps.user.relation.schema.RelationApplySchema;
import com.jiujun.voice.modules.apps.user.relation.service.RelationService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;
/**
 * 
 * @author Coody
 *
 */
@Service
public class RelationServiceImpl implements RelationService {

	@Resource
	RelationQueueDao relationQueueDao;

	@Resource
	RelationRecordDao relationRecordDao;

	@Resource
	IMessageHandle imessageHandle;

	@Resource
	UserInfoService userInfoService;

	@Override
	public RelationRecord getRelation(String userId, String targeUserId) {
		return relationRecordDao.getRelation(userId, targeUserId);
	}

	@Override
	public void addRelation(String userId, String targeUserId) {
		Long code = relationQueueDao.addQueue(userId, targeUserId);
		if (code > 0) {
			/**
			 * 推送添加好友消息
			 */
			UserInfo userInfo = userInfoService.getUserInfo(userId);
			imessageHandle.sendGeneralMsg(targeUserId, "好友申请", userInfo.getName() + "添加您为好友",
					MsgEnum.RELATION_NOTICE.getType());
		}
	}

	@Override
	public Long unFollow(String userId, String targeUserId) {
		return relationRecordDao.delRelation(userId, targeUserId);
	}

	@Override
	public RelationQueue getRelationQueue(String sender, String receiver) {
		return relationQueueDao.getQueue(sender, receiver);
	}

	@Override
	@Transacted
	public void doRelation(String sender, String receiver, Integer status) {
		RelationQueue queue = getRelationQueue(sender, receiver);
		if (queue == null || queue.getStatus() != 0) {
			throw ErrorCode.ERROR_1031.builderException();
		}
		Long code = relationQueueDao.updateQueue(sender, receiver, status);
		if (code < 0) {
			throw ErrorCode.ERROR_1031.builderException();
		}
		if(status==2){
			UserInfo userInfo = userInfoService.getUserInfo(receiver);
			imessageHandle.sendGeneralMsg(sender, "好友申请", userInfo.getName() + "拒绝了您的好友申请",
					MsgEnum.RELATION_NOTICE.getType());
			return ;
		}
		// 添加双向关注关系
		code = relationRecordDao.addRelation(sender, receiver);
		if (code < 0) {
			throw ErrorCode.ERROR_1004.builderException();
		}
		// 添加双向关注关系
		code = relationRecordDao.addRelation(receiver, sender);
		if (code < 0) {
			throw ErrorCode.ERROR_1004.builderException();
		}
		// 向申请者推送消息
		UserInfo userInfo = userInfoService.getUserInfo(receiver);
		imessageHandle.sendGeneralMsg(sender, "好友申请", userInfo.getName() + "接受了您的请求并添加您为好友",
				MsgEnum.RELATION_NOTICE.getType());
	}

	@Override
	public Long ignoreUser(String userId, String targeUserId) {
		return relationRecordDao.ignoreRelation(userId, targeUserId);
	}

	@Override
	public List<RelationApplySchema> getApplyQueues(String userId) {
		List<RelationQueue> queues=relationQueueDao.getQueues(userId);
		if(StringUtil.isNullOrEmpty(queues)){
			return null;
		}
		List<RelationApplySchema> schemas=new ArrayList<RelationApplySchema>();
		for(RelationQueue queue:queues){
			UserInfo user=userInfoService.getUserInfo(queue.getSender());
			if(user==null){
				continue;
			}
			RelationApplySchema schema=new RelationApplySchema();
			schema.setHead(user.getHead());
			schema.setName(user.getName());
			schema.setTime(queue.getUpdateTime());
			schema.setStatus(queue.getStatus());
			schema.setUserId(queue.getSender());
			schemas.add(schema);
		}
		return schemas;
	}

	@Override
	public List<String> getRelations(String userId) {
		return relationRecordDao.getRelations(userId);
	}

	public static void main(String[] args) {
		System.out.println("\u7528\u6237\u540d\u4e0d\u80fd\u4e3a\u7a7a");
	}
}
