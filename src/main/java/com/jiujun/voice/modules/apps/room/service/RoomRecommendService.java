package com.jiujun.voice.modules.apps.room.service;

import java.util.List;

import com.jiujun.voice.modules.apps.room.domain.RoomRecommend;

/**
 * @author Coody
 *
 */
public interface RoomRecommendService {
	
	RoomRecommend getRoomRecommend(String roomId);
	
	/**
	 * 获取有效的房间推荐配置
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @return
	 */
	List<RoomRecommend> listRoomRecommend();
}
