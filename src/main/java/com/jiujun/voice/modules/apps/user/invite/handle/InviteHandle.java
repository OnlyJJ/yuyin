package com.jiujun.voice.modules.apps.user.invite.handle;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.logger.util.LogUtil;


/**
 * 邀请码计算规则
 * 
 * userId+78364164096转36进制
 * @author Coody
 *
 */
@Component
public class InviteHandle {
	
	
	/**
	 * 8位36进制某个基数  本字段固定为10000000(36进制)的10进制值，不允许修改。
	 */
	public static final Long INVITE_BASEERED= 78364164096L;

	/**
	 * 根据userId计算邀请码
	 * @param userId
	 * @return
	 */
	public String getInvite(String userId){
		String invite= Long.toString(Long.valueOf(userId)+INVITE_BASEERED, 36).toUpperCase();
		LogUtil.logger.info("获取用户邀请码>>"+userId+":"+invite);
		return invite;
	}
	
	
	/**
	 * 根据邀请码获得用户ID
	 * @param invite
	 * @return
	 */
	public String getUserIdByInvite(String invite){
		String userId= String.valueOf(Long.parseLong(invite,36)-INVITE_BASEERED);
		LogUtil.logger.info("根据邀请码获取用户ID>>"+userId+":"+invite);
		return userId;
	}
	
	
	public static void main(String[] args) {
		System.out.println(new InviteHandle().getInvite("130905"));
	}

}

