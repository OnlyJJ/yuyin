package com.jiujun.voice.modules.apps.jewel.service;

import com.jiujun.voice.modules.apps.jewel.domain.CardInfo;

/**
 * 
 * @author Coody
 *
 */
public interface CardInfoService {
	
	Long save(String userId, String cardNO, String owner, String bank, String subbranch, String address);
	
	/**
	 * 获取用户的银行卡信息
	 * @author Shao.x
	 * @date 2019年1月14日
	 * @param userId 
	 * @return
	 */
	CardInfo getCardInfo(String userId);
}
