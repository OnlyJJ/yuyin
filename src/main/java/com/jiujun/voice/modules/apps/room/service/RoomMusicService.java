package com.jiujun.voice.modules.apps.room.service;

import java.util.List;

import com.jiujun.voice.modules.apps.room.domain.RoomMusic;

public interface RoomMusicService {

	/**
	 * 获取房间播放的音乐
	 * @param roomId
	 * @return
	 */
	RoomMusic getRoomMusic(String roomId);
	/**
	 * 保存当前正在播放的音乐列表
	 * @param music
	 * @return
	 */
	Long saveRoomMusic(RoomMusic music);
	/**
	 * 移除播放音乐的用户
	 * @param roomId
	 * @return
	 */
	Long removeRoomMusic(String roomId);
	
	/**
	 * 获取所有在播放音乐的房间
	 * @return
	 */
	List<RoomMusic> getTotalRoomMusics();
	/**
	 * 移除播放音乐的用户
	 * @return
	 */
	Long removeRoomMusic(String roomId, String userId);

}
