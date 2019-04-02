package com.jiujun.voice.modules.apps.jewel.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.jewel.domain.JewelCollect;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class JewelCollectDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 插入或更新jewel
	 * @author Shao.x
	 * @date 2019年1月14日
	 * @param jc
	 * @return
	 */
	public Long saveOrUpdate(JewelCollect jc, String addFiel) {
		Long code = jdbcHandle.saveOrUpdate(jc, addFiel);
		if(code < 1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	public JewelCollect getJewelCollect(String userId, String time) {
		String sql = "select * from t_jewel_collect where userId=? and dayTime=? limit 1";
		return jdbcHandle.queryFirst(JewelCollect.class, sql, userId, time);
	}
}