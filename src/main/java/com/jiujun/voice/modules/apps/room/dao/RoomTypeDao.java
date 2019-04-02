package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.room.domain.RoomType;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomTypeDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 获取全部有效的房间类型配置
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @return
	 */
	public List<RoomType> listRoomType() {
		String sql = "select * from t_room_type where status=1";
		return jdbcHandle.query(RoomType.class, sql);
	}
}