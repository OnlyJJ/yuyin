package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.room.domain.RoomMicManage;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomMicManageDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public Long saveOrUpdate(RoomMicManage info) {
		Long code = jdbcHandle.saveOrUpdate(info);
		if(code < 1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	public Long delete(String roomId, int seat) {
		String sql = "delete from t_room_mic_manage where roomId=? and seat=?";
		return jdbcHandle.update(sql, roomId, seat);
	}
	
	public Integer getMicStatus(String roomId, int seat) {
		String sql = "select status from t_room_mic_manage where roomId=? and seat=?";
		return jdbcHandle.queryFirst(Integer.class, sql, roomId, seat);
	}
	
	public List<RoomMicManage> getAll(String roomId) {
		String sql = "select * from t_room_mic_manage where roomId=?";
		return jdbcHandle.query(RoomMicManage.class, sql, roomId);
	}
	
}