package com.jiujun.voice.modules.apps.jewel.service;

import com.jiujun.voice.modules.apps.jewel.domain.DrawJewel;

/**
 * 
 * @author Coody
 *
 */
public interface DrawJewelService {
	
	/**
	 * 获取用户收益信息
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param userId
	 * @return
	 */
	DrawJewel getUserDrawJewel(String userId);
	
	/**
	 * 加总收益
	 * @author Shao.x
	 * @date 2019年1月16日
	 * @param userId
	 * @param jewel
	 * @return
	 */
	Long addTotalJewel(String userId, int jewel);
}
