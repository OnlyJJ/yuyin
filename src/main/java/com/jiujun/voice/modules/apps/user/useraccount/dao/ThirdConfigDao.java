package com.jiujun.voice.modules.apps.user.useraccount.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.useraccount.domain.ThirdConfig;
/**
 * @author Coody
 *
 */
@Repository
public class ThirdConfigDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	@CacheWrite(fields={"packager","type"})
	public ThirdConfig getConfig(String packager,Integer type) {
		String sql="select * from t_third_config where packager=? and type=? limit 1";
		return jdbcHandle.queryFirst(ThirdConfig.class, sql,packager,type);
	}
}
