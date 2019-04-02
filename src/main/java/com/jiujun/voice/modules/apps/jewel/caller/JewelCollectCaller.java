package com.jiujun.voice.modules.apps.jewel.caller;


import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
import com.jiujun.voice.modules.apps.gift.service.GiftInfoService;
import com.jiujun.voice.modules.apps.jewel.domain.JewelCollect;
import com.jiujun.voice.modules.apps.jewel.service.JewelCollectService;
import com.jiujun.voice.modules.caller.entity.SendGiftEntity;
import com.jiujun.voice.modules.caller.iface.AbstractSendGiftCaller;

/**
 * 
 * @author Coody
 *
 */
@Component
public class JewelCollectCaller extends AbstractSendGiftCaller {
	
	@Resource
	JewelCollectService jewelCollectService;
	@Resource
	GiftInfoService giftInfoService;
	
	@Override
	public void execute(SendGiftEntity entity) {
		if(entity == null) {
			return;
		}
		String userId = entity.getToUserId();
		GiftInfo gift = giftInfoService.getGiftInfo(entity.getGiftId());
		if(gift == null) {
			return;
		}
		int jewel = gift.getRate() * gift.getPrice() / 100;
		JewelCollect jc = new JewelCollect();
		jc.setJewel(jewel);
		jc.setDayTime(DateUtils.getDateString(DateUtils.YYYYMMDD));
		jc.setUserId(userId);
		jewelCollectService.saveOrUpdate(jc, "jewel");
	}

}
