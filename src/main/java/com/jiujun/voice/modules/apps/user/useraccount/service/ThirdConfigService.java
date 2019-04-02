package com.jiujun.voice.modules.apps.user.useraccount.service;

import com.jiujun.voice.modules.apps.user.useraccount.domain.ThirdConfig;
/**
 * 
 * @author Coody
 *
 */
public interface ThirdConfigService {

	/**
	 * 根据包名获取APP配置
	 * @param packager 包名
	 * @param type 2QQ 3微信
	 * @return
	 */
	public ThirdConfig getConfig(String packager,Integer type);
}
