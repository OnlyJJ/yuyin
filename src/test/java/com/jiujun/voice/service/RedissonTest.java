package com.jiujun.voice.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.common.rlock.handle.LockHandle;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = VoiceApplication.class)
public class RedissonTest {
	@Resource
	LockHandle lockHandle;
	
	@Test
	public void test() throws InterruptedException {
		for(int i=0; i<3;i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					String lockname = "lock-test-redison";
					try {
						System.err.println("开始竞争锁：" + Thread.currentThread().getName());
						lockHandle.lock(lockname);
						System.err.println("抢锁成功:" + Thread.currentThread().getName());
						Thread.sleep(2000);
						System.err.println("抢锁成功: sleep 2s" + Thread.currentThread().getName());
					}catch(Exception e) {
						
					} finally {
						lockHandle.unlock(lockname);
					}
				}
			}).start();
		}
		Thread.sleep(20000);
	}
	
}
