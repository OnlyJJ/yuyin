package com.jiujun.voice.modules.apps.room.service;

import java.util.List;

import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.modules.apps.room.cmd.schema.MemberInfoSchema;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
/**
 * 
 * @author Coody
 *
 */
public interface RoomInfoService {
	
	/**
	 * 创建房间
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param info
	 */
	Long createRoom(RoomInfo info);
	
	/**
	 * 修改房间信息
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param info
	 * @return
	 */
	Long modifyRoomInfo(RoomInfo info);
	
	/**
	 * 获取房间基本信息
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param roomId
	 * @return
	 */
	RoomInfo getRoomInfo(String roomId);
	
	/**
	 * 根据userId获取个人房间
	 * @author Shao.x
	 * @date 2018年12月18日
	 * @param userId
	 * @return
	 */
	RoomInfo getRoomInfoByUserId(String userId);
	
	/**
	 * 获取房间内，用户个人信息
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @return 本方法不返回null，不存在会抛异常提示
	 */
	MemberInfoSchema getMemberInfo(String roomId, String userId);
	
	/**
	 * 获取房间成员列表
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @param roomId
	 * @return
	 */
	Pager listMembers(String roomId, int pageSize, int pageNo);
	
	/**
	 * 获取用户所在房间
	 * @author Shao.x
	 * @date 2018年12月26日
	 * @param userId
	 * @return
	 */
	RoomInfo getUserInRoom(String userId);
	
	
	public List<RoomInfo> getSystemRoomInfos() ;
}
