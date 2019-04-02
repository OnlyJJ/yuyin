package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.room.domain.RoomMusic;

@Repository
public class RoomMusicDao {

	@Resource
	JdbcHandle jdbcHandle;

	@CacheWrite(key = CacheConstants.ROOM_MUSICS, fields = "roomId", time = 600)
	public RoomMusic getRoomMusic(String roomId) {
		String sql = "select * from t_room_music where roomId=? limit 1";
		return jdbcHandle.queryFirst(RoomMusic.class, sql, roomId);
	}

	@CacheWipe(key = CacheConstants.ROOM_MUSICS, fields = "music.roomId")
	public Long saveRoomMusic(RoomMusic music) {
		return jdbcHandle.saveOrUpdate(music);
	}

	@CacheWipe(key = CacheConstants.ROOM_MUSICS, fields = "roomId")
	public Long removeRoomMusic(String roomId) {
		String sql = "delete from t_room_music where roomId=? limit 1";
		return jdbcHandle.update(sql, roomId);
	}
	@CacheWipe(key = CacheConstants.ROOM_MUSICS, fields = "roomId")
	public Long removeRoomMusic(String roomId,String userId) {
		String sql = "delete from t_room_music where roomId=? and userId=? limit 1";
		return jdbcHandle.update(sql, roomId,userId);
	}
	public List<RoomMusic> getTotalRoomMusics(){
		String sql="select userId,roomId from t_room_music";
		return jdbcHandle.query(RoomMusic.class, sql);
	}
}
