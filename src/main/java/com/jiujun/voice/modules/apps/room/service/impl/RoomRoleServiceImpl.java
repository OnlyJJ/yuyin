package com.jiujun.voice.modules.apps.room.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWipes;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.modules.apps.room.dao.RoomRoleDao;
import com.jiujun.voice.modules.apps.room.domain.RoomRole;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Role;
import com.jiujun.voice.modules.apps.room.service.RoomRoleService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

/**
 * @author Coody
 *
 */
@Service
public class RoomRoleServiceImpl implements RoomRoleService{

	@Resource
	RoomRoleDao roomRoleDao;
	@Resource
	IMessageHandle messageHandle;
	@Resource
	UserInfoService userInfoService;
	
	@Override
	public Long insert(RoomRole role) {
		return roomRoleDao.insertRole(role);
	}
	
	@Override
	public Long saveOrUpdate(RoomRole role) {
		return roomRoleDao.saveOrUpdate(role);
	}
	
	@Override
//	@CacheWrite(key=CacheConstants.ROOM_MANAGER, fields= {"roomId","userId"},
//		time = CacheTimeConstants.TIME_30M)
	public RoomRole getRoomRole(String roomId, String userId) {
		return roomRoleDao.getRoomRole(roomId, userId);
	}

	@Override
	@CacheWipes({@CacheWipe(key= CacheConstants.MEMBER_INFO, fields = {"roomId","userId"}),
		@CacheWipe(key= CacheConstants.ROOM_MANAGER, fields = {"roomId", "userId"})
	})
	public Long handleManage(String accessUserId, String userId, String roomId, int operate) {
		// 权限校验
		Long code = 0L;
		RoomRole role = roomRoleDao.getRoomRole(accessUserId, roomId);
		if(role == null || role.getIdentity() != Role.OWNER.getIdentity()) {
			throw new CmdException(ErrorCode.ERROR_503);
		}
		if(accessUserId.equals(userId)) {
			return code;
		}
		switch(operate) {
		case 1: // 上管理，则插入记录
			RoomRole newRole = new RoomRole();
			newRole.setUserId(userId);
			newRole.setRoomId(roomId);
			newRole.setIdentity(Role.MANAGER.getIdentity());
			code = roomRoleDao.saveOrUpdate(newRole);
			break;
		case 2: // 下管理，则删除记录
			code = roomRoleDao.deleteRole(userId, roomId);
			break;
		default:
			throw new CmdException(ErrorCode.ERROR_501);	
		}
		if(code >0) {
			StringBuilder msg = new StringBuilder();
			UserInfo accessUser = userInfoService.getUserInfo(accessUserId);
			UserInfo toUser = userInfoService.getUserInfo(userId);
			if(accessUser != null && toUser != null) {
				if(operate == 1) {
					msg.append(toUser.getName()).append("已成为管理员");
				}
			}
			JSONObject content = new JSONObject();
			content.put("toUserId", userId);
			content.put("behavior", 1);
			content.put("msg", msg.toString());
			messageHandle.sendRoomMsg(roomId, content.toString(), MsgEnum.ROOM_BEHAVIOR.getType());
		}
		return code;
	}

}
