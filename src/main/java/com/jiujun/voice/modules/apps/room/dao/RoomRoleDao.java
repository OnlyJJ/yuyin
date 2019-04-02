package com.jiujun.voice.modules.apps.room.dao;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.room.domain.RoomRole;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomRoleDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 获取用户所在房间的角色信息
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public RoomRole getRoomRole(String roomId, String userId) {
		String sql = "select * from t_room_role where userId=? and roomId=? limit 1";
		return jdbcHandle.queryFirst(RoomRole.class, sql, userId, roomId);
	}
	
	/**
	 * 插入角色信息
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param role
	 * @return
	 */
	public Long insertRole(RoomRole role) {
		Long code = jdbcHandle.insert(role);
		if(code <0) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	/**
	 * 删除角色
	 * @author Shao.x
	 * @date 2018年12月4日
	 * @param userId
	 * @param roomId
	 * @return
	 */
	public Long deleteRole(String userId, String roomId) {
		String sql = "delete from t_room_role where userId=? and roomId=?";
		return jdbcHandle.update(sql, userId, roomId);
	}
	
	public Long saveOrUpdate(RoomRole role) {
		Long code = jdbcHandle.saveOrUpdate(role);
		if(code <0) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
}