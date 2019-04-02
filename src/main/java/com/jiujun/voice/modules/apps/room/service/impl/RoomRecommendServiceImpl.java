package com.jiujun.voice.modules.apps.room.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.room.dao.RoomRecommendDao;
import com.jiujun.voice.modules.apps.room.domain.RoomRecommend;
import com.jiujun.voice.modules.apps.room.service.RoomRecommendService;
/**
 * @author Coody
 *
 */
@Service
public class RoomRecommendServiceImpl implements RoomRecommendService{

	@Resource
	RoomRecommendDao roomRecommendDao;
	
	@Override
//	@CacheWrite(time=10*60)
	public List<RoomRecommend> listRoomRecommend() {
		return roomRecommendDao.listRoomRecommend();
	}

	@Override
	public RoomRecommend getRoomRecommend(String roomId) {
		return roomRecommendDao.getRoomRecommend(roomId);
	}
	
}
