package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomInfoDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 插入房间信息
	 * 
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param info
	 * @return
	 */
	public Long insertRoomInfo(RoomInfo info) {
		Long code = jdbcHandle.insert(info);
		if (code < 0) {
			throw new CmdException(ErrorCode.ERROR_2001);
		}
		return code;
	}

	/**
	 * 修改房间信息
	 * 
	 * @author Shao.x
	 * @date 2018年12月6日
	 * @param info
	 * @return
	 */
	public Long modifyRoomInfo(RoomInfo info) {
		Long code = jdbcHandle.saveOrUpdate(info);
		if (code < 0) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}

	/**
	 * 根据房间号获取房间信息
	 * 
	 * @author Shao.x
	 * @date 2018年12月6日
	 * @param roomId
	 * @return
	 */
	public RoomInfo getRoomInfo(String roomId) {
		String sql = "select * from t_room_info where roomId=? limit 1";
		return jdbcHandle.queryFirst(RoomInfo.class, sql, roomId);
	}

	/**
	 * 获取个人房间
	 * 
	 * @author Shao.x
	 * @date 2018年12月18日
	 * @param userId
	 * @return
	 */
	public RoomInfo getRoomInfoByUserId(String userId) {
		String sql = "select * from t_room_info where userId=? limit 1";
		return jdbcHandle.queryFirst(RoomInfo.class, sql, userId);
	}

	/**
	 * 根据用户id获取房间信息
	 * 
	 * @author Shao.x
	 * @date 2018年12月6日
	 * @param userId
	 * @return
	 */
	public RoomInfo getRoomInfoByUser(String userId) {
		String sql = "select * from t_room_info where userId=? limit 1";
		return jdbcHandle.queryFirst(RoomInfo.class, sql, userId);
	}

	/**
	 * 获取系统房间列表
	 */
	public List<RoomInfo> getSystemRoomInfos() {
		String sql = "select * from t_room_info where grade=1 ";
		return jdbcHandle.query(RoomInfo.class, sql);
	}
}