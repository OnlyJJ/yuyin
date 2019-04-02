package com.jiujun.voice.modules.apps.user.useraccount.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.useraccount.domain.CharmLevelHis;
/**
 * @author Coody
 *
 */
@Repository
public class CharmLevelHisDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	  
	public Long save(CharmLevelHis cl) {
		return jdbcHandle.insert(cl);
	}
	
	public Integer getLastLevel(int charmLevel) {
		String sql = "SELECT sort FROM t_charmLevel_his WHERE charmLevel = ? ORDER BY sort DESC LIMIT 1";
		return jdbcHandle.queryFirst(Integer.class, sql, charmLevel);
	}
}