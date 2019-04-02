package com.jiujun.voice.modules.apps.room.service;

import java.util.List;

import com.jiujun.voice.modules.apps.room.domain.RoomActive;
/**
 * @author Coody
 *
 */
public interface RoomActiveService {
	
	/**
	 * 激活房间
	 * @author Shao.x
	 * @date 2018年12月13日
	 * @param roomId
	 */
	void activeRoom(String roomId);
	
	/**
	 * 关闭房间（不可被搜索，不在列表中显示）
	 * @author Shao.x
	 * @date 2018年12月13日
	 * @param roomId
	 */
	void closeRoom(String roomId);
	
	/**
	 * 根据房间获取激活房间
	 * @author Shao.x
	 * @date 2018年12月19日
	 * @param roomId
	 * @return
	 */
	RoomActive getRoomActive(String roomId);
	
	/**
	 * 获取所有激活房间
	 * @author Shao.x
	 * @date 2018年12月14日
	 * @return
	 */
	List<RoomActive> getAllRoomActive();
	
	/**
	 * 搜索激活房间
	 * @author Shao.x
	 * @date 2018年12月12日
	 * @param condition 搜索条件，精确用户id，房间id，模糊匹配房间名
	 * @return
	 */
	List<RoomActive> searchRoomActive(String condition);
	
	/**
	 * 获取所有激活房间的id
	 * @author Shao.x
	 * @date 2018年12月13日
	 * @return
	 */
	List<String> getAllActiveRoomId();
	
	
	/**
	 * 更新房间偏好
	 */
	public Long pushEnjoyType(String roomId,String enjoyType);
}
