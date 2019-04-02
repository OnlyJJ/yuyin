package com.jiujun.voice.modules.apps.system.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.system.domain.ClientApp;

/**
 * @author Coody
 *
 */
@Repository
public class ClientAppDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 获取比当前大的版本列表
	 * 
	 * @param clientType
	 *            0安卓 1IOS
	 * @param packager
	 *            包名
	 * @param currentVersion
	 *            当前版本
	 * @return
	 */

	@CacheWrite
	public List<ClientApp> getNextClientApps(Integer clientType, String packager, String currentVersion) {
		String sql = "select * from t_client_app where type=? and packager=? and version>? and status=1 order by version desc ";
		return jdbcHandle.query(ClientApp.class, sql, clientType, packager, currentVersion);
	}

	/**
	 * 获取最新版本
	 */
	@CacheWrite
	public ClientApp getLastVersion(Integer clientType, String packager) {
		String sql = "select * from t_client_app where type=? and packager=? order by version desc limit 1";
		return jdbcHandle.queryFirst(ClientApp.class, sql, clientType, packager);
	}

	/**
	 * 获取所有包名
	 */
	@CacheWrite
	public List<String> getPackagers(Integer clientType) {
		String sql = "select packager from t_client_app where type=?";
		return jdbcHandle.query(String.class, sql, clientType);
	}
}
