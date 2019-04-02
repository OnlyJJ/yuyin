package com.jiujun.voice.modules.apps.music.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.music.cmd.vo.schema.MusicInfoSchema;
import com.jiujun.voice.modules.apps.music.dao.MusicInfoDao;
import com.jiujun.voice.modules.apps.music.domain.MusicInfo;
import com.jiujun.voice.modules.apps.music.service.MusicService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;

/**
 * 
 * @author Coody
 *
 */
@Service
@SuppressWarnings("unchecked")
public class MusicServiceImpl implements MusicService {

	@Resource
	MusicInfoDao musicInfoDao;
	@Resource
	UserInfoService userInfoService;
	@Override
	public MusicInfo getMusicBySign(String sign, Long size) {
		return musicInfoDao.getMusicBySign(sign, size);
	}
	@Override
	public Pager searchMusicPager(String keyWord, Pager pager) {
		pager = musicInfoDao.searchMusicPager(keyWord, pager);
		if (StringUtil.isNullOrEmpty(pager.getData())) {
			return pager;
		}
		List<MusicInfoSchema> schemas = PropertUtil.getNewList(pager.getData(), MusicInfoSchema.class);
		List<String> userIds = PropertUtil.getFieldValues(schemas, "userId");
		List<UserInfo> user = userInfoService.getUserInfos(userIds.toArray(new String[] {}));
		Map<String, UserInfo> userMap = (Map<String, UserInfo>) PropertUtil.listToMap(user, "userId");
		for (MusicInfoSchema schema : schemas) {
			if (!userMap.containsKey(schema.getUserId())) {
				continue;
			}
			UserInfo userInfo = userMap.get(schema.getUserId());
			schema.setUserName(userInfo.getName());
		}
		pager.setData(schemas);
		return pager;
	}
	@Override
	public Pager searchMusicPager(Pager pager) {
		pager = musicInfoDao.searchMusicPager(pager);
		if (StringUtil.isNullOrEmpty(pager.getData())) {
			return pager;
		}
		List<MusicInfoSchema> schemas = PropertUtil.getNewList(pager.getData(), MusicInfoSchema.class);
		List<String> userIds = PropertUtil.getFieldValues(schemas, "userId");
		List<UserInfo> user = userInfoService.getUserInfos(userIds.toArray(new String[] {}));
		Map<String, UserInfo> userMap = (Map<String, UserInfo>) PropertUtil.listToMap(user, "userId");
		for (MusicInfoSchema schema : schemas) {
			if (!userMap.containsKey(schema.getUserId())) {
				continue;
			}
			UserInfo userInfo = userMap.get(schema.getUserId());
			schema.setUserName(userInfo.getName());
		}
		pager.setData(schemas);
		return pager;
	}

	@Override
	public void pushDownNum(Long musicId) {
		musicInfoDao.pushDownNum(musicId);
	}

	@Override
	public Long addMusic(MusicInfo music) {
		return musicInfoDao.addMusic(music);
	}

	@Override
	public MusicInfo getMusicById(Long musicId) {
		return musicInfoDao.getMusicById(musicId);
	}

	@Override
	public Long delMusic(Long musicId) {
		return musicInfoDao.delMusic(musicId);
	}

	@Override
	public Pager getUserMusicPager(String userId, Pager pager) {
		return musicInfoDao.getUserMusicPager(userId, pager);
	}

}
