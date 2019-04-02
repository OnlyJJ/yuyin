package com.jiujun.voice.modules.apps.jewel.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.jewel.dao.DrawJewelDao;
import com.jiujun.voice.modules.apps.jewel.domain.DrawJewel;
import com.jiujun.voice.modules.apps.jewel.service.DrawJewelService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class DrawJewelServiceImpl implements DrawJewelService{

	@Resource
	DrawJewelDao drawJewelDao;
	
	@Override
	public DrawJewel getUserDrawJewel(String userId) {
		return drawJewelDao.getDrawJewel(userId);
	}

	@Override
	public Long addTotalJewel(String userId, int jewel) {
		DrawJewel drawJewel = new DrawJewel();
		drawJewel.setUserId(userId);
		drawJewel.setTotalJewel(jewel);
		drawJewel.setUpdateTime(new Date());
		return drawJewelDao.saveOrUpdate(drawJewel);
	}
	
}
