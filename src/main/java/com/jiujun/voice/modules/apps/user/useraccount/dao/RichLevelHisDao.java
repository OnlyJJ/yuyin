package com.jiujun.voice.modules.apps.user.useraccount.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.useraccount.domain.RichLevelHis;
/**
 * @author Coody
 *
 */
@Repository
public class RichLevelHisDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	  
	public Long save(RichLevelHis cl) {
		return jdbcHandle.insert(cl);
	}
	
	public Integer getLastLevel(int expLevel) {
		String sql = "SELECT sort FROM t_richLevel_his WHERE richLevel = ? ORDER BY sort DESC LIMIT 1";
		return jdbcHandle.queryFirst(Integer.class, sql, expLevel);
	}
}