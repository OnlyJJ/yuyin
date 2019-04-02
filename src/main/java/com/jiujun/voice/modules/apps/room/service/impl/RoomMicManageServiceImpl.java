package com.jiujun.voice.modules.apps.room.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWipes;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.modules.apps.room.dao.RoomMicManageDao;
import com.jiujun.voice.modules.apps.room.domain.RoomMicManage;
import com.jiujun.voice.modules.apps.room.service.RoomMicManageService;
/**
 * @author Coody
 *
 */
@Service
public class RoomMicManageServiceImpl implements RoomMicManageService {

	@Resource
	RoomMicManageDao roomMicManageDao;
	
	@Override
//	@CacheWrite(key = CacheConstants.ROOM_MIC_STATUS, fields = {"roomId","seat"}, time = CacheTimeConstants.TIME_24H)
	public Integer getMicStatus(String roomId, int seat) {
		return roomMicManageDao.getMicStatus(roomId, seat);
	}

	@Override
	@CacheWipes({@CacheWipe(key = CacheConstants.ROOM_MIC_STATUS, fields = {"roomId","seat"}),
		@CacheWipe(key=CacheConstants.ROOM_MIC_SEAT, fields= {"roomId", "seat"})
//		@CacheWipe(key=CacheConstants.ROOM_MIC_LIST, fields= "roomId")
	})
	public Long operateMic(String roomId, int seat, int status, int seatType) {
		RoomMicManage info = new RoomMicManage();
		info.setRoomId(roomId);
		info.setSeat(seat);
		info.setStatus(status);
		info.setSeatType(seatType);
		return roomMicManageDao.saveOrUpdate(info);
	}

	@Override
	public List<RoomMicManage> getAll(String roomId) {
		return roomMicManageDao.getAll(roomId);
	}

	@Override
	@CacheWipe(key = CacheConstants.ROOM_MIC_STATUS, fields = {"roomId","seat"})
	public Long delete(String roomId, int seat) {
		return roomMicManageDao.delete(roomId, seat);
	}
	
}
