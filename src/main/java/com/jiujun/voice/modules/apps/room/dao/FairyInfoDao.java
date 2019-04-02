package com.jiujun.voice.modules.apps.room.dao;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.room.domain.FairyInfo;

/**
 * 
 * @author Tangys
 *
 */
@Repository
public class FairyInfoDao {

	@Resource
	JdbcHandle jdbcHandle;

	public FairyInfo getCurrentFairy() {
		Date time = new Date();
		String sql = "select * from t_fairy_info where status=? and beginTime < ? and endTime > ? limit 1";
		return jdbcHandle.queryFirst(FairyInfo.class, sql, 1, time, time);
	}
}
