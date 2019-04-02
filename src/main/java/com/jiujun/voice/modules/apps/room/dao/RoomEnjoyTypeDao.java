package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.room.domain.RoomEnjoyType;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomEnjoyTypeDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 获取全部有效的房间偏好配置
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @return
	 */
	public List<RoomEnjoyType> listRoomEnjoyConf() {
		String sql = "select * from t_room_enjoy_type where status=1 order by sort desc,id asc";
		return jdbcHandle.query(RoomEnjoyType.class, sql);
	}
	
	/**
	 * 根据类型获取详细信息
	 * @author Shao.x
	 * @date 2018年12月12日
	 * @param types 如'CJ,WZRY'
	 * @return
	 */
	public List<RoomEnjoyType> listRoomEnjoyType(String types) {
		String sql = "select * from t_room_enjoy_type where status=1 and type in (?) "
				+ " order by sort desc,id asc";
		return jdbcHandle.query(RoomEnjoyType.class, sql, types);
	}
}