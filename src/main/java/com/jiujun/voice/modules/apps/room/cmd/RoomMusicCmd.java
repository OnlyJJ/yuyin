package com.jiujun.voice.modules.apps.room.cmd;

import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.room.cmd.schema.RoomMusicSchema;
import com.jiujun.voice.modules.apps.room.cmd.vo.LoadRoomMusicsReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.LoadRoomMusicsRespVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.StopStartMusicReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.SubmitMusicsReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.SwitchMusicReqVO;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomMusic;
import com.jiujun.voice.modules.apps.room.domain.RoomRole;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
/**
 * 
 * @author Coody
 *
 */
import com.jiujun.voice.modules.apps.room.service.RoomMusicService;
import com.jiujun.voice.modules.apps.room.service.RoomRoleService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

@CmdOpen("roomMusic")
@DocFlag("房间音乐中心")
public class RoomMusicCmd extends RootCmd {

	@Resource
	RoomMusicService roomMusicService;
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	RoomRoleService roomRoleService;
	@Resource
	IMessageHandle messageHandle;

	@DocFlag("同步音乐列表")
	@CmdAction
	public BaseRespVO submitMusics(SubmitMusicsReqVO req) {

		RoomInfo roomInfo = roomInfoService.getUserInRoom(req.getHeader().getUserId());
		if (roomInfo == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_2019);
		}
		RoomRole role = roomRoleService.getRoomRole(roomInfo.getRoomId(), req.getHeader().getUserId());
		if (role == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_503);
		}
		RoomMusic roomMusic = roomMusicService.getRoomMusic(roomInfo.getRoomId());
		if (roomMusic != null && !roomMusic.getUserId().equals(req.getHeader().getUserId())) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_2020);
		}
		if(StringUtil.isNullOrEmpty(req.getMusics())){
			roomMusicService.removeRoomMusic(roomInfo.getRoomId());
			return new BaseRespVO();
		}
		roomMusic = new RoomMusic();
		roomMusic.setUserId(req.getHeader().getUserId());
		roomMusic.setRoomId(roomInfo.getRoomId());
		String musicContext = JSON.toJSONString(req.getMusics());
		roomMusic.setMusicsContext(musicContext);
		Long code = roomMusicService.saveRoomMusic(roomMusic);
		if (code < 1) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_502);
		}
		return new BaseRespVO();
	}

	@DocFlag("切换歌曲")
	@CmdAction
	public BaseRespVO switchMusic(SwitchMusicReqVO req) {
		RoomInfo roomInfo = roomInfoService.getUserInRoom(req.getHeader().getUserId());
		if (roomInfo == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_2019);
		}
		RoomRole role = roomRoleService.getRoomRole(roomInfo.getRoomId(), req.getHeader().getUserId());
		if (role == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_503);
		}
		RoomMusic roomMusic = roomMusicService.getRoomMusic(roomInfo.getRoomId());
		if (roomMusic == null) {
			return new BaseRespVO();
		}
		messageHandle.sendGeneralMsg(roomMusic.getUserId(), "来自"+req.getHeader().getUserId()+"切换歌曲", req.getFileSign(), MsgEnum.SWITCH_MUSIC.getType());
		return new BaseRespVO();
	}
	
	@DocFlag("暂停/播放歌曲")
	@CmdAction
	public BaseRespVO stopStartMusic(StopStartMusicReqVO req) {
		RoomInfo roomInfo = roomInfoService.getUserInRoom(req.getHeader().getUserId());
		if (roomInfo == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_2019);
		}
		RoomRole role = roomRoleService.getRoomRole(roomInfo.getRoomId(), req.getHeader().getUserId());
		if (role == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_503);
		}
		RoomMusic roomMusic = roomMusicService.getRoomMusic(roomInfo.getRoomId());
		if (roomMusic == null) {
			return new BaseRespVO();
		}
		messageHandle.sendGeneralMsg(roomMusic.getUserId(), "来自"+req.getHeader().getUserId()+"操作歌曲状态",req.getStatus().toString(), MsgEnum.STOP_MUSIC.getType());
		return new BaseRespVO();
	}

	@DocFlag("房间音乐列表")
	@CmdAction
	public LoadRoomMusicsRespVO loadRoomMusics(LoadRoomMusicsReqVO req) {
		RoomMusic roomMusic = roomMusicService.getRoomMusic(req.getRoomId());
		if (roomMusic == null) {
			return new LoadRoomMusicsRespVO();
		}
		List<RoomMusicSchema> musics = JSON.parseObject(roomMusic.getMusicsContext(),
				new TypeReference<List<RoomMusicSchema>>() {
				});
		LoadRoomMusicsRespVO resp = new LoadRoomMusicsRespVO();
		resp.setMusics(musics);
		return resp;
	}

}
