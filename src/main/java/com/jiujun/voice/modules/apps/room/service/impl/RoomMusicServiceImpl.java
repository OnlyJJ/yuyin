package com.jiujun.voice.modules.apps.room.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.room.dao.RoomMusicDao;
import com.jiujun.voice.modules.apps.room.domain.RoomMusic;
import com.jiujun.voice.modules.apps.room.service.RoomMusicService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

@Service
public class RoomMusicServiceImpl implements RoomMusicService {

	@Resource
	RoomMusicDao roomMusicDao;
	@Resource
	IMessageHandle messageHandle;

	@Override
	public RoomMusic getRoomMusic(String roomId) {
		return roomMusicDao.getRoomMusic(roomId);
	}

	@Override
	public Long saveRoomMusic(RoomMusic music) {
		Long code = roomMusicDao.saveRoomMusic(music);
		if (code > 0) {
			messageHandle.sendRoomMsg(music.getRoomId(), "音乐列表变更", MsgEnum.MUSIC_CHANGE.getType());
		}
		return code;
	}

	@Override
	public Long removeRoomMusic(String roomId) {
		Long code = roomMusicDao.removeRoomMusic(roomId);
		if (code > 0) {
			messageHandle.sendRoomMsg(roomId, "音乐列表变更", MsgEnum.MUSIC_CHANGE.getType());
		}
		return code;
	}

	@Override
	public Long removeRoomMusic(String roomId, String userId) {
		Long code = roomMusicDao.removeRoomMusic(roomId, userId);
		if (code > 0) {
			messageHandle.sendRoomMsg(roomId, "音乐列表变更", MsgEnum.MUSIC_CHANGE.getType());
		}
		return code;
	}

	@Override
	public List<RoomMusic> getTotalRoomMusics() {
		return roomMusicDao.getTotalRoomMusics();
	}
}
