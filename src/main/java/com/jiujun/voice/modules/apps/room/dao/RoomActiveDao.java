package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.room.domain.RoomActive;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomActiveDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 插入记录
	 * @author Shao.x
	 * @date 2018年12月12日
	 * @return
	 */
	public Long insert(RoomActive ra) {
		return jdbcHandle.insert(ra);
	}
	
	/**
	 * 插入或更新
	 * @author Shao.x
	 * @date 2018年12月13日
	 * @param ra
	 * @return
	 */
	public Long saveOrUpdate(RoomActive ra) {
		return jdbcHandle.saveOrUpdate(ra);
	}
	
	/**
	 * 删除记录
	 * @author Shao.x
	 * @date 2018年12月13日
	 * @param roomId
	 * @return
	 */
	public Long delete(String roomId) {
		String sql = "delete from t_room_active where roomId=?";
		return jdbcHandle.update(sql, roomId);
	}
	
	/**
	 * 搜索激活房间
	 * @author Shao.x
	 * @date 2018年12月13日
	 * @param condition 精确匹配条件
	 * @param conditionLike 模糊匹配条件
	 * @return
	 */
	public List<RoomActive> getRoomActiveByCondition(String condition, String conditionLike) {
		String sql = "select * from t_room_active where roomId=? or userId=? or roomName like ? limit 64";
		return jdbcHandle.query(RoomActive.class, sql, condition, condition, conditionLike);
	}
	
	/**
	 * 获取所有激活房间
	 * @author Shao.x
	 * @date 2018年12月13日
	 * @return
	 */
	public List<RoomActive> getAll() {
		String sql = "select * from t_room_active";
		return jdbcHandle.query(RoomActive.class, sql);
	}
	
	/**
	 * 根据房间id获取激活房间
	 * @author Shao.x
	 * @date 2018年12月19日
	 * @param roomId
	 * @return
	 */
	public RoomActive getRoomActive(String roomId) {
		String sql = "select * from t_room_active where roomId=?";
		return jdbcHandle.queryFirst(RoomActive.class, sql, roomId);
	}
	
	
	/**
	 * 更新偏好设置
	 * @param roomId
	 * @param enjoyType
	 * @return
	 */
	public Long pushEnjoyType(String roomId,String enjoyType){
		String sql="update t_room_active set enjoyType=? where roomId=? limit 1";
		return jdbcHandle.update(sql, enjoyType,roomId);
	}
}