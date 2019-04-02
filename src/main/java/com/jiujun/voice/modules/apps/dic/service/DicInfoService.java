package com.jiujun.voice.modules.apps.dic.service;

import com.jiujun.voice.modules.apps.dic.domain.DicInfo;
/**
 * 字典
 * @author Coody
 *
 */
public interface DicInfoService {

	/**
	 * 根据Key查询字典信息
	 * @param key
	 * @return
	 */
	public DicInfo getDicInfo(String key);
	
	/**
	 * 更新字典信息
	 */
	public Long saveDicInfo(DicInfo dicInfo);
}
