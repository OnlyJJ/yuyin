package com.jiujun.voice.modules.apps.user.invite.service;

import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.modules.apps.user.invite.domain.InvitePrizedRecord;
import com.jiujun.voice.modules.apps.user.invite.domain.InviteRecord;

public interface InviteService {

	
	/**
	 * 根据用户ID查询邀请奖励
	 */
	public InvitePrizedRecord getUserInvitePrize(String userId);
	
	/**
	 * 加载我的邀请列表
	 */
	public Pager getInvitePager(String userId,Pager pager);
	
	/**
	 * 录入我的邀请人
	 * @param userId 当前用户
	 * @param inviterCode 邀请码
	 * @return
	 */
	public Long inputInvite(String userId,String inviterCode);
	/**
	 * 查询用户的伯乐(邀请者)
	 * @param registranter 被邀请者用户ID
	 * @return
	 */
	public InviteRecord getInviteRecordByUserId(String registranter);
	
	/**
	 * 记录奖励
	 * @param userId
	 * @param ingot  可为0
	 * @param gold 可为0
	 * @return
	 */
	public Long pushUserInvitePrize(String userId,Integer ingot,Integer gold);
}
