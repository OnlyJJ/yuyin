package com.jiujun.voice.modules.apps.home.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.home.domain.BannerInfo;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class BannerInfoDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 获取所有有效的banner配置
	 * @author Shao.x
	 * @date 2018年12月12日
	 * @return
	 */
	public List<BannerInfo> listBannerInfo(int showPlace) {
		String sql = "select * from t_banner_info where status=1 and showPlace=?"
				+ " and NOW() between beginTime and endTime";
		return jdbcHandle.query(BannerInfo.class, sql, showPlace);
	}
}