package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.room.domain.RoomMicInfo;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomMicInfoDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 插入记录
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @param info
	 * @return
	 */
	public Long insert(RoomMicInfo info) {
		Long code = jdbcHandle.insert(info);
		if(code < 1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	public Long saveOrUpdate(RoomMicInfo info) {
		Long code = jdbcHandle.saveOrUpdate(info);
		if(code < 1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	/**
	 * 删除
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @param id
	 * @return
	 */
	public Long delete(Integer id) {
		String sql = "delete from t_room_mic_info where id=?";
		return jdbcHandle.update(sql, id);
	}
	
	/**
	 * 获取房间所有麦位信息
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @param roomId
	 * @return
	 */
	public List<RoomMicInfo> listMicInfo(String roomId) {
		String sql = "select * from t_room_mic_info where roomId=? order by seat asc";
		return jdbcHandle.query(RoomMicInfo.class, sql, roomId);
	}
	
	/**
	 * 获取房间具体麦位信息
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @param seat 位置
	 * @return
	 */
	public RoomMicInfo getRoomMicInfoBySeat(String roomId, int seat) {
		String sql = "select * from t_room_mic_info where roomId=? and seat=? limit 1";
		return jdbcHandle.queryFirst(RoomMicInfo.class, sql, roomId, seat);
	}
	
	/**
	 * 获取用户上麦信息
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @return
	 */
	public RoomMicInfo getRoomMicInfoByUser(String userId) {
		String sql = "select * from t_room_mic_info where userId=? limit 1";
		return jdbcHandle.queryFirst(RoomMicInfo.class, sql, userId);
	}
	
	/**
	 * 删除房间所有麦位
	 * @author Shao.x
	 * @date 2018年12月21日
	 * @param roomId
	 */
	public void deleteAll(String roomId) {
		String sql = "delete from t_room_mic_info where roomId=?";
		jdbcHandle.update(sql, roomId);
	}
	
	public Long delete(String roomId, String userId) {
		String sql = "delete from t_room_mic_info where roomId=? and userId=?";
		return jdbcHandle.update(sql, roomId, userId);
	}
}