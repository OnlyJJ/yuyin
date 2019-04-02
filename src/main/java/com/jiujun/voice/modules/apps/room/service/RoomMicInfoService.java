package com.jiujun.voice.modules.apps.room.service;

import java.util.List;

import com.jiujun.voice.modules.apps.room.cmd.schema.MicInfoSchema;
import com.jiujun.voice.modules.apps.room.domain.RoomMicInfo;
/**
 * @author Coody
 *
 */
public interface RoomMicInfoService {
	/**
	 * 获取房间麦位信息
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @return
	 */
	List<MicInfoSchema> listRoomMicInfo(String roomId);
	
	/**
	 * 获取具体麦位信息
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @param seat 位置
	 * @return
	 */
	RoomMicInfo getRoomMicInfoBySeat(String roomId, int seat);
	
	/**
	 * 获取用户上麦信息
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @return
	 */
	RoomMicInfo getRoomMicInfoByUser(String userId);
	
	/**
	 * 上、下麦管理
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @param seat 麦位
	 * @param seatType 麦位类型，0-普通，1-主麦
	 * @param opearte 1-上麦，2-下麦
	 * @return
	 */
	Long upAndDownMic(String roomId, String userId, int seat, int seatType, int opearte);
	/**
	 * 下麦
	 */
	Long downMic(String roomId, String userId);
	
	/**
	 * 切换麦位
	 * @author Shao.x
	 * @date 2018年12月23日
	 * @param seat 麦位
	 * @param seatType 麦位类型，0-普通，1-主麦
	 * @return
	 */
	Long changeMic(String roomId, String userId, int seat, int seatType);
	
	/**
	 * 抱上or抱下麦位
	 * @author Shao.x
	 * @date 2018年12月23日
	 * @param roomId 房间
	 * @param userId 操作用户
	 * @param targetUserId 被操作用户
	 * @param seat 位置
	 * @param seatType 麦位类型，0-普通，1-主麦
	 * @param opearte 1-上麦，2-下麦
	 * @return
	 */
	Long manageMic(String roomId, String userId, String targetUserId, int seat, int seatType, int opearte);
	
	/**
	 * 清空房间麦位
	 * @author Shao.x
	 * @date 2018年12月21日
	 * @param roomId
	 */
	void clean(String roomId);
	
	/**
	 * 移除麦位
	 * @author Shao.x
	 * @date 2018年12月24日
	 * @param roomId
	 * @param userId
	 * @return
	 */
	Long remove(String roomId, String userId);
	
	/**
	 * 麦位操作管理
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param roomId 房间
	 * @param userId 操作者
	 * @param seat 位置
	 * @param seatType 麦位类型，0-普通，1-主麦
	 * @param opearte 操作类型，1-锁麦，2-解锁麦位，3-禁麦，4-解禁麦位
	 * @return
	 */
	Long operateMic(String roomId, String userId, int seat, int seatType, int opearte);
	
	
	/**
	 * 获取房间所有麦位
	 */
	public List<RoomMicInfo> listMicInfo(String roomId) ;
	
}
