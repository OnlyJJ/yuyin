package com.jiujun.voice.modules.apps.user.useraccount.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.user.useraccount.dao.CharmLevelHisDao;
import com.jiujun.voice.modules.apps.user.useraccount.domain.CharmLevelHis;
import com.jiujun.voice.modules.apps.user.useraccount.service.CharmLevelHisService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class CharmLevelHisServiceImpl implements CharmLevelHisService{

	@Resource
	CharmLevelHisDao charmLevelHisDao;
	@Override
	public Long save(String userId, int charmLevel) {
		int sort = 1;
		Integer lastSort = charmLevelHisDao.getLastLevel(charmLevel);
		if(lastSort != null) {
			sort = lastSort + 1;
		}  
		CharmLevelHis cl = new CharmLevelHis();
		cl.setUserId(userId);
		cl.setCharmLevel(charmLevel);
		cl.setSort(sort);
		cl.setCreatTime(new Date());
		return charmLevelHisDao.save(cl);
	}


	
}
