package com.jiujun.voice.modules.apps.system.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.system.domain.AppResource;

@Repository
public class AppResourceDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	@CacheWrite(time=3)
	public List<AppResource> getAppResources(){
		String sql="select * from t_app_resource order by id desc ";
		return jdbcHandle.query(AppResource.class,sql);
		
	}
}
