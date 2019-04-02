package com.jiujun.voice.common.rlock.handle;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.logger.util.LogUtil;

/**
 * 分布式锁
 * 
 * @author shao.xiang
 * @date 2017-09-08
 */
@Component
public class LockHandle {

	@Resource
	public RedissonClient redisson;

	/** 默认线程最大等待时间 30s */
	private static final int DEFAULT_WAITTIME = 30;
	/** 默认锁过期自动释放时间，20秒 */
	private static final int DEFAULT_LEASETIME = 20;

	private LockHandle() {
	}

	
	/**
	 * 竞争锁，有默认自动过期时间
	 * 
	 * @param lockname 锁
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void lock(final String lockname) throws InterruptedException {
		lock(lockname, DEFAULT_WAITTIME, DEFAULT_LEASETIME);
	}

	/**
	 * 竞争锁，有默认自动过期时间
	 * 
	 * @param lockname 锁
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void lock(final String lockname, int waittime, int leasetime) throws InterruptedException {
		RLock lock = redisson.getLock(lockname);
		long begin = System.currentTimeMillis();
		LogUtil.logger.info("### lock-开始竞争锁，ThreadId=" + Thread.currentThread().getId() + ",lockname=" + lockname);
		boolean success = lock.tryLock(waittime, leasetime, TimeUnit.SECONDS);
		if (!success) {
			LogUtil.logger.info("### lock-加锁超时，ThreadId=" + Thread.currentThread().getId() + ",lockname=" + lockname);
			throw ErrorCode.ERROR_1008.builderException();
		}
		long end = System.currentTimeMillis();
		LogUtil.logger.info("### lock-加锁成功！ThreadId=" + Thread.currentThread().getId() + ",lockname=" + lockname
				+ ",time=" + (end - begin));
	}

	/**
	 * 释放锁
	 * 
	 * @param lockname 锁名称
	 */
	public void unlock(String lockname) {
		try {
			LogUtil.logger.info("### lock-处理解锁，ThreadId=" + Thread.currentThread().getId() + ",lockname=" + lockname);
			RLock lock = redisson.getLock(lockname);
			lock.unlock();
			LogUtil.logger.info("### lock-解锁成功，ThreadId=" + Thread.currentThread().getId() + ",lockname=" + lockname);
		} catch (Exception e) {
			LogUtil.logger.info("### lock-解锁失败，ThreadId=" + Thread.currentThread().getId() + ",lockname=" + lockname);
			LogUtil.logger.error(e.getMessage());
		}
	}
}
