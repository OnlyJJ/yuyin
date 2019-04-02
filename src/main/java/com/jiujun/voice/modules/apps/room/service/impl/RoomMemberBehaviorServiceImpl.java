package com.jiujun.voice.modules.apps.room.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.room.cmd.schema.MemberInfoSchema;
import com.jiujun.voice.modules.apps.room.dao.RoomMemberBehaviorDao;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomMemberBehavior;
import com.jiujun.voice.modules.apps.room.domain.RoomMicInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomRole;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Behavior;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Grade;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Role;
import com.jiujun.voice.modules.apps.room.service.RoomActiveService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMemberBehaviorService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomRoleService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;
import com.mysql.jdbc.StringUtils;

/**
 * 
 * @author Coody
 *
 */
@Service
public class RoomMemberBehaviorServiceImpl implements RoomMemberBehaviorService {

	@Resource
	RoomMemberBehaviorDao roomMemberBehaviorDao;
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	RoomRoleService roomRoleService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	RedisCache redisCache;
	@Resource
	IMessageHandle messageHandle;
	@Resource
	RoomActiveService roomActiveService;
	@Resource
	RoomMicInfoService roomMicInfoService;

	/** 成员列表排序基数 */
	private static final long BASE = 100000000L;
	/** 禁言时间 */
	private static final int BAN_TALK_TIME = 2 * 60;

	@Override
	public Integer checkHasPwd(String roomId) {
		int lockFlag = 0;
		RoomInfo room = roomInfoService.getRoomInfo(roomId);
		if (room != null) {
			lockFlag = room.getLockFlag();
		}
		return lockFlag;
	}

	@Override
	public void checkRoomPwd(String roomId, String userId, String password) {
		RoomInfo room = roomInfoService.getRoomInfo(roomId);
		if (room == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		if (room.getLockFlag() == Constants.STATUS_1 && !room.getUserId().equals(userId)) {
			if (StringUtils.isNullOrEmpty(password) || !password.equals(room.getPassword())) {
				throw new CmdException(ErrorCode.ERROR_2014);
			}
		}
	}

	@Override
	public MemberInfoSchema inRoom(String roomId, String userId, String password) {
		LogUtil.logger.info("### 进入房间：" + roomId + "，用户：" + userId);
		RoomInfo room = roomInfoService.getRoomInfo(roomId);
		if (room == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		String onlineKey = CacheConstants.USER_ONLINE_ROOM + userId;
		String oldRoom = redisCache.getString(onlineKey);
		// 进入房间前，先校验是否还在其他房间，在则退出其他房间状态
		if (!StringUtil.isNullOrEmpty(oldRoom) && !oldRoom.equals(roomId)) {
			outRoom(oldRoom, userId);
		}
		String key = CacheConstants.MEMBER_SORT_DATA + roomId;
		// 是否还在房间，在房间，则直接加入，不在，则进行校验
		boolean online = redisCache.zcontains(key, userId);
		if (!online) {
			// 是否满员（房主不受限制）
			boolean isFull = checkIsFull(roomId, userId);
			if (isFull) {
				throw new CmdException(ErrorCode.ERROR_2015);
			}
			// 房主不需要用密码
			if (room.getLockFlag() == Constants.STATUS_1 && !room.getUserId().equals(userId)) {
				if (StringUtils.isNullOrEmpty(password) || !password.equals(room.getPassword())) {
					throw new CmdException(ErrorCode.ERROR_2014);
				}
			}
			// 是否被踢
			boolean status = checkInRoom(roomId, userId);
			if (!status) {
				throw new CmdException(ErrorCode.ERROR_2005);
			}
		}
		// 记录用户在线房间
		redisCache.setString(onlineKey, roomId);
		MemberInfoSchema member = roomInfoService.getMemberInfo(roomId, userId);
		double sorce = 0D;
		int expLevel = member.getExpLevel();
		if (expLevel > 0) {
			sorce = expLevel * BASE;
		}
		long time = DateUtils.getSubTime();
		sorce = 0 - sorce - time;
		// 插入到房间有序集合中
		redisCache.zadd(key, sorce, userId);

		// 发公屏消息
		int role = member.getRole();
		UserSchema sender = userInfoService.getUserSchema(userId);
		sender.setRole(role);
		LogUtil.logger.info("### 进入房间，发消息：" + JSON.toJSONString(sender));
		messageHandle.sendRoomMsg(sender, roomId, null, MsgEnum.IN_ROOM.getType());

		// 处理房间激活业务
		if (role == Role.OWNER.getIdentity() && room.getGrade() != Grade.SYS.getValue()) { // 如果是房主，则激活
			roomActiveService.activeRoom(roomId);
		}
		// 刷新列表
		messageHandle.sendRoomMsg(roomId, null, MsgEnum.REFRESH_MEMBER.getType());
		return member;
	}

	@Override
	// @CacheWipe(key= CacheConstants.MEMBER_INFO, fields = {"roomId","userId"})
	public void outRoom(String roomId, String userId) {
		LogUtil.logger.info("### 退出房间：" + roomId + "，用户：" + userId);
		// 清除房间成员列表
		String key = CacheConstants.MEMBER_SORT_DATA + roomId;
		redisCache.zrem(key, userId);
		// 清除在线房间记录
		redisCache.delCache(CacheConstants.USER_ONLINE_ROOM + userId);
		// 退出麦位
		roomMicInfoService.remove(roomId, userId);

		RoomInfo info = roomInfoService.getRoomInfo(roomId);
		if (info != null && info.getGrade() == Grade.GENERAL.getValue()) {
			// 处理关闭房间
			roomActiveService.closeRoom(roomId);
		}
		// 刷新列表
		messageHandle.sendRoomMsg(roomId, null, MsgEnum.REFRESH_MEMBER.getType());
		// 刷新麦位
		messageHandle.sendRoomMsg(roomId, null, MsgEnum.REFRESH_MIC.getType());
	}

	@Override
	// @CacheWipe(key= CacheConstants.MEMBER_INFO, fields = {"roomId","userId"})
	public Long manageTalk(String roomId, String accessUserId, String userId, int operate) {
		checkAccess(roomId, accessUserId, userId);
		Long code = 0L;
		switch (operate) {
		case 1:
			code = banTalk(roomId, userId);
			break;
		case 2:
			code = unBanTalk(roomId, userId);
			break;
		default:
			throw new CmdException(ErrorCode.ERROR_501);
		}
		if (code > 0) {
			StringBuilder msg = new StringBuilder();
			UserInfo accessUser = userInfoService.getUserInfo(accessUserId);
			UserInfo toUser = userInfoService.getUserInfo(userId);
			msg.append(toUser.getName()).append("已被").append(accessUser.getName());
			if (accessUser != null && toUser != null) {
				if (operate == 1) {
					msg.append("禁言");
				} else {
					msg.append("解除禁言");
				}
			}
			JSONObject content = new JSONObject();
			content.put("toUserId", userId);
			content.put("behavior", 2);
			content.put("msg", msg.toString());
			messageHandle.sendRoomMsg(roomId, content.toString(), MsgEnum.ROOM_BEHAVIOR.getType());
		}
		return code;
	}

	@Override
	// @CacheWipe(key= CacheConstants.MEMBER_INFO, fields = {"roomId","userId"})
	public void forceOut(String roomId, String accessUserId, String userId) {
		checkAccess(roomId, accessUserId, userId);
		RoomMemberBehavior behavior = new RoomMemberBehavior();
		Date now = new Date();
		Date endTime = DateUtils.addDate(now, 2, BAN_TALK_TIME);
		behavior.setBeginTime(now);
		behavior.setEndTime(endTime);
		behavior.setRoomId(roomId);
		behavior.setUserId(userId);
		behavior.setBehavior(Behavior.OUTROOM.getValue());
		roomMemberBehaviorDao.saveOrUpdate(behavior);

		// 移除成员列表
		outRoom(roomId, userId);

		StringBuilder msg = new StringBuilder();
		UserInfo accessUser = userInfoService.getUserInfo(accessUserId);
		UserInfo toUser = userInfoService.getUserInfo(userId);
		if (accessUser != null && toUser != null) {
			msg.append(toUser.getName()).append("已被").append(accessUser.getName()).append("踢出房间");
		}
		JSONObject content = new JSONObject();
		content.put("toUserId", userId);
		content.put("behavior", 3);
		content.put("msg", msg.toString());
		messageHandle.sendRoomMsg(roomId, content.toString(), MsgEnum.ROOM_BEHAVIOR.getType());
		// 刷新麦位
		messageHandle.sendRoomMsg(roomId, null, MsgEnum.REFRESH_MIC.getType());
	}

	@Override
	public boolean checkMic(String roomId, String userId) {
		RoomMemberBehavior behavior = roomMemberBehaviorDao.getRoomMemberBehavior(roomId, userId,
				Behavior.MIC.getValue());
		if (behavior == null) {
			return true;
		}
		Integer status = behavior.getStatus();
		if (status == Constants.STATUS_1) {
			Date beginTime = behavior.getBeginTime();
			Date endTime = behavior.getEndTime();
			Date now = new Date();
			if (now.after(beginTime) && now.before(endTime)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkTalk(String roomId, String userId) {
		RoomMemberBehavior behavior = roomMemberBehaviorDao.getRoomMemberBehavior(roomId, userId,
				Behavior.TALK.getValue());
		if (behavior == null) {
			return true;
		}
		Integer status = behavior.getStatus();
		if (status == Constants.STATUS_1) {
			Date beginTime = behavior.getBeginTime();
			Date endTime = behavior.getEndTime();
			Date now = new Date();
			if (now.after(beginTime) && now.before(endTime)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkInRoom(String roomId, String userId) {
		RoomMemberBehavior behavior = roomMemberBehaviorDao.getRoomMemberBehavior(roomId, userId,
				Behavior.OUTROOM.getValue());
		if (behavior == null) {
			return true;
		}
		Integer status = behavior.getStatus();
		if (status == Constants.STATUS_1) {
			Date beginTime = behavior.getBeginTime();
			Date endTime = behavior.getEndTime();
			Date now = new Date();
			if (now.after(beginTime) && now.before(endTime)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean checkIsFull(String roomId, String userId) {
		RoomInfo room = roomInfoService.getRoomInfo(roomId);
		if (room == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		String key = CacheConstants.MEMBER_SORT_DATA + roomId;
		int total = (int) redisCache.zcard(key);
		if (total >= room.getMemberLimit() && !room.getUserId().equals(userId)) {
			return true;
		}
		return false;
	}

	/**
	 * 禁言
	 * 
	 * @author Shao.x
	 * @date 2018年12月23日
	 */
	private Long banTalk(String roomId, String userId) {
		RoomMemberBehavior behavior = new RoomMemberBehavior();
		Date now = new Date();
		Date endTime = DateUtils.addDate(now, 2, BAN_TALK_TIME);
		behavior.setBeginTime(now);
		behavior.setEndTime(endTime);
		behavior.setRoomId(roomId);
		behavior.setUserId(userId);
		behavior.setBehavior(Behavior.TALK.getValue());
		behavior.setStatus(Constants.STATUS_1);
		return roomMemberBehaviorDao.saveOrUpdate(behavior);
	}

	/**
	 * 解除禁言
	 * 
	 * @author Shao.x
	 * @date 2018年12月23日
	 * @param roomId
	 * @param userId
	 */
	private Long unBanTalk(String roomId, String userId) {
		return roomMemberBehaviorDao.delete(roomId, userId, Behavior.TALK.getValue());
	}

	private void checkAccess(String roomId, String accessUserId, String userId) {
		// 操作者
		RoomRole role = roomRoleService.getRoomRole(roomId, accessUserId);
		if (role == null) {
			throw new CmdException(ErrorCode.ERROR_503);
		}
		if (role.getIdentity() != Role.OWNER.getIdentity()) { // 管理员权限
			// 被操作者
			RoomRole toRole = roomRoleService.getRoomRole(roomId, userId);
			if (toRole != null) {
				throw new CmdException(ErrorCode.ERROR_503);
			}
		} else {
			// 房主自己不操作自己
			if (userId.equals(accessUserId)) {
				throw new CmdException(ErrorCode.ERROR_2016);
			}
		}

	}

	@Override
	public void cleanUser(String userId) {
		String onlineKey = CacheConstants.USER_ONLINE_ROOM + userId;
		String oldRoom = redisCache.getString(onlineKey);
		if (!StringUtil.isNullOrEmpty(oldRoom)) {
			outRoom(oldRoom, userId);
		} else {
			RoomMicInfo micInfo = this.roomMicInfoService.getRoomMicInfoByUser(userId);
			if (micInfo != null) {
				String roomId = micInfo.getRoomId();
				outRoom(roomId, userId);
			}
		}
	}

}
