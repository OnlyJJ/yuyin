package com.jiujun.voice.modules.apps.jewel.dao;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.jewel.domain.DrawJewel;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class DrawJewelDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public DrawJewel getDrawJewel(String userId) {
		String sql = "select * from t_draw_jewel where userId=? limit 1";
		return jdbcHandle.queryFirst(DrawJewel.class, sql, userId);
	}
	
	public Long saveOrUpdate(DrawJewel drawJewel) {
		Long code = jdbcHandle.saveOrUpdate(drawJewel, "totalJewel");
		if(code < 1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
}