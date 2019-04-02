package com.jiujun.voice.modules.apps.home.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.home.dao.BannerInfoDao;
import com.jiujun.voice.modules.apps.home.domain.BannerInfo;
import com.jiujun.voice.modules.apps.home.service.BannerInfoService;

/**
 * 
 * @author Coody
 *
 */
@Service
public class BannerInfoServiceImpl implements BannerInfoService{

	@Resource
	BannerInfoDao bannerInfoDao;
	
	@Override
	public List<BannerInfo> listBannerInfo(int showPlace) {
		return bannerInfoDao.listBannerInfo(showPlace);
	}
	
}
