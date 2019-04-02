package com.jiujun.voice.modules.apps.room.service;

import java.util.List;

import com.jiujun.voice.modules.apps.room.domain.RoomEnjoyType;
/**
 * 
 * @author Coody
 *
 */
public interface RoomEnjoyTypeService {
	
	/**
	 * 获取房间偏好配置信息
	 * @author Shao.x
	 * @date 2018年11月30日
	 * @return
	 */
	List<RoomEnjoyType> listRoomEnjoyConf();
	
	/**
	 * 根据具体类型获取详细配置信息
	 * @author Shao.x
	 * @date 2018年12月12日
	 * @param types 多个用逗号隔开，如CJ,WZRY
	 * @return
	 */
	List<RoomEnjoyType> listRoomEnjoyType(String types);
}
