package com.jiujun.voice.modules.apps.home.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.home.cmd.schema.RoomInfoSchema;
import com.jiujun.voice.modules.apps.home.cmd.schema.SearchResultSchema;
import com.jiujun.voice.modules.apps.home.service.HomePageService;
import com.jiujun.voice.modules.apps.room.cmd.schema.MicInfoSchema;
import com.jiujun.voice.modules.apps.room.domain.RoomActive;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomRecommend;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.EnjoyType;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Feature;
import com.jiujun.voice.modules.apps.room.enums.RoomEnum.Grade;
import com.jiujun.voice.modules.apps.room.service.RoomActiveService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomRecommendService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;

/**
 * 
 * @author Coody
 *
 */
@Service
public class HomePageServiceImpl implements HomePageService {
	
	@Resource
	RoomActiveService roomActiveService;
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	RoomMicInfoService roomMicInfoService;
	@Resource
	RedisCache redisCache;
	@Resource
	RoomRecommendService roomRecommendService;

	@Override
	public Pager listQuickRoom(int pageSize, int pageNo) {
		// 获取可展示的房间---缓存（只缓存房间id）
		Pager page = new Pager();
		List<String> roomIds = roomActiveService.getAllActiveRoomId();
		if(StringUtil.isAllNull(roomIds)) {
			return page;
		}
		List<RoomInfoSchema> ret = new ArrayList<RoomInfoSchema>();
		int index = pageNo > 1 ? (pageNo - 1) * pageSize : 0;
		int size = roomIds.size();
		page.setCount(size);
		for(int i=0; i<pageSize; i++) {
			if(size <= index) {
				break;
			}
			String roomId = roomIds.get(index);
			RoomInfoSchema dto = handleData(roomId);
			if(null != dto) {
				RoomActive room = roomActiveService.getRoomActive(roomId);
				if(null != room && null != room.getCreateTime()) {
					dto.setActiveTime(room.getCreateTime().getTime());
				}
				ret.add(dto);
			}
			index++;
		}
		page.setData(ret);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return page;
	}
	
	@Override
	public Pager listRoomByType(String type, int pageSize, int pageNo) {
		Pager page = new Pager();
		List<RoomActive> list = roomActiveService.getAllRoomActive();
		if(null != list && list.size() >0) {
			List<RoomInfoSchema> ret = new ArrayList<RoomInfoSchema>();
			int index = pageNo > 1 ? (pageNo - 1) * pageSize : 0;
			int size = list.size();
			page.setCount(size);
			for(int i=0; i<pageSize; i++) {
				if(size <= index) {
					break;
				}
				RoomActive ra = list.get(index);
				String enjoyType = ra.getEnjoyType();
				if(!StringUtil.isNullOrEmpty(enjoyType)) {
					String[] types = enjoyType.split(Constants.SEPARATOR_COMMA);
					List<String> listType = Arrays.asList(types);
					if(listType.contains(type)) {
						String roomId = ra.getRoomId();
						RoomInfoSchema dto = handleData(roomId);
						if(dto != null) {
							dto.setActiveTime(ra.getCreateTime().getTime());
							ret.add(dto);
						}
					}
				} else { // 为空的，归类到其他
					if(type.toLowerCase().equalsIgnoreCase(EnjoyType.OTHER.getValue())) {
						String roomId = ra.getRoomId();
						RoomInfoSchema dto = handleData(roomId);
						if(dto != null) {
							dto.setActiveTime(ra.getCreateTime().getTime());
							ret.add(dto);
						}
					}
				}
				index++;
			}
			page.setData(ret);
		}
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return page;
	}
	
	@Override
	public Pager listRecommend(int pageSize, int pageNo) {
		// 获取所有激活房间-此规则暂未定
//		List<String> roomIds = roomActiveService.getAllActiveRoomId();
//		if(StringUtil.isAllNull(roomIds)) {
//			return null;
//		}
		// 获取所有推荐房间
		List<RoomRecommend> list = roomRecommendService.listRoomRecommend();
		if(StringUtil.isAllNull(list)) {
			return null;
		}
		
		Pager page = new Pager();
		List<RoomInfoSchema> ret = new ArrayList<RoomInfoSchema>();
		int index = pageNo > 1 ? (pageNo - 1) * pageSize : 0;
		int size = list.size();
		page.setCount(size);
		for(int i=0; i<pageSize; i++) {
			if(size <= index) {
				break;
			}
			RoomRecommend rec = list.get(index);
			String roomId = rec.getRoomId();
			RoomInfoSchema dto = handleData(roomId);
			if(null != dto) {
				if(rec.getUrl() != null) {
					dto.setUrl(rec.getUrl());
				}
				RoomInfo room = roomInfoService.getRoomInfo(roomId);
				if(null != room && null != room.getCreateTime()) {
					dto.setActiveTime(room.getCreateTime().getTime());
				}
				ret.add(dto);
			}
			index++;
		}
		page.setData(ret);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return page;
	}

	@Override
	public List<SearchResultSchema> search(String condition) {
		// 房间id---查活跃房间的缓存
		List<SearchResultSchema> ret = new ArrayList<SearchResultSchema>();
		// 先匹配激活的房间
		List<RoomActive> list = roomActiveService.searchRoomActive(condition);
		List<String> userIds = new ArrayList<String>();
		if(list !=null && list.size() >0) {
			for(RoomActive ra : list) {
				RoomInfo room = roomInfoService.getRoomInfo(ra.getRoomId());
				if(room == null) {
					continue;
				}
				SearchResultSchema dto = new SearchResultSchema();
				String userId = ra.getUserId();
				dto.setRoomId(ra.getRoomId());
				dto.setRoomName(ra.getRoomName());
				dto.setUserId(userId);
				dto.setLockFlag(room.getLockFlag());
				dto.setActiveTime(ra.getCreateTime().getTime());
				String key = CacheConstants.MEMBER_SORT_DATA + ra.getRoomId();
				int online = (int) redisCache.zcard(key);
				if(online <= 0) {
					continue;
				}
				int full = 0;
				int memberLimit = room.getMemberLimit();
				if(online >= memberLimit) {
					full = 1;
				}
				dto.setOnline(online);
				dto.setFull(full);
				UserSchema user = userInfoService.getUserSchema(userId);
				if(null != user) {
					dto.setIcon(user.getCompleHead());
					dto.setNickName(user.getName());
				}
				// 在线人数暂时不处理，后续有需求再来处理
				userIds.add(userId);
				ret.add(dto);
			}
		}
		
		if(StringUtil.isAccount(condition)) {
			// 匹配用户表的用户id
			String userId = condition;
			if(!userIds.contains(userId)) {
				UserSchema user = userInfoService.getUserSchema(userId);
				if(null != user) {
					SearchResultSchema dto = new SearchResultSchema();
					dto.setUserId(user.getUserId());
					dto.setNickName(user.getName());
					dto.setIcon(user.getCompleHead());
					ret.add(dto);
				}
			}
		} else {
			List<UserSchema> users = userInfoService.searchByKeyWord(condition);
			if(users != null && users.size() >0) {
				for(UserSchema user: users) {
					SearchResultSchema dto = new SearchResultSchema();
					dto.setUserId(user.getUserId());
					dto.setNickName(user.getName());
					dto.setIcon(user.getCompleHead());
					ret.add(dto);
				}
			}
		}
		return ret;
	}

	private RoomInfoSchema handleData(String roomId) {
		RoomInfoSchema room = new RoomInfoSchema();
		RoomInfo info = roomInfoService.getRoomInfo(roomId);
		if(info == null) {
			return null;
		}
		if(info.getFeature() == Feature.HIDE.getValue()) {
			return null;
		}
		String userId = info.getUserId();
		room.setRoomId(roomId);
		room.setUserId(userId);
		room.setName(info.getName());
		room.setLockFlag(info.getLockFlag());
		// 在线人数
		String key = CacheConstants.MEMBER_SORT_DATA + roomId;
		int online = (int) redisCache.zcard(key);
		int grade = info.getGrade();
		if(grade != Grade.SYS.getValue()) {
			if(online <= 0) { // 房间成员为空，并且麦位为空，则不在首页显示（防止换成丢失）
				List<MicInfoSchema> mics = roomMicInfoService.listRoomMicInfo(roomId);
				if(null == mics || mics.size() <= 0) {
					return null;
				}
			}
		}
		room.setOnline(online);
		int full = 0;
		int memberLimit = info.getMemberLimit();
		if(online >= memberLimit) {
			full = 1;
		}
		room.setFull(full);
		UserSchema user = userInfoService.getUserSchema(userId);
		if(user != null) {
			room.setIcon(user.getCompleHead());
		}
		room.setGrade(info.getGrade());
		return room;
	}

}
