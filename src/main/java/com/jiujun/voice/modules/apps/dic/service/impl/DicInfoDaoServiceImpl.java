package com.jiujun.voice.modules.apps.dic.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.dic.dao.DicInfoDao;
import com.jiujun.voice.modules.apps.dic.domain.DicInfo;
import com.jiujun.voice.modules.apps.dic.service.DicInfoService;

/**
 * 
 * @author Coody
 *
 */
@Service
public class DicInfoDaoServiceImpl implements DicInfoService{

	@Resource
	DicInfoDao dicInfoDao;
	
	@Override
	public DicInfo getDicInfo(String name) {
		return dicInfoDao.getDicInfo(name);
	}
	
	@Override
	public Long saveDicInfo(DicInfo dicInfo) {
		return dicInfoDao.saveDicInfo(dicInfo);
	}
	
}
