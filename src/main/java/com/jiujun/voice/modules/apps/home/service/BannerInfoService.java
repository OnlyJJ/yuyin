package com.jiujun.voice.modules.apps.home.service;

import java.util.List;

import com.jiujun.voice.modules.apps.home.domain.BannerInfo;

/**
 * 
 * @author Coody
 *
 */
public interface BannerInfoService {
	/**
	 * 获取有效的banner
	 * @author Shao.x
	 * @date 2018年12月12日
	 * @return
	 */
	List<BannerInfo> listBannerInfo(int showPlace);
}
