package com.jiujun.voice.service;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.modules.caller.entity.SendGiftEntity;

/**
 * 送礼调度器测试
 * 
 * @author Coody
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VoiceApplication.class)
public class TestCaller {

	@Resource
	RedisCache redisCache;

	@Resource
	DataSource dataSource;

	@Test
	public void pustCaller() {
		SendGiftEntity sendGiftEntity = new SendGiftEntity();
		sendGiftEntity.setFromUserId("10086");
		sendGiftEntity.setToUserId("10010");
		sendGiftEntity.setGiftId(86);
		sendGiftEntity.setNum(2);
		sendGiftEntity.setRoomId("10010");
		redisCache.lBeanPushTail(CacheConstants.CALLER_SENDGIFT_QUEUE, sendGiftEntity);
	}

	public static void testSwitch() {
		String name = "l";
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			switch (name) {
			case "a":
				break;
			case "b":
				break;
			case "c":
				break;
			case "d":
				break;
			case "e":
				break;
			case "f":
				break;
			case "g":
				break;
			case "h":
				break;
			case "i":
				break;
			case "j":
				break;
			case "k":
				break;
			case "l":
				break;
			default:
				break;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("switch>>" + (end - start));
	}

	public static void testIf() {
		String name = "l";
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000000; i++) {
			if (name.equals("a")) {
				continue;
			}
			if (name.equals("b")) {
				continue;
			}
			if (name.equals("c")) {
				continue;
			}
			if (name.equals("d")) {
				continue;
			}
			if (name.equals("e")) {
				continue;
			}
			if (name.equals("f")) {
				continue;
			}
			if (name.equals("g")) {
				continue;
			}
			if (name.equals("h")) {
				continue;
			}
			if (name.equals("i")) {
				continue;
			}
			if (name.equals("j")) {
				continue;
			}
			if (name.equals("k")) {
				continue;
			}
			if (name.equals("l")) {
				continue;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("if>>" + (end - start));
	}

	public static void main(String[] args) {
		testIf();
		testSwitch();
	}
}
