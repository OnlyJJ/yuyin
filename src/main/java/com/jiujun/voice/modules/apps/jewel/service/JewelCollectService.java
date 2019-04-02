package com.jiujun.voice.modules.apps.jewel.service;

import com.jiujun.voice.modules.apps.jewel.domain.JewelCollect;
/**
 * 
 * @author Coody
 *
 */
public interface JewelCollectService {
	
	Long saveOrUpdate(JewelCollect jc, String addFiel);
	
	/**
	 * 获取用户日收益
	 * @author Shao.x
	 * @date 2019年1月14日
	 * @param userId
	 * @param time
	 * @return
	 */
	JewelCollect getJewelCollect(String userId, String time);
}
