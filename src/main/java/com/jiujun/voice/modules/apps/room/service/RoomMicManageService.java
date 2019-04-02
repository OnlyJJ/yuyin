package com.jiujun.voice.modules.apps.room.service;

import java.util.List;

import com.jiujun.voice.modules.apps.room.domain.RoomMicManage;
/**
 * @author Coody
 *
 */
public interface RoomMicManageService {
	/**
	 * 获取麦位状态
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param roomId
	 * @param seat
	 * @return 麦位状态，0-正常，1-禁麦，2-锁麦
	 */ 
	Integer getMicStatus(String roomId, int seat);
	
	/**
	 * 获取房间所有被禁、锁的麦位
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param roomId
	 * @return
	 */
	List<RoomMicManage> getAll(String roomId);
	
	/**
	 * 管理麦位
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param roomId
	 * @param seat
	 * @param status 麦位状态，1-禁麦，2-锁麦
	 * @return
	 */
	Long operateMic(String roomId, int seat, int status, int seatType);
	
	Long delete(String roomId, int seat);
}
