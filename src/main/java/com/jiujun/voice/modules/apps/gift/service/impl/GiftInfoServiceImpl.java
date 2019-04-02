package com.jiujun.voice.modules.apps.gift.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.gift.dao.GiftDao;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
import com.jiujun.voice.modules.apps.gift.service.GiftInfoService;

/**
 * 
 * @author Coody
 *
 */
@Service
@SuppressWarnings("unchecked")
public class GiftInfoServiceImpl implements GiftInfoService {

	@Resource
	GiftDao giftDao;

	@Override
	public List<GiftInfo> loadGiftInfos() {
		return giftDao.loadGiftInfos();
	}

	@Override
	public GiftInfo getGiftInfo(Integer giftId) {
		return giftDao.getGiftInfo(giftId);
	}

	@Override
	public Map<Integer, GiftInfo> getGiftInfos(Integer... giftIds) {
		List<GiftInfo> giftInfos = giftDao.getGiftInfos(giftIds);
		if (StringUtil.isNullOrEmpty(giftInfos)) {
			return null;
		}
		Map<Integer, GiftInfo> map = (Map<Integer, GiftInfo>) PropertUtil.listToMap(giftInfos, "giftId");
		return map;
	}
	

}
