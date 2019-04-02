package com.jiujun.voice.modules.apps.recom.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.recom.dao.RecomActivityDao;
import com.jiujun.voice.modules.apps.recom.domain.RecomActivity;
import com.jiujun.voice.modules.apps.recom.service.RecomActivityService;

/**
 * 
 * @author Coody
 *
 */
@Service
public class  RecomActivityServiceImpl implements RecomActivityService{
	
	@Resource
	RecomActivityDao recomActivityDao;


	@Override
	public List<RecomActivity> getRecomers() {
		return recomActivityDao.getRecomers();
	}
}
