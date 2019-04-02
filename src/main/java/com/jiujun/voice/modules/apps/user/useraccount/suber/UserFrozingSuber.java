package com.jiujun.voice.modules.apps.user.useraccount.suber;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMusicService;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.useraccount.suber.entity.UserFrozingEntity;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;
import com.jiujun.voice.modules.redis.suber.AbstractRedisSuber;

/**
 * 冻结用户指令
 * 
 * @author Coody
 *
 */
@Component
public class UserFrozingSuber extends AbstractRedisSuber<UserFrozingEntity> {

	@Resource
	UserAccountService userAccountService;
	@Resource
	IMessageHandle imessageHandle;
	@Resource
	UserAuthHandle userAuthHandle;
	@Resource
	RoomMusicService roomMusicService;
	@Resource
	RoomInfoService roomInfoService;

	@Override
	public void execute(UserFrozingEntity entity) {

		LogUtil.logger.debug("冻结解冻用户指令>>" + entity);
		UserAccount account = userAccountService.getUserAccountByUserId(entity.getUserId());
		account.setStatus(entity.getStatus());
		// 修改用户状态
		userAccountService.updateUserAccount(account, "status");
		// 封号
		if (entity.getStatus() == -1) {
			// 发送封号消息
			imessageHandle.sendGeneralMsg(entity.getUserId(), "账号冻结通知", "您的账号已被封停", MsgEnum.USER_FROZING.getType(),
					false);
			// 停止直播间音乐
			RoomInfo room = roomInfoService.getUserInRoom(entity.getUserId());
			roomMusicService.removeRoomMusic(room.getRoomId(), entity.getUserId());
			// 擦除token
			userAuthHandle.removeToken(entity.getUserId());
			return;
		}

	}

}
