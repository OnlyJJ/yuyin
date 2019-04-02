package com.jiujun.voice.modules.apps.room.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.room.domain.RoomThemeRecord;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.StringUtil;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class RoomThemeRecordDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 获取房间有效的话题
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param roomId 房间，非空
	 * @return
	 */
	public RoomThemeRecord getValidTheme(String roomId) {
		String sql = "select * from t_room_theme_record where roomId=? and status=1 order by id desc";
		List<RoomThemeRecord> list = jdbcHandle.query(RoomThemeRecord.class, sql, roomId);
		if(StringUtil.isNullOrEmpty(list)) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 更新房间话题
	 * @author Shao.x
	 * @date 2018年12月3日
	 * @param record
	 * @return
	 */
	public Long saveTheme(RoomThemeRecord record) {
		Long code = jdbcHandle.saveOrUpdate(record);
		if(code <0) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
}