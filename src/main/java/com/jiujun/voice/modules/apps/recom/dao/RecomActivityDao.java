package com.jiujun.voice.modules.apps.recom.dao;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.recom.domain.RecomActivity;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class RecomActivityDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	
	
	/**
	 * 查询推荐活动
	 */
	@CacheWrite(time=60)
	public List<RecomActivity> getRecomers(){
		String sql="select * from t_recom_activity where status=0 order by seq";
		return jdbcHandle.query(RecomActivity.class,sql);
	}
	
}
