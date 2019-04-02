package com.jiujun.voice.modules.apps.system.service;

import java.util.List;

import com.jiujun.voice.modules.apps.system.domain.ClientApp;

/**
 * @author Coody
 *
 */
public interface ClientAppService {

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

	public List<ClientApp> getNextClientApps(Integer clientType, String packager, String currentVersion);

	/**
	 * 获取最新版本
	 */
	public ClientApp getLastVersion(Integer clientType, String packager);

	/**
	 * 获取客户端所有包名 0安卓 1IOS
	 * 
	 * @return
	 */
	List<String> getPackagers(Integer clientType);
}
