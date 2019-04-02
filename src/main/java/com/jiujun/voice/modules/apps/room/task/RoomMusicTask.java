package com.jiujun.voice.modules.apps.room.task;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.task.BaseTask;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.room.domain.RoomMicInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomMusic;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMusicService;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle.TokenEntity;

/**
 * 定时处理激活房间状态及房间在线用户
 * 
 * @author Coody
 *
 */
@Component
public class RoomMusicTask extends BaseTask {

	@Resource
	RoomMusicService roomMusicService;
	@Resource
	UserAuthHandle userAuthHandle;
	@Resource
	RoomMicInfoService roomMicInfoService;

	/**
	 * 清理房间音乐
	 * 
	 * @author Shao.x
	 * @date 2018年12月21日
	 */
	@LogFlag("清理房间音乐")
	@Scheduled(cron = "0 0/1 * * * ? ")
	public synchronized void cleanActiveRoom() {
		List<RoomMusic> musics = roomMusicService.getTotalRoomMusics();
		if (StringUtil.isNullOrEmpty(musics)) {
			return;
		}
		List<String> userIds = PropertUtil.getFieldValues(musics, "userId");
		Map<String, TokenEntity> tokenEntitys = userAuthHandle.getUserTokens(userIds.toArray(new String[] {}));
		for (RoomMusic music : musics) {
			TokenEntity token = tokenEntitys.get(music.getUserId());
			if (token == null) {
				roomMusicService.removeRoomMusic(music.getRoomId());
			}
			if (System.currentTimeMillis() - token.getAcviteTime().getTime() < 1000 * 60 * 5) {
				RoomMicInfo roomMicInfo = roomMicInfoService.getRoomMicInfoByUser(music.getUserId());
				if (roomMicInfo == null) {
					roomMusicService.removeRoomMusic(music.getRoomId());
				}
				continue;
			}
			roomMusicService.removeRoomMusic(music.getRoomId());
		}
	}

}
