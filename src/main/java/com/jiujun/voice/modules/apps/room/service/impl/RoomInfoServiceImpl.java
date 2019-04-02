package com.jiujun.voice.modules.apps.room.service.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWipes;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.constants.CacheTimeConstants;
import com.jiujun.voice.common.constants.FormatConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.room.cmd.schema.MemberInfoSchema;
import com.jiujun.voice.modules.apps.room.dao.RoomInfoDao;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomMicInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomRole;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Role;
import com.jiujun.voice.modules.apps.room.service.RoomActiveService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMemberBehaviorService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomRoleService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

/**
 * @author Coody
 *
 */
@Service
public class RoomInfoServiceImpl implements RoomInfoService {

	@Resource
	RoomInfoDao roomInfoDao;
	@Resource
	RoomRoleService roomRoleService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	RoomMemberBehaviorService roomMemberBehaviorService;
	@Resource
	RoomMicInfoService roomMicInfoService;
	@Resource
	RedisCache redisCache;
	@Resource
	IMessageHandle messageHandle;
	@Resource
	RoomActiveService roomActiveService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheWipes({ @CacheWipe(key = CacheConstants.PERSON_ROOM, fields = "info.userId"),
			@CacheWipe(key = CacheConstants.ROOM_INFO, fields = "info.roomId") })
	public Long createRoom(RoomInfo info) {
		// 校验是否可创建房间
		RoomInfo dbRoom = roomInfoDao.getRoomInfoByUser(info.getUserId());
		if (dbRoom != null) {
			throw new CmdException(ErrorCode.ERROR_2004);
		}
		if (StringUtils.isNotEmpty(info.getPassword())) {
			boolean pwdCheck = StringUtil.isMatcher(new String(Base64.getDecoder().decode(info.getPassword())),
					FormatConstants.USER_PWD);
			if (!pwdCheck) {
				throw ErrorCode.ERROR_1006.builderException("密码格式不正确", "密码区间6-16位");
			}
		}
		// 插入房间信息
		info.setRoomId(info.getUserId()); // 暂时使用用户id，预留
		roomInfoDao.insertRoomInfo(info);
		RoomRole role = new RoomRole();
		role.setRoomId(info.getRoomId());
		role.setUserId(info.getUserId());
		role.setIdentity(Role.OWNER.getIdentity());
		// 同步插入房间角色
		roomRoleService.insert(role);
		return 1L;
	}

	@Override
	@CacheWipe(key = CacheConstants.ROOM_INFO, fields = "info.roomId")
	public Long modifyRoomInfo(RoomInfo info) {
		// 权限校验
		RoomInfo dbInfo = roomInfoDao.getRoomInfo(info.getRoomId());
		if (dbInfo == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		if (!dbInfo.getUserId().equals(info.getUserId())) {
			throw new CmdException(ErrorCode.ERROR_503);
		}
		int lockFlag = 0;
		String password = null;
		if (info.getLockFlag() != null) {
			lockFlag = info.getLockFlag();
			if (lockFlag == 1) { // 设置有密码访问
				password = info.getPassword();
				if (StringUtils.isEmpty(password)) {
					throw new CmdException(ErrorCode.ERROR_2018);
				}
				if (StringUtils.isNotEmpty(password)) {
					boolean pwdCheck = StringUtil.isMatcher(new String(Base64.getDecoder().decode(password)),
							FormatConstants.USER_PWD);
					if (!pwdCheck) {
						throw ErrorCode.ERROR_1006.builderException("密码格式不正确", "密码区间6-16位");
					}
				}
			}
		}
		dbInfo.setLockFlag(lockFlag);
		dbInfo.setPassword(password);
		if (info.getMemberLimit() != null) {
			int members = info.getMemberLimit();
			String key = CacheConstants.MEMBER_SORT_DATA + info.getRoomId();
			int total = (int) redisCache.zcard(key);
			if (members < total) {
				throw new CmdException(ErrorCode.ERROR_2017);
			}
			dbInfo.setMemberLimit(info.getMemberLimit());
		}
		if (info.getFeature() != null) {
			dbInfo.setFeature(info.getFeature());
		}
		if (StringUtils.isNotEmpty(info.getEnjoyType())) {
			dbInfo.setEnjoyType(info.getEnjoyType());
		}
		dbInfo.setUpdateTime(new Date());
		dbInfo.setName(info.getName());
		dbInfo.setEnjoyType(info.getEnjoyType());
		Long code = roomInfoDao.modifyRoomInfo(dbInfo);
		// 修改房间偏好
		roomActiveService.pushEnjoyType(dbInfo.getRoomId(), dbInfo.getEnjoyType());
		messageHandle.sendRoomMsg(dbInfo.getRoomId(), null, MsgEnum.RESH_ROONINFO.getType());
		return code;
	}

	@Override
	@CacheWrite(key = CacheConstants.ROOM_INFO, fields = "roomId", time = CacheTimeConstants.TIME_30M)
	public RoomInfo getRoomInfo(String roomId) {
		return roomInfoDao.getRoomInfo(roomId);
	}

	@Override
	// @CacheWrite(key= CacheConstants.MEMBER_INFO, fields =
	// {"roomId","userId"}, time= CacheTimeConstants.TIME_10M)
	public MemberInfoSchema getMemberInfo(String roomId, String userId) {
		UserSchema user = userInfoService.getUserSchema(userId);
		if (user == null) {
			throw new CmdException(ErrorCode.ERROR_1009);
		}
		RoomInfo room = getRoomInfo(roomId);
		if (room == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		MemberInfoSchema member = new MemberInfoSchema();
		member.setSex(user.getSex());
		member.setUserId(userId);
		UserAccount account = userAccountService.getUserAccountByUserId(userId);
		if (account != null) {
			member.setCharmLevel(account.getCharmLevel());
			member.setExpLevel(account.getExpLevel());
		}
		member.setName(user.getName());
		member.setIcon(user.getCompleHead());
		// 房间角色
		int role = 0;
		RoomRole roomRole = roomRoleService.getRoomRole(roomId, userId);
		if (roomRole != null) {
			role = roomRole.getIdentity();
		}
		member.setRole(role);
		// 是否禁言
		int talk = 0;
		boolean isTalk = roomMemberBehaviorService.checkTalk(roomId, userId);
		if (!isTalk) {
			talk = 1;
		}
		member.setTalkFlg(talk);
		// 是否禁止上麦
		int mic = 0;
		boolean isMic = roomMemberBehaviorService.checkMic(roomId, userId);
		if (!isMic) {
			mic = 1;
		}
		member.setRoomId(roomId);
		member.setMicFlag(mic);
		// 用户的个人房间
		RoomInfo info = getRoomInfoByUserId(userId);
		if (null != info) {
			member.setMyRoomId(info.getRoomId());
		}
		return member;
	}

	@Override
	public Pager listMembers(String roomId, int pageSize, int pageNo) {
		RoomInfo room = roomInfoDao.getRoomInfo(roomId);
		if (room == null) {
			throw new CmdException(ErrorCode.ERROR_2003);
		}
		Pager page = new Pager();
		List<MemberInfoSchema> data = new ArrayList<MemberInfoSchema>();
		// 成员列表从sort set取，排序在进入房间的时候处理
		String key = CacheConstants.MEMBER_SORT_DATA + roomId;
		int total = (int) redisCache.zcard(key);
		int start = pageNo > 1 ? pageSize * (pageNo - 1) : 0;
		int end = pageNo > 1 ? pageSize * pageNo - 1 : pageSize - 1;
		List<String> sorts = redisCache.zlist(key, start, end);
		if (sorts != null && sorts.size() > 0) {
			for (String userId : sorts) {
				MemberInfoSchema member = getMemberInfo(roomId, userId);
				if (member != null) {
					data.add(member);
				}
			}
			data = PropertUtil.doSeq(data, "role");
			data = PropertUtil.doSeqDesc(data, "expLevel");
			page.setData(data);
		}
		page.setCount(total);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return page;
	}

	@Override
	@CacheWrite(key = CacheConstants.PERSON_ROOM, fields = "userId", time = CacheTimeConstants.TIME_2H)
	public RoomInfo getRoomInfoByUserId(String userId) {
		return roomInfoDao.getRoomInfoByUserId(userId);
	}

	@Override
	public RoomInfo getUserInRoom(String userId) {
		RoomInfo room = null;
		String roomId = redisCache.getString(CacheConstants.USER_ONLINE_ROOM + userId);
		if (StringUtil.isNullOrEmpty(roomId)) {
			// 查询是否还有在麦位上的房间
			RoomMicInfo mic = roomMicInfoService.getRoomMicInfoByUser(userId);
			if (mic != null) {
				roomId = mic.getRoomId();
			}
		}
		if (StringUtils.isNotEmpty(roomId)) {
			room = getRoomInfo(roomId);
		}
		return room;
	}

	@Override
	public List<RoomInfo> getSystemRoomInfos() {
		return roomInfoDao.getSystemRoomInfos();
	}

}
