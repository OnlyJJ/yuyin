package com.jiujun.voice.modules.caller.task;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.task.BaseTask;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.modules.caller.entity.SendGiftEntity;
import com.jiujun.voice.modules.caller.iface.AbstractSendGiftCaller;

/**
 * 赠送礼物后续任务
 * 
 * @author Coody
 *
 */
@Component
public class SendGiftTriggerTask extends BaseTask {

	@Resource
	RedisCache redisCache;

	@Scheduled(cron = "0/1 * * * * ? ")
	public synchronized void sendGiftTrigger() throws InterruptedException {
		SendGiftEntity entity = redisCache.lBeanProp(CacheConstants.CALLER_SENDGIFT_QUEUE);
		if (entity == null) {
			return;
		}
		while (entity != null) {
			try {
				AbstractSendGiftCaller.trigger(entity);
				TimeUnit.MILLISECONDS.sleep(1);
				entity = redisCache.lBeanProp(CacheConstants.CALLER_SENDGIFT_QUEUE);
			} catch (Exception e) {
				PrintException.printException(e);
				redisCache.lBeanPushTail(CacheConstants.CALLER_SENDGIFT_QUEUE, entity);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		}
	}

}
