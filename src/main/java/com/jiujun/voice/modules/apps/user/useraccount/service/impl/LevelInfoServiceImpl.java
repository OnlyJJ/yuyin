package com.jiujun.voice.modules.apps.user.useraccount.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.user.useraccount.dao.LevelInfoDao;
import com.jiujun.voice.modules.apps.user.useraccount.domain.LevelInfo;
import com.jiujun.voice.modules.apps.user.useraccount.service.LevelInfoService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class LevelInfoServiceImpl implements LevelInfoService{

	@Resource
	LevelInfoDao levelInfoDao;
	
	@Override
	public LevelInfo getLevel(int type, long exp) {
		return levelInfoDao.getLevel(type, exp);
	}

	@Override
	public long getNextRichExp(int level) {
		String nextLevelKey = String.valueOf(level+1);
		Map<String, Long> data = getAllUserLevel();
		if(data == null) {
			return -1L;
		}
		if(!data.containsKey(nextLevelKey)) {
			return -1L;
		}
		return data.get(nextLevelKey);
	}

	@Override
	public long getNextCharmExp(int level) {
		String nextLevelKey = String.valueOf(level+1);
		Map<String, Long> data = getAllCharmLevel();
		if(data == null) {
			return -1L;
		}
		if(!data.containsKey(nextLevelKey)) {
			return -1L;
		}
		return data.get(nextLevelKey);
	}

	private Map<String, Long> getAllUserLevel() {
		Map<String, Long> data = new HashMap<String, Long>();
		List<LevelInfo> list = levelInfoDao.getAllRich();
		if(list != null && list.size() >0) {
			for(LevelInfo lv : list) {
				data.put(String.valueOf(lv.getLevel()), lv.getExp());
			}
		}
		return data;
	}
	
	private Map<String, Long> getAllCharmLevel() {
		Map<String, Long> data = new HashMap<String, Long>();
		List<LevelInfo> list = levelInfoDao.getAllCharm();
		if(list != null && list.size() >0) {
			for(LevelInfo lv : list) {
				data.put(String.valueOf(lv.getLevel()), lv.getExp());
			}
		}
		return data;
	}

	
}
