package com.jiujun.voice.modules.apps.room.service;

import com.jiujun.voice.modules.apps.room.domain.RoomRole;
/**
 * @author Coody
 *
 */
public interface RoomRoleService {
	
	Long insert(RoomRole role);
	
	Long saveOrUpdate(RoomRole role);

	/**
	 * 获取用户所在房间角色信息
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @return
	 */
	RoomRole getRoomRole(String roomId, String userId);
	
	/**
	 * 设置管理员，1-上管理，2-下管理
	 * @author Shao.x
	 * @date 2018年12月4日
	 * @param accessUserId 操作者
	 * @param userId 被操作者
	 * @param roomId 房间
	 * @param operate 操作类型，1-上管理，2-下管理
	 */
	Long handleManage(String accessUserId, String userId, String roomId, int operate);
}
