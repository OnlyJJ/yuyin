package com.jiujun.voice.modules.apps.music.cmd;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.cmd.vo.PagerReqVO;
import com.jiujun.voice.common.cmd.vo.PagerRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.modules.apps.music.cmd.vo.AddMusicReqVO;
import com.jiujun.voice.modules.apps.music.cmd.vo.DelMusicReqVO;
import com.jiujun.voice.modules.apps.music.cmd.vo.DownMusicReqVO;
import com.jiujun.voice.modules.apps.music.cmd.vo.GetCdnUrlReqVO;
import com.jiujun.voice.modules.apps.music.cmd.vo.GetCdnUrlRespVO;
import com.jiujun.voice.modules.apps.music.cmd.vo.GetHotMusicReqVO;
import com.jiujun.voice.modules.apps.music.cmd.vo.schema.MusicInfoSchema;
import com.jiujun.voice.modules.apps.music.domain.MusicInfo;
import com.jiujun.voice.modules.apps.music.service.MusicService;

@DocFlag("音乐中心")
@CmdOpen("music")
public class MusicCmd extends RootCmd {

	@Resource
	MusicService musicService;

	@CmdAction
	@DocFlag("热门/搜索音乐列表")
	public PagerRespVO<MusicInfoSchema> getHotMusic(GetHotMusicReqVO req) {
		Pager pager = musicService.searchMusicPager(req.getKeyWord(), req.toPager());
		PagerRespVO<MusicInfoSchema> resp = new PagerRespVO<MusicInfoSchema>().fromPager(pager);
		return resp;
	}

	@CmdAction
	@DocFlag("下载登记接口")
	public BaseRespVO downMusic(DownMusicReqVO req) {
		musicService.pushDownNum(req.getMusicId());
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("查询文件接口")
	public GetCdnUrlRespVO getCdnUrl(GetCdnUrlReqVO req) {

		MusicInfo music = musicService.getMusicBySign(req.getFileSign(), req.getSize());
		if (music == null) {
			return new GetCdnUrlRespVO();
		}	
		GetCdnUrlRespVO resp = new GetCdnUrlRespVO();
		resp.setUrl(music.getUrl());
		return resp;
	}

	@CmdAction
	@DocFlag("音乐录入接口")
	public BaseRespVO addMusic(AddMusicReqVO req) {

		MusicInfo music = new MusicInfo();
		BeanUtils.copyProperties(req, music);
		music.setUserId(req.getHeader().getUserId());
		Long code = musicService.addMusic(music);
		if (code < 1) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1004);
		}
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("删除音乐接口")
	public BaseRespVO delMusic(DelMusicReqVO req) {

		MusicInfo music = musicService.getMusicById(req.getMusicId());
		if (music == null) {
			return new BaseRespVO();
		}
		if (!req.getHeader().getUserId().equals(music.getUserId())) {
			return new BaseRespVO();
		}
		Long code = musicService.delMusic(req.getMusicId());
		if (code < 1) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1004);
		}
		return new BaseRespVO();

	}

	@CmdAction
	@DocFlag("我的音乐列表")
	public PagerRespVO<MusicInfo> myMusicPager(PagerReqVO req) {
		Pager pager = musicService.getUserMusicPager(req.getHeader().getUserId(), req.toPager());
		PagerRespVO<MusicInfo> resp = new PagerRespVO<MusicInfo>().fromPager(pager);
		return resp;
	}
}
