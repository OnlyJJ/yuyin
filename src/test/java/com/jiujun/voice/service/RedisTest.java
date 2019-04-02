package com.jiujun.voice.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.common.cache.instance.RedisCache;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = VoiceApplication.class)
public class RedisTest {
	@Resource
	RedisCache redisCache;
	
	@Test
	public void test() {
		String key = "voice-test";
		String value = "this is test!";
		redisCache.setString(key, value);
		
		System.err.println(redisCache.getString(key));
	}
}
