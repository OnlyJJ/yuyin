package com.jiujun.voice.modules.apps.music.service;

import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.modules.apps.music.domain.MusicInfo;

public interface MusicService {

	
	public MusicInfo getMusicBySign(String sign, Long size) ;

	public Pager searchMusicPager(String keyWord, Pager pager) ;

	public Pager searchMusicPager(Pager pager) ;
	
	public void pushDownNum(Long musicId);
	
	public Long addMusic(MusicInfo music);
	
	public MusicInfo getMusicById(Long musicId);
	
	public Long delMusic(Long musicId);
	
	public Pager getUserMusicPager(String userId,Pager pager);
}
