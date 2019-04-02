package com.jiujun.voice.modules.apps.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.system.dao.AppResourceDao;
import com.jiujun.voice.modules.apps.system.domain.AppResource;
import com.jiujun.voice.modules.apps.system.service.AppResourceService;

@Service
public class AppResourceServiceImpl implements AppResourceService{

	
	@Resource
	AppResourceDao appResourceDao;
	
	@Override
	public List<AppResource> getAppResources() {
		return appResourceDao.getAppResources();
	}

	
}
