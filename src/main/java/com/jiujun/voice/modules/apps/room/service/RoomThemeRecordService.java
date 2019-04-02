package com.jiujun.voice.modules.apps.room.service;

import com.jiujun.voice.modules.apps.room.domain.RoomThemeRecord;
/**
 * @author Coody
 *
 */
public interface RoomThemeRecordService {

	/**
	 * 获取有效的房间话题
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param roomId
	 * @return
	 */
	RoomThemeRecord getValidTheme(String roomId);
	
	/**
	 * 更新房间话题
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param record
	 * @return
	 */
	Long saveTheme(String userId, String roomId, String theme);
}
