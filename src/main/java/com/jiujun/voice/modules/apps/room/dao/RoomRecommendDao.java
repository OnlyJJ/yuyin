package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.room.domain.RoomRecommend;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomRecommendDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public RoomRecommend getRoomRecommend(String roomId) {
		String sql = "select * from t_room_recommend where roomId=? limit 1";
		return jdbcHandle.queryFirst(RoomRecommend.class, sql, roomId);
	}
	
	public List<RoomRecommend> listRoomRecommend() {
		String sql = "SELECT * FROM t_room_recommend WHERE STATUS=1 AND NOW() BETWEEN beginTime AND endTime ORDER BY sort DESC";
		return jdbcHandle.query(RoomRecommend.class, sql);
	}
}