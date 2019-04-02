package com.jiujun.voice.modules.apps.room.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.room.dao.FairyInfoDao;
import com.jiujun.voice.modules.apps.room.domain.FairyInfo;
import com.jiujun.voice.modules.apps.room.service.FairyInfoService;

@Service
public class FairyInfoServiceImpl implements FairyInfoService {

	@Resource
	FairyInfoDao fairyInfoDao;

	@Override
	public FairyInfo getCurrentFairy() {

		return fairyInfoDao.getCurrentFairy();
	}

}
