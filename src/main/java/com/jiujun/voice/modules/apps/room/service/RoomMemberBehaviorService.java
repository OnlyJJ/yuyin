package com.jiujun.voice.modules.apps.room.service;

import com.jiujun.voice.modules.apps.room.cmd.schema.MemberInfoSchema;

/**
 * 房间成员行为服务
 * @author Shao.x
 * @date 2018年12月6日
 */
public interface RoomMemberBehaviorService {
	/**
	 * 校验房间是否有密码
	 * @author Shao.x
	 * @date 2018年12月24日
	 * @param roomId
	 * @return
	 */
	Integer checkHasPwd(String roomId);
	
	/**
	 * 校验房间密码
	 * @author Shao.x
	 * @date 2018年12月23日
	 * @param roomId 房间
	 * @param userId 进房间用户
	 * @param password 房间密码
	 */
	void checkRoomPwd(String roomId, String userId, String password);
	/**
	 * 进入房间
	 * @author Shao.x
	 * @date 2018年12月6日
	 * @param type 1-进入房间，2-退出房间
	 * @return
	 */
	MemberInfoSchema inRoom(String roomId, String userId, String password);
	
	/**
	 * 退出房间
	 * @author Shao.x
	 * @date 2018年12月10日
	 */
	void outRoom(String roomId, String userId);
	
	/**
	 * 禁言、解禁
	 * @author Shao.x
	 * @date 2018年12月6日
	 * @param accessUserId 操作者
	 * @param userId 被操作者
	 * @param operate 1-禁言，2-解禁
	 * @return
	 */
	Long manageTalk(String roomId, String accessUserId, String userId, int operate);
	
	/**
	 * 踢出房间
	 * @author Shao.x
	 * @date 2018年12月6日
	 * @param accessUserId 操作者
	 * @param userId 被操作者
	 */
	void forceOut(String roomId, String accessUserId, String userId);
	
	/**
	 * 校验用户在房间内用麦状态
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @return true-正常使用，false-否，被禁麦
	 */
	boolean checkMic(String roomId, String userId);
	
	/**
	 * 校验用户在房间内是否正常发言
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @return true-正常发言，false-禁言
	 */
	boolean checkTalk(String roomId, String userId);
	
	/**
	 * 校验用户是否正常进入房间
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @return true-正常，false-被踢，不能进入房间
	 */
	boolean checkInRoom(String roomId, String userId);
	
	/**
	 * 校验房间是否满员（房主不受限制）
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param roomId
	 * @param userId
	 * @return
	 */
	boolean checkIsFull(String roomId, String userId);
	
	/**
	 * 清除用户状态，包括：房间成员列表，麦位
	 * @author Shao.x
	 * @date 2019年1月18日
	 * @param userId
	 */
	void cleanUser(String userId);
}
