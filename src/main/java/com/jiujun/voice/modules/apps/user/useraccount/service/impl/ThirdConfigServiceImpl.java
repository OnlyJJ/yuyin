package com.jiujun.voice.modules.apps.user.useraccount.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.user.useraccount.dao.ThirdConfigDao;
import com.jiujun.voice.modules.apps.user.useraccount.domain.ThirdConfig;
import com.jiujun.voice.modules.apps.user.useraccount.service.ThirdConfigService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class ThirdConfigServiceImpl implements ThirdConfigService{
	
	
	@Resource
	ThirdConfigDao thirdConfigDao;

	@Override
	public ThirdConfig getConfig(String packager,Integer type) {
		return thirdConfigDao.getConfig(packager,type);
	}

}
