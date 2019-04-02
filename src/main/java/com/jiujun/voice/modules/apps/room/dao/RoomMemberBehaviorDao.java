package com.jiujun.voice.modules.apps.room.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.room.domain.RoomMemberBehavior;

/**
 * -房间成员行为操作表
 * @author Coody
 *
 */
@Repository
public class RoomMemberBehaviorDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public Long saveOrUpdate(RoomMemberBehavior rmb) {
		Long code = jdbcHandle.saveOrUpdate(rmb);
		if(code <0) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	public Long delete(String roomId, String userId, int behavior) {
		String sql = "delete from t_room_member_behavior where roomId=? and userId=? and behavior=?";
		Long code = jdbcHandle.update(sql, roomId, userId, behavior);
		if(code <1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	/**
	 * 获取用户的房间行为信息
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @param roomId
	 * @param userId
	 * @return
	 */
//	@CacheWrite(key=CacheConstants.ROOM_USER_BEHAVIOR, fields= {"roomId", "userId"}, 
//			time=CacheTimeConstants.TIME_30M)
	public RoomMemberBehavior getRoomMemberBehavior(String roomId, String userId, int behavior) {
		String sql = "select * from t_room_member_behavior where roomId=? and userId=? and behavior=? limit 1";
		return jdbcHandle.queryFirst(RoomMemberBehavior.class, sql, roomId, userId, behavior);
	}
}