package com.jiujun.voice.modules.apps.gift.service;

import java.util.List;
import java.util.Map;

import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
/**
 * 
 * @author Coody
 *
 */
public interface GiftInfoService {



	public List<GiftInfo> loadGiftInfos() ;

	public GiftInfo getGiftInfo(Integer giftId) ;
	
	public Map<Integer, GiftInfo> getGiftInfos(Integer ...giftId);
}
