package com.jiujun.voice.modules.apps.user.useraccount.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.user.useraccount.dao.TradeDao;
import com.jiujun.voice.modules.apps.user.useraccount.domain.TradeRecord;
import com.jiujun.voice.modules.apps.user.useraccount.service.TradeService;

@Service
public class TradeServiceImpl implements TradeService{

	@Resource
	TradeDao tradeDao;
	
	@Override
	public Long changeGold(String userId, int gold, String remark){
		return tradeDao.changeGold(userId, gold,remark);
	}
	
	@Override
	public Long changeJewel(String userId, int jewel, String remark) {
		return tradeDao.changeJewel(userId, jewel,remark);
	}

	@Override
	public Long changeIngot(String userId, int ingot, String remark) {
		return tradeDao.changeIngot(userId, ingot,remark);
	}

	@Override
	public List<TradeRecord> getTradeRecord(String dayCode, Integer currencyType, Integer tradeType, Integer line) {
		return tradeDao.getTradeRecord(dayCode, currencyType, tradeType, line);
	}
}
