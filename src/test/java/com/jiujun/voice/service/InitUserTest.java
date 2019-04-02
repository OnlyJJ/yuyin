package com.jiujun.voice.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.utils.FileUtils;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.modules.apps.user.invite.domain.InvitePrizedRecord;
import com.jiujun.voice.modules.apps.user.invite.service.InviteService;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
@RunWith(SpringRunner.class)
@SpringBootTest( classes = VoiceApplication.class)
public class InitUserTest {

	@Resource
	UserAccountService userAccountService;
	@Resource
	RedisCache redisCache;
	@Resource
	InviteService inviteService;
	
	
	@Test
	public void createUser(){
		
		for(int i=0;i<30;i++){
			UserAccount account=userAccountService.initUserAccount("V_测试0320-"+(i)+"号", null, null, null, JUUIDUtil.createUuid(), "123456", 4);
			FileUtils.writeAppendLine("d://uid.txt", account.getUserId());
		}
	}
	
	
	
	@Test
	public void testPay() throws InterruptedException{
		InvitePrizedRecord prizeRecord=inviteService.getUserInvitePrize("100759");
		System.out.println(prizeRecord.toString());
	}
	
	public static void main(String[] args) {
		System.out.println(JUUIDUtil.createUuid());
	}
}
