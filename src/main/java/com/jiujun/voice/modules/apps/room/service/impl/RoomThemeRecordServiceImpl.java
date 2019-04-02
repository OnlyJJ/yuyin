package com.jiujun.voice.modules.apps.room.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.modules.apps.room.dao.RoomThemeRecordDao;
import com.jiujun.voice.modules.apps.room.domain.RoomRole;
import com.jiujun.voice.modules.apps.room.domain.RoomThemeRecord;
import com.jiujun.voice.modules.apps.room.service.RoomRoleService;
import com.jiujun.voice.modules.apps.room.service.RoomThemeRecordService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;
/**
 * @author Coody
 *
 */
@Service
public class RoomThemeRecordServiceImpl implements RoomThemeRecordService{

	@Resource
	RoomThemeRecordDao roomThemeRecordDao;
	@Resource
	RoomRoleService roomRoleService;
	@Resource
	IMessageHandle messageHandle;
	
	@Override
	public RoomThemeRecord getValidTheme(String roomId) {
		return roomThemeRecordDao.getValidTheme(roomId);
	}
	
	@Override
	public Long saveTheme(String userId, String roomId, String theme) {
		// 权限校验
		RoomRole role = roomRoleService.getRoomRole(roomId, userId);
		if(role == null) {
			throw new CmdException(ErrorCode.ERROR_503);
		}
		// 屏蔽关键字
		
		// 主题是否可以相同？
		RoomThemeRecord record = new RoomThemeRecord();
		record.setUserId(userId);
		record.setRoomId(roomId);
		record.setTheme(theme);
		record.setIdentity(role.getIdentity());
		Long code = roomThemeRecordDao.saveTheme(record);
		messageHandle.sendRoomMsg(roomId, null, MsgEnum.RESH_THEME_MSG.getType());
		return code;
	}
	
}
