package com.jiujun.voice.modules.apps.user.useraccount.service;

/**
 * 
 * @author Coody
 *
 */
public interface RichLevelHisService {
	
	/**
	 * 记录升级
	 * @author Shao.x
	 * @date 2019年1月8日
	 * @param userId
	 * @param richLevel 财富等级
	 * @return
	 */
	Long save(String userId, int richLevel);
}
