package com.jiujun.voice.modules.apps.room.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheTimeConstants;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.modules.apps.room.dao.RoomEnjoyTypeDao;
import com.jiujun.voice.modules.apps.room.domain.RoomEnjoyType;
import com.jiujun.voice.modules.apps.room.service.RoomEnjoyTypeService;
/**
 * @author Coody
 *
 */
@Service
public class RoomEnjoyTypeServiceImpl implements RoomEnjoyTypeService{

	@Resource
	RoomEnjoyTypeDao roomEnjoyTypeDao;
	
	@Override
	@CacheWrite(time = CacheTimeConstants.TIME_10M)
	public List<RoomEnjoyType> listRoomEnjoyConf() {
		return roomEnjoyTypeDao.listRoomEnjoyConf();
	}

	@Override
	public List<RoomEnjoyType> listRoomEnjoyType(String types) {
		String[] enjoys = types.split(Constants.SEPARATOR_COMMA);
		List<RoomEnjoyType> ret = new ArrayList<RoomEnjoyType>();
		Map<String, RoomEnjoyType> all = getAllEnjoyType();
		if(enjoys.length >0 && all.size() >0) {
			for(String type : enjoys) {
				ret.add(all.get(type));
			}
		}
		return ret;
	}
	
	private Map<String, RoomEnjoyType> getAllEnjoyType() {
		List<RoomEnjoyType> all = listRoomEnjoyConf();
		Map<String, RoomEnjoyType> data = new HashMap<String, RoomEnjoyType>();
		if(all != null && all.size() >0) {
			for(RoomEnjoyType ret : all) {
				data.put(ret.getType(), ret);
			}
		}
		return data;
	}
	
}
