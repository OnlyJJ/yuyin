package com.jiujun.voice.modules.caller.task;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.task.BaseTask;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.modules.apps.pay.domain.PayRecord;
import com.jiujun.voice.modules.caller.iface.AbstractPayCaller;

/**
 * 充值后续任务
 * 
 * @author Coody
 *
 */
@Component
public class PayTriggerTask extends BaseTask {

	@Resource
	RedisCache redisCache;

	@Scheduled(cron = "0/1 * * * * ? ")
	public synchronized void sendGiftTrigger() throws InterruptedException {
		PayRecord entity = redisCache.lBeanProp(CacheConstants.CALLER_PAY_QUEUE);
		if (entity == null) {
			return;
		}
		while (entity != null) {
			try {
				AbstractPayCaller.trigger(entity);
				TimeUnit.MILLISECONDS.sleep(1);
				entity = redisCache.lBeanProp(CacheConstants.CALLER_PAY_QUEUE);
			} catch (Exception e) {
				PrintException.printException(e);
				redisCache.lBeanPushTail(CacheConstants.CALLER_PAY_QUEUE, entity);
				TimeUnit.MILLISECONDS.sleep(100);
			}
		}
	}

}
