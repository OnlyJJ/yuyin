package com.jiujun.voice.modules.apps.room.task;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.task.BaseTask;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.room.cmd.schema.MicInfoSchema;
import com.jiujun.voice.modules.apps.room.domain.RoomActive;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomRecommend;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Grade;
import com.jiujun.voice.modules.apps.room.service.RoomActiveService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomRecommendService;
import com.jiujun.voice.modules.apps.user.robot.domain.UserRobot;
import com.jiujun.voice.modules.apps.user.robot.service.UserRobotService;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle.TokenEntity;

/**
 * 定时处理激活房间状态及房间在线用户
 * 
 * @author Coody
 *
 */
@Component
public class RoomStatusTask extends BaseTask {

	@Resource
	RoomInfoService roomInfoService;
	@Resource
	RoomMicInfoService roomMicInfoService;
	@Resource
	RoomActiveService roomActiveService;
	@Resource
	UserAuthHandle userAuthHandle;
	@Resource
	RedisCache redisCache;
	@Resource
	RoomRecommendService roomRecommendService;
	@Resource
	UserRobotService userRobotService;

	/**
	 * 清理激活房间
	 * 
	 * @author Shao.x
	 * @date 2018年12月21日
	 */
	@LogFlag("清理激活房间")
	@Scheduled(cron = "0 0/5 * * * ? ")
	public synchronized void cleanActiveRoom() {
		try {
			// 所有激活的房间
			List<RoomActive> all = roomActiveService.getAllRoomActive();
			if (all != null && all.size() > 0) {
				for (RoomActive ra : all) {
					// String userId = ra.getUserId();
					String roomId = ra.getRoomId();
					String onlineKey = CacheConstants.MEMBER_SORT_DATA + roomId;
					long online = redisCache.zcard(onlineKey);
					if (online <= 0) {
						LogUtil.logger.info("## 清理激活房间(cleanActiveRoom)：roomId = " + roomId);
						// 关闭激活
						roomActiveService.closeRoom(roomId);
					}
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * （2）当用户主动杀进程等导致服务端>=60分钟未接受到心跳包，则自动将其送下麦位
	 */
	@LogFlag("清理麦上成员")
	@Scheduled(cron = "0 0/5 * * * ? ")
	public synchronized void cleanMicks() {
		/**
		 * 获取所有活跃的房间
		 */
		List<RoomActive> all = roomActiveService.getAllRoomActive();
		if (StringUtil.isNullOrEmpty(all)) {
			return;
		}
		List<RoomInfo> rooms = roomInfoService.getSystemRoomInfos();
		List<String> roomIds = PropertUtil.getFieldValues(rooms, "roomId");
		for (RoomActive room : all) {
			if (roomIds.contains(room.getRoomId())) {
				LogUtil.logger.debug("系统房间，不予处理>>" + room.getRoomId());
				continue;
			}

			/**
			 * 获取所有麦位信息
			 */
			List<MicInfoSchema> mics = roomMicInfoService.listRoomMicInfo(room.getRoomId());
			if (StringUtil.isNullOrEmpty(mics)) {
				continue;
			}
			List<String> userIds = PropertUtil.getFieldValues(mics, "userId");
			Map<String, TokenEntity> tokens = userAuthHandle.getUserTokens(userIds.toArray(new String[] {}));
			for (String userId : userIds) {
				try {
					if (!tokens.containsKey(userId)) {
						UserRobot robot = userRobotService.getUserRobot(userId);
						if (robot != null) {
							continue;
						}
						LogUtil.logger.info("清理麦上僵尸用户>>" + room.getRoomId() + ":" + userId);
						/**
						 * 下麦
						 */
						roomMicInfoService.downMic(room.getRoomId(), userId);
						continue;
					}

					TokenEntity token = tokens.get(userId);
					if (token == null) {
						continue;
					}
					if (System.currentTimeMillis() - token.getAcviteTime().getTime() > 1000 * 60 * 60) {
						/**
						 * 下麦
						 */
						LogUtil.logger.info("清理麦上僵尸用户>>" + room.getRoomId() + ":" + userId);
						roomMicInfoService.downMic(room.getRoomId(), userId);
						continue;
					}
				} catch (Exception e) {
					LogUtil.logger.error("清理麦位信息异常>>"+userId);
				}
				
			}
		}
	}

	/**
	 * 清理房间在线成员
	 * 
	 * @author Shao.x
	 * @date 2018年12月21日
	 */
	@LogFlag("清理房间在线成员")
	@Scheduled(cron = "0 0/20 * * * ? ")
	public synchronized void cleanUsers() {
		try {
			List<RoomActive> all = roomActiveService.getAllRoomActive();
			if (all != null && all.size() > 0) {
				for (RoomActive ra : all) {
					String roomId = ra.getRoomId();
					String key = CacheConstants.MEMBER_SORT_DATA + roomId;
					List<String> userIds = redisCache.zlist(key, 0, -1);
					if (userIds != null && userIds.size() > 0) {
						for (String userId : userIds) {
							boolean online = userAuthHandle.isOnLine(userId);
							if (!online) {
								String roomMemberKey = CacheConstants.MEMBER_SORT_DATA + roomId;
								redisCache.zrem(roomMemberKey, userId);
								String onlineKey = CacheConstants.USER_ONLINE_ROOM + userId;
								redisCache.delCache(onlineKey);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
		// 清理推荐放
		try {
			List<RoomRecommend> list = roomRecommendService.listRoomRecommend();
			if (list != null && list.size() > 0) {
				for (RoomRecommend rc : list) {
					String roomId = rc.getRoomId();
					RoomInfo room = roomInfoService.getRoomInfo(roomId);
					if (room == null) {
						continue;
					}
					int grade = room.getGrade();
					String roomUserId = room.getUserId();
					String key = CacheConstants.MEMBER_SORT_DATA + roomId;
					List<String> userIds = redisCache.zlist(key, 0, -1);
					if (userIds != null && userIds.size() > 0) {
						for (String userId : userIds) {
							// 推荐房，并且是系统房，房主不清理
							if (grade == Grade.SYS.getValue() && userId.equals(roomUserId)) {
								continue;
							}
							// 如果是机器人，则不处理
							UserRobot robot = userRobotService.getUserRobot(userId);
							if (robot != null) {
								continue;
							}
							boolean online = userAuthHandle.isOnLine(userId);
							if (!online) {
								String roomMemberKey = CacheConstants.MEMBER_SORT_DATA + roomId;
								redisCache.zrem(roomMemberKey, userId);
								String onlineKey = CacheConstants.USER_ONLINE_ROOM + userId;
								redisCache.delCache(onlineKey);
							}
						}
					}
				}
			}
		} catch (Exception e) {
		}
	}
}
