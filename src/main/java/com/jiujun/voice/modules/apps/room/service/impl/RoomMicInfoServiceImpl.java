package com.jiujun.voice.modules.apps.room.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.rlock.constant.LockConstant;
import com.jiujun.voice.common.rlock.handle.LockHandle;
import com.jiujun.voice.modules.apps.room.cmd.schema.MicInfoSchema;
import com.jiujun.voice.modules.apps.room.dao.RoomMicInfoDao;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomMicInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomMicManage;
import com.jiujun.voice.modules.apps.room.domain.RoomRole;
import com.jiujun.voice.modules.apps.room.enums.MicEnum.Status;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.MicType;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Role;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMemberBehaviorService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMicManageService;
import com.jiujun.voice.modules.apps.room.service.RoomMusicService;
import com.jiujun.voice.modules.apps.room.service.RoomRoleService;
import com.jiujun.voice.modules.apps.user.robot.service.UserRobotService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;
import com.mysql.jdbc.StringUtils;

/**
 * @author Coody
 *
 */
@Service
public class RoomMicInfoServiceImpl implements RoomMicInfoService {
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	RoomMicInfoDao roomMicInfoDao;
	@Resource
	RoomMemberBehaviorService roomMemberBehaviorService;
	@Resource
	RoomRoleService roomRoleService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	IMessageHandle messageHandle;
	@Resource
	RoomMicManageService roomMicManageService;
	@Resource
	LockHandle lockHandle;
	@Resource
	RedisCache redisCache;
	@Resource
	UserRobotService userRobotService;
	@Resource
	RoomMusicService roomMusicService;

	@Override
	// @CacheWrite(key=CacheConstants.ROOM_MIC_LIST, fields="roomId",
	// time=CacheTimeConstants.TIME_10M)
	public List<MicInfoSchema> listRoomMicInfo(String roomId) {
		List<MicInfoSchema> ret = new ArrayList<MicInfoSchema>();
		List<RoomMicInfo> micList = roomMicInfoDao.listMicInfo(roomId);
		List<Integer> seats = new ArrayList<Integer>();
		if (micList != null && micList.size() > 0) {
			for (RoomMicInfo mic : micList) {
				MicInfoSchema dto = new MicInfoSchema();
				BeanUtils.copyProperties(mic, dto);
				String userId = mic.getUserId();
				RoomRole role = roomRoleService.getRoomRole(roomId, userId);
				if (role != null) {
					dto.setRole(role.getIdentity());
				}
				UserSchema user = userInfoService.getUserSchema(userId);
				if (user != null) {
					dto.setIcon(user.getCompleHead());
					dto.setName(user.getName());
				}
				int micStatus = 0;
				Integer status = roomMicManageService.getMicStatus(roomId, mic.getSeat());
				if (status != null) {
					micStatus = status;
					seats.add(mic.getSeat());
				}
				dto.setStatus(micStatus);
				ret.add(dto);
			}
		}
		List<RoomMicManage> list = roomMicManageService.getAll(roomId);
		if (list != null && list.size() > 0) {
			for (RoomMicManage rmg : list) {
				int seat = rmg.getSeat();
				if (seats.contains(seat)) {
					continue;
				}
				MicInfoSchema dto = new MicInfoSchema();
				BeanUtils.copyProperties(rmg, dto);
				ret.add(dto);
			}
		}
		return ret;
	}

	@Override
	// @CacheWrite(key=CacheConstants.ROOM_MIC_SEAT, fields= {"roomId", "seat"},
	// time=CacheTimeConstants.TIME_10M)
	public RoomMicInfo getRoomMicInfoBySeat(String roomId, int seat) {
		return roomMicInfoDao.getRoomMicInfoBySeat(roomId, seat);
	}

	@Override
	// @CacheWrite(key=CacheConstants.USER_MIC_INFO, fields= "userId",
	// time=CacheTimeConstants.TIME_10M)
	public RoomMicInfo getRoomMicInfoByUser(String userId) {
		return roomMicInfoDao.getRoomMicInfoByUser(userId);
	}
	@Override
	@CacheWipe(key = CacheConstants.ROOM_MIC_SEAT, fields = { "roomId", "seat" })
	@CacheWipe(key = CacheConstants.USER_MIC_INFO, fields = { "roomId", "userId" })
	public Long upAndDownMic(String roomId, String userId, int seat, int seatType, int opearte) {
		Long code = 0L;
		switch (opearte) {
		case 1: // 上麦
			code = upMic(roomId, userId, seat, seatType);
			break;
		case 2: // 下麦
			code = downMic(roomId, userId);
			break;
		default:
			throw new CmdException(ErrorCode.ERROR_501);
		}
		if (code > 0) {
			// 发消息刷新麦位
			messageHandle.sendRoomMsg(roomId, null, MsgEnum.REFRESH_MIC.getType());
		}
		return code;
	}

	@Override
	@Transacted
	@CacheWipe(key = CacheConstants.ROOM_MIC_SEAT, fields = { "roomId", "seat" })
	@CacheWipe(key = CacheConstants.USER_MIC_INFO, fields = { "roomId", "userId" })
	// @CacheWipe(key=CacheConstants.ROOM_MIC_LIST, fields= "roomId")

	public Long changeMic(String roomId, String userId, int seat, int seatType) {
		LogUtil.logger.info("## 切换麦位：roomId = " + roomId + ",userId = " + userId + ",seat = " + seat);
		RoomInfo room = roomInfoService.getRoomInfo(roomId);
		if (room == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		RoomMicInfo mic = getRoomMicInfoByUser(userId);
		if (mic != null && mic.getSeat() == seat) { // 位置不变
			return 1L;
		}
		checkAccess(roomId, userId, seatType);
		checkMicStatus(roomId, seat);
		// 删除老位置
		roomMicInfoDao.delete(mic.getId());
		RoomMicInfo info = new RoomMicInfo();
		info.setRoomId(roomId);
		info.setUserId(userId);
		info.setSeat(seat);
		info.setSeatType(seatType);
		Long code = roomMicInfoDao.insert(info);
		if (code < 0) {
			throw new CmdException(ErrorCode.ERROR_2007);
		}
		// 发消息刷新麦位
		messageHandle.sendRoomMsg(roomId, null, MsgEnum.REFRESH_MIC.getType());
		return 1L;
	}

	@Override
	@CacheWipe(key = CacheConstants.ROOM_MIC_SEAT, fields = { "roomId", "seat" })
	@CacheWipe(key = CacheConstants.USER_MIC_INFO, fields = { "roomId", "userId" })
	// @CacheWipe(key=CacheConstants.ROOM_MIC_LIST, fields= "roomId")
	public Long manageMic(String roomId, String userId, String targetUserId, int seat, int seatType, int opearte) {
		// checkManageAccess(roomId, userId);
		checkAccess(roomId, userId, targetUserId);
		Long code = 0L;
		switch (opearte) {
		case 1: // 上麦
			// 被抱上麦位用户必须在房间
			if (checkInRoom(roomId, targetUserId)) {
				code = upMic(roomId, targetUserId, seat, seatType);
			}
			break;
		case 2: // 下麦
			// 权限校验
			code = downMic(roomId, targetUserId);
			break;
		default:
			throw new CmdException(ErrorCode.ERROR_501);
		}
		if (code > 0) {
			// 发消息刷新麦位
			messageHandle.sendRoomMsg(roomId, null, MsgEnum.REFRESH_MIC.getType());

			StringBuilder msg = new StringBuilder();
			UserSchema user = userInfoService.getUserSchema(userId);
			UserSchema toUser = userInfoService.getUserSchema(targetUserId);
			msg.append(toUser.getName()).append("被").append(user.getName());
			if (opearte == 1) {
				msg.append("抱上麦");
			} else {
				msg.append("抱下麦");
			}
			JSONObject content = new JSONObject();
			content.put("type", opearte);
			content.put("userId", targetUserId);
			content.put("msg", msg.toString());
			messageHandle.sendRoomMsg(roomId, content.toString(), MsgEnum.PICK_MIC.getType());
		}
		return code;
	}

	@Override
	// @CacheWipes({
	// @CacheWipe(key=CacheConstants.ROOM_MIC_LIST, fields= "roomId")
	// })
	public void clean(String roomId) {
		if (!StringUtils.isNullOrEmpty(roomId)) {
			roomMicInfoDao.deleteAll(roomId);
		}
	}

	@Override
	@CacheWipe(key = CacheConstants.USER_MIC_INFO, fields = { "roomId", "userId" })
	// @CacheWipe(key=CacheConstants.ROOM_MIC_LIST, fields= "roomId")

	public Long remove(String roomId, String userId) {
		if (StringUtils.isNullOrEmpty(roomId) || StringUtils.isNullOrEmpty(userId)) {
			return 0L;
		}
		RoomMicInfo mic = getRoomMicInfoByUser(userId);
		if (mic != null && roomId.equals(mic.getRoomId())) {
			return roomMicInfoDao.delete(roomId, userId);
		}
		return 0L;
	}

	@Override
	public Long operateMic(String roomId, String userId, int seat, int seatType, int opearte) {
		LogUtil.logger.info("## 麦位操作：roomId= " + roomId + ",操作者userId= " + userId + ",位置seat= " + seat
				+ ",操作类型（锁麦(1)/解锁麦位(2)/禁麦(3)/解禁麦位(4)）opearte= " + opearte + ",begin...");
		Long code = 0L;
		RoomInfo room = roomInfoService.getRoomInfo(roomId);
		if (room == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		checkManageAccess(roomId, userId, seat, opearte);
		switch (opearte) {
		case 1: // 锁麦
			roomMicManageService.operateMic(roomId, seat, Status.LOCK.getValue(), seatType);
			// 移除当前麦位用户
			RoomMicInfo mic = getRoomMicInfoBySeat(roomId, seat);
			if (mic != null) {
				downMic(roomId, mic.getUserId());
			}
			break;
		case 2: // 解锁麦位
			roomMicManageService.delete(roomId, seat);
			break;
		case 3: // 禁麦
			roomMicManageService.operateMic(roomId, seat, Status.BAN.getValue(), seatType);
			break;
		case 4: // 解禁麦位
			roomMicManageService.delete(roomId, seat);
			break;
		default:
			throw new CmdException(ErrorCode.ERROR_501);
		}
		// 更新麦位
		messageHandle.sendRoomMsg(roomId, null, MsgEnum.REFRESH_MIC.getType());
		LogUtil.logger.info("## 麦位操作：roomId= " + roomId + ",操作者userId= " + userId + ",位置seat= " + seat
				+ ",操作类型（锁麦(1)/解锁麦位(2)/禁麦(3)/解禁麦位(4)）opearte= " + opearte + ",end!");
		return code;
	}

	/**
	 * 上麦
	 * 
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @throws CmdException
	 */
	private Long upMic(String roomId, String userId, int seat, int seatType) throws CmdException {
		LogUtil.logger.info("## 上麦：roomId=" + roomId + ",userId=" + userId + ",seat=" + seat + ",seatType=" + seatType
				+ ",begin...");
		Long code = 0L;
		String lockname = LockConstant.LOCK_ROOM_UPMIC + roomId;
		try {
			lockHandle.lock(lockname);
			RoomInfo room = roomInfoService.getRoomInfo(roomId);
			if (room == null) {
				throw new CmdException(ErrorCode.ERROR_2003);
			}
			boolean userMicStatus = roomMemberBehaviorService.checkMic(roomId, userId);
			if (!userMicStatus) {
				throw new CmdException(ErrorCode.ERROR_2008);
			}
			checUserkMic(userId);
			checkMicStatus(roomId, seat);
			checkAccess(roomId, userId, seatType);
			RoomMicInfo info = new RoomMicInfo();
			info.setRoomId(roomId);
			info.setUserId(userId);
			info.setSeat(seat);
			info.setSeatType(seatType);
			code = roomMicInfoDao.insert(info);
			if (code < 0) {
				throw new CmdException(ErrorCode.ERROR_2007);
			}
		} catch (InterruptedException e) {
			LogUtil.logger.error(e.getMessage());
		} finally {
			lockHandle.unlock(lockname);
		}
		LogUtil.logger.info(
				"## 上麦：roomId=" + roomId + ",userId=" + userId + ",seat=" + seat + ",seatType=" + seatType + ",end!");
		return code;
	}

	/**
	 * 
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @throws CmdException
	 */
	@Override
	public Long downMic(String roomId, String userId) throws CmdException {
		LogUtil.logger.info("## 下麦处理，roomId = " + roomId + ",userId = " + userId);
		RoomMicInfo info = getRoomMicInfoByUser(userId);
		if (info != null) {
			roomMusicService.removeRoomMusic(roomId,userId);
			return roomMicInfoDao.delete(info.getId());
		}
		return 0L;
	}

	/**
	 * 校验用户是否可上麦，（只允许同时在一个房间上麦，不可多个）
	 * 
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @throws CmdException
	 */
	private void checUserkMic(String userId) throws CmdException {
		RoomMicInfo mic = getRoomMicInfoByUser(userId);
		if (mic != null) {
			throw new CmdException(ErrorCode.ERROR_2010);
		}
	}

	/**
	 * 校验麦位是否正常使用（已被占用，已被禁用）
	 * 
	 * @author Shao.x
	 * @date 2018年12月10日
	 * @return
	 */
	private void checkMicStatus(String roomId, int seat) throws CmdException {
		Integer status = roomMicManageService.getMicStatus(roomId, seat);
		if (status != null && status == Status.LOCK.getValue()) {
			throw new CmdException(ErrorCode.ERROR_2006);
		}
		RoomMicInfo mic = getRoomMicInfoBySeat(roomId, seat);
		if (mic != null) {
			throw new CmdException(ErrorCode.ERROR_2007);
		}
	}

	/**
	 * 校验麦位权限
	 * 
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @param seatType
	 *            0-普通，1-主麦
	 */
	private void checkAccess(String roomId, String userId, int seatType) throws CmdException {
		if (seatType == MicType.MASTER.getValue()) {
			RoomRole role = roomRoleService.getRoomRole(roomId, userId);
			if (role == null) {
				throw new CmdException(ErrorCode.ERROR_2009);
			}
		}
	}

	/**
	 * 校验操作者权限
	 * 
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @throws CmdException
	 */
	private void checkManageAccess(String roomId, String userId, int seat, int opearte) throws CmdException {
		RoomRole role = roomRoleService.getRoomRole(roomId, userId);
		if (role == null) {
			throw new CmdException(ErrorCode.ERROR_503);
		}
		if (role.getIdentity() != Role.OWNER.getIdentity()) { // 管理员的权限
			// 锁/解麦 无权限
			if ((opearte == 1 || opearte == 2)) {
				throw new CmdException(ErrorCode.ERROR_503);
			}

			// 禁/解麦 需要判断麦位是否有人
			RoomMicInfo mic = getRoomMicInfoBySeat(roomId, seat);
			if (mic != null) {
				String seatUser = mic.getUserId();
				RoomRole roleToUser = roomRoleService.getRoomRole(roomId, seatUser);
				// 管理员不能操作管理员
				if (roleToUser != null) {
					throw new CmdException(ErrorCode.ERROR_503);
				}
			}
		}
	}

	/**
	 * 权限校验
	 * 
	 * @author Shao.x
	 * @date 2019年1月16日
	 * @param roomId
	 * @param accessUserId
	 *            操作者
	 * @param toUserId
	 *            被操作者
	 */
	private void checkAccess(String roomId, String accessUserId, String toUserId) {
		// 操作者
		RoomRole role = roomRoleService.getRoomRole(roomId, accessUserId);
		if (role == null) {
			throw new CmdException(ErrorCode.ERROR_503);
		}
		RoomInfo room = roomInfoService.getRoomInfo(roomId);
		if (room == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		if (toUserId.equals(room.getUserId()) && !accessUserId.equals(toUserId)) { // 当前被操作的对象是房主
			throw new CmdException(ErrorCode.ERROR_503);
		}
		// 被操作者
		RoomRole toRole = roomRoleService.getRoomRole(roomId, toUserId);
		if (toRole != null) { // 被操作者是管理员，则只有房主能处理
			if (role.getIdentity() != Role.OWNER.getIdentity()) {
				throw new CmdException(ErrorCode.ERROR_503);
			}
		}

	}

	/**
	 * 校验是否在房间
	 * 
	 * @author Shao.x
	 * @date 2019年1月17日
	 * @param roomId
	 * @param userId
	 * @return
	 */
	private boolean checkInRoom(String roomId, String userId) {
		String key = CacheConstants.MEMBER_SORT_DATA + roomId;
		// 是否还在房间，在房间，则直接加入，不在，则进行校验
		return redisCache.zcontains(key, userId);
	}

	@Override
	public List<RoomMicInfo> listMicInfo(String roomId) {
		return roomMicInfoDao.listMicInfo(roomId);
	}

}
