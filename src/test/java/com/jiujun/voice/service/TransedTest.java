package com.jiujun.voice.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.modules.apps.user.useraccount.service.BackPackService;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = VoiceApplication.class)
public class TransedTest {
	
	@Resource
	BackPackService backPackService;

	
	@Test
	public void transedTest() {
//		backPackService.addBackPack("129849", "4", 1, 1, "测试礼物");
	}
}
