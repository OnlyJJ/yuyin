package com.jiujun.voice.modules.apps.user.useraccount.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.user.useraccount.dao.RichLevelHisDao;
import com.jiujun.voice.modules.apps.user.useraccount.domain.RichLevelHis;
import com.jiujun.voice.modules.apps.user.useraccount.service.RichLevelHisService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class RichLevelHisServiceImpl implements RichLevelHisService{

	@Resource
	RichLevelHisDao richLevelHisDao;
	@Override
	public Long save(String userId, int richLevel) {
		int sort = 1;
		Integer lastSort = richLevelHisDao.getLastLevel(richLevel);
		if(lastSort != null) {
			sort = lastSort + 1;
		}  
		RichLevelHis rl = new RichLevelHis();
		rl.setUserId(userId);
		rl.setRichLevel(richLevel);
		rl.setSort(sort);
		rl.setCreatTime(new Date());
		return richLevelHisDao.save(rl);
	}


	
}
