package com.jiujun.voice.modules.apps.jewel.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.jewel.dao.JewelCollectDao;
import com.jiujun.voice.modules.apps.jewel.domain.JewelCollect;
import com.jiujun.voice.modules.apps.jewel.service.JewelCollectService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class JewelCollectServiceImpl implements JewelCollectService{

	@Resource
	JewelCollectDao jewelCollectDao;
	
	@Override
	public Long saveOrUpdate(JewelCollect jc, String addFiel) {
		return jewelCollectDao.saveOrUpdate(jc, addFiel);
	}

	@Override
	public JewelCollect getJewelCollect(String userId, String time) {
		return jewelCollectDao.getJewelCollect(userId, time);
	}
	
}
