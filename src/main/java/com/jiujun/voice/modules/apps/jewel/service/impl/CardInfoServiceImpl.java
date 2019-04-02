package com.jiujun.voice.modules.apps.jewel.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.jewel.dao.CardInfoDao;
import com.jiujun.voice.modules.apps.jewel.domain.CardInfo;
import com.jiujun.voice.modules.apps.jewel.service.CardInfoService;

/**
 * 
 * @author Coody
 *
 */
@Service
public class CardInfoServiceImpl implements CardInfoService{

	@Resource
	CardInfoDao cardInfoDao;
	
	@Override
	public Long save(String userId, String cardNO, String owner, String bank, String subbranch, String address) {
		CardInfo info = new CardInfo();
		info.setUserId(userId);
		info.setCardNO(cardNO);
		info.setOwner(owner);
		info.setBank(bank);
		info.setSubbranch(subbranch);
		info.setAddress(address);
		return cardInfoDao.insert(info);
	}
	
	
	@Override
	public CardInfo getCardInfo(String userId) {
		return cardInfoDao.getCardInfo(userId);
	}

}
