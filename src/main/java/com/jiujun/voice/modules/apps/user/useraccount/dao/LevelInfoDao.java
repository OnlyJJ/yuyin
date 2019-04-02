package com.jiujun.voice.modules.apps.user.useraccount.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.constants.CacheTimeConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.useraccount.domain.LevelInfo;
/**
 * @author Coody
 *
 */
@Repository
public class LevelInfoDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 获取所有财富等级
	 * @author Shao.x
	 * @date 2018年12月17日
	 * @return
	 */
	@CacheWrite(key=CacheConstants.RICH_LEVEL_ALL, time=CacheTimeConstants.TIME_24H)
	public List<LevelInfo> getAllRich() {
		String sql = "select * from t_level_info where type=1";
		return jdbcHandle.query(LevelInfo.class, sql);
	}
	
	/**
	 * 获取所有魅力等级
	 * @author Shao.x
	 * @date 2018年12月19日
	 * @return
	 */
	@CacheWrite(key=CacheConstants.CHARM_LEVEL_ALL, time=CacheTimeConstants.TIME_24H)
	public List<LevelInfo> getAllCharm() {
		String sql = "select * from t_level_info where type=2";
		return jdbcHandle.query(LevelInfo.class, sql);
	}
	
	/**
	 * 获取当前经验对应的等级
	 * @author Shao.x
	 * @date 2018年12月17日
	 * @param type 类型，1-财富等级，2-魅力等级
	 * @param exp 当前经验
	 * @return
	 */
	public LevelInfo getLevel(int type, long exp) {
		String sql = "select * from t_level_info where type=? and exp<=? order by exp desc limit 1";
		return jdbcHandle.queryFirst(LevelInfo.class, sql, type, exp);
	}
	
}