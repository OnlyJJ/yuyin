package com.jiujun.voice.modules.apps.room.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.constants.CacheTimeConstants;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.room.dao.RoomActiveDao;
import com.jiujun.voice.modules.apps.room.domain.RoomActive;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.service.RoomActiveService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
/**
 * @author Coody
 *
 */
@Service
public class RoomActiveServiceImpl implements RoomActiveService{

	@Resource 
	RoomActiveDao roomActiveDao;
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	RoomMicInfoService roomMicInfoService;
	@Resource
	RedisCache redisCache;
	
	@Override
	@CacheWipe(key=CacheConstants.ACTIVE_ROOM_ALL)
	@CacheWipe(key=CacheConstants.ACTIVE_ROOM_INFO, fields="roomId")
	public void activeRoom(String roomId) {
		LogUtil.logger.info("### 激活房间（activeRoom）：roomId = " + roomId);
		RoomInfo room = roomInfoService.getRoomInfo(roomId);
		if(room == null) {
			return;
		}
		RoomActive ra = new RoomActive();
		ra.setRoomId(roomId);
		ra.setUserId(room.getUserId());
		ra.setEnjoyType(room.getEnjoyType());
		ra.setRoomName(room.getName());
		roomActiveDao.saveOrUpdate(ra);
		
		// 更新搜索和首页列表
		long sorce = 0 - DateUtils.getSubTime();
		String key = CacheConstants.ACTIVE_ROOM_SORT;
		redisCache.zadd(key, sorce, roomId);
	}

	@Override
	@CacheWipe(key=CacheConstants.ACTIVE_ROOM_ALL)
	@CacheWipe(key=CacheConstants.ACTIVE_ROOM_INFO, fields="roomId")
	public void closeRoom(String roomId) {
		LogUtil.logger.info("### 关闭激活的房间（closeRoom）：roomId = " + roomId);
		// 如果房间没有人，则删除激活
		String onlineKey = CacheConstants.MEMBER_SORT_DATA + roomId;
		long online = redisCache.zcard(onlineKey);
		if(online <= 0) {
			// 移除首页
			String key = CacheConstants.ACTIVE_ROOM_SORT;
			redisCache.zrem(key, roomId);
			// 删除激活
			roomActiveDao.delete(roomId);
			// 清空房间麦位
			roomMicInfoService.clean(roomId);
		}
	}

	@Override
	public List<RoomActive> searchRoomActive(String condition) {
		LogUtil.logger.info("### 搜索激活的房间（getRoomActive）: condition = " + condition);
		if(StringUtil.checkSearchStr(condition)) {
			return null;
		}
		condition = StringUtil.replaceBlank(condition);
		if(StringUtil.isNullOrEmpty(condition)) {
			return null;
		}
		String conditionLike = MessageFormat.format("%{0}%", condition);
		return roomActiveDao.getRoomActiveByCondition(condition, conditionLike);
	}

	@Override
	public List<String> getAllActiveRoomId() {
		String key = CacheConstants.ACTIVE_ROOM_SORT;
		List<String> roomIds = redisCache.zlist(key, 0, -1);
		if(StringUtil.isAllNull(roomIds)) {
			roomIds = new ArrayList<String>();
			List<RoomActive> list = roomActiveDao.getAll();
			if(list != null && list.size() >0) {
				Map<String, Double> members = new HashMap<String, Double>();
				for(RoomActive ra : list) {
					String roomId = ra.getRoomId();
					long sorce = DateUtils.getSubTime(ra.getCreateTime());
					members.put(roomId, Double.valueOf(sorce));
					roomIds.add(roomId);
				}
				redisCache.zadd(key, members);
			}
		}
		return roomIds;
	}

	@Override
	@CacheWrite(key=CacheConstants.ACTIVE_ROOM_ALL)
	public List<RoomActive> getAllRoomActive() {
		return roomActiveDao.getAll();
	}

	@Override
	@CacheWrite(key=CacheConstants.ACTIVE_ROOM_INFO, fields="roomId", time=CacheTimeConstants.TIME_10M)
	public RoomActive getRoomActive(String roomId) {
		return roomActiveDao.getRoomActive(roomId);
	}


	@Override
	public Long pushEnjoyType(String roomId,String enjoyType){
		return roomActiveDao.pushEnjoyType(roomId, enjoyType);
	}
	
}
