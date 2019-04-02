package com.jiujun.voice.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.modules.apps.home.cmd.schema.SearchResultSchema;
import com.jiujun.voice.modules.apps.home.service.BannerInfoService;
import com.jiujun.voice.modules.apps.home.service.HomePageService;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.domain.RoomThemeRecord;
import com.jiujun.voice.modules.apps.room.service.RoomActiveService;
import com.jiujun.voice.modules.apps.room.service.RoomEnjoyTypeService;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomMemberBehaviorService;
import com.jiujun.voice.modules.apps.room.service.RoomMicInfoService;
import com.jiujun.voice.modules.apps.room.service.RoomRecommendService;
import com.jiujun.voice.modules.apps.room.service.RoomRoleService;
import com.jiujun.voice.modules.apps.room.service.RoomThemeRecordService;
import com.jiujun.voice.modules.apps.room.service.SendGiftRecordService;
import com.jiujun.voice.modules.apps.user.useraccount.service.LevelInfoService;
import com.jiujun.voice.modules.apps.user.userinfo.dao.UserInfoDao;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = VoiceApplication.class)
public class RoomServiceTest {
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	RoomThemeRecordService roomThemeRecordService;
	@Resource
	RoomActiveService roomActiveService;
	@Resource
	HomePageService homePageService;
	@Resource
	RoomEnjoyTypeService roomEnjoyTypeService;
	@Resource
	RoomMemberBehaviorService roomMemberBehaviorService;
	@Resource
	RoomMicInfoService roomMicInfoService;
	@Resource
	BannerInfoService nannerInfoService;
	@Resource
	SendGiftRecordService sendGiftRecordService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	RoomRoleService roomRoleService;
	@Resource
	UserInfoDao userInfoDao;
	@Resource
	LevelInfoService levelInfoService;
	@Resource
	RoomRecommendService roomRecommendService;
	
	public static void main(String[] args) throws Exception { 
		long time = DateUtils.getSubTime();
		long sorce1 = 0-200000000 - time;
		System.err.println("sorce1= " + sorce1 + ",time= "+time);
		Thread.sleep(2000);
		long time2 = DateUtils.getSubTime();
		long sorce2 = 0-200000000 - time2;
		System.err.println("sorce2= " + sorce2 + ",time2= "+time2);
	}
	
	@Test
	public void create() {
		RoomInfo info = new RoomInfo();
		info.setUserId("101011");
		info.setRoomId("101011");
		info.setName("哎哟不错");
		info.setEnjoyType("CJ");
		roomInfoService.createRoom(info);
	}
	
	@Test
	public void getRoom() {
		RoomInfo info = roomInfoService.getRoomInfo("101011");
		if(info != null) {
			System.err.println(JSON.toJSON(info));
		}
		RoomThemeRecord rd = roomThemeRecordService.getValidTheme("101011");
		if(rd != null) {
			System.err.println(JSON.toJSON(rd));
		}
	}
	
	@Test
	public void editTheme() {
		String userId = "101011";
		String roomId = "101011";
		String theme = "好厉害哦";
		roomThemeRecordService.saveTheme(userId, roomId, theme);
	}
	
	@Test
	public void getRoomActive() {
//		PageDTO<RoomInfoDTO> data = homePageService.listQuickRoom(36, 1);
		List<SearchResultSchema> data = homePageService.search("101010");
		if(data != null) {
			System.err.println(JSON.toJSONString(data));
		}
	}
	
	@Test
	public void getRoomEnjoy() {
//		List<RoomEnjoyType> data = roomEnjoyTypeService.listRoomEnjoyConf();
//		List<BannerInfo> data = nannerInfoService.listBannerInfo();
//		if(data != null) {
//			System.err.println(JSON.toJSONString(data));
//		}
		String roomId = "124532";
		String fromUserId = "124532";
		String toUserId = "115031";
		int giftId= 1;
		int num = 1;
		int giftSource = 1;
//		sendGiftRecordService.sendGift(roomId, fromUserId, toUserId, giftId, num, giftSource);
	}
	
	@Test
	public void mic() {
	}

}
