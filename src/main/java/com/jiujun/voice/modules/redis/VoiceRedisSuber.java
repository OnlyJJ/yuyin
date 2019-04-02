package com.jiujun.voice.modules.redis;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.threadpool.SysThreadHandle;
import com.jiujun.voice.modules.redis.entity.RedisSuberEntity;
import com.jiujun.voice.modules.redis.suber.AbstractRedisSuber;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

/**
 * 
 * @author Coody
 *
 */
@Component
public class VoiceRedisSuber implements InitializingBean {


	@Resource
	private JedisPool jedisPool;

	@Override
	public void afterPropertiesSet() throws Exception {
		SubscriberSuber subscriberSuber = new SubscriberSuber();
		SysThreadHandle.THREAD_POOL.execute(new Runnable() {
			@Override
			public void run() {
				jedisPool.getResource().subscribe(subscriberSuber, CacheConstants.VOICE_PUBSUB);
			}
		});
	}

	public class SubscriberSuber extends JedisPubSub {

		public SubscriberSuber() {
		}

		@Override
		public void onMessage(String channel, String message) { // 收到消息会调用
			LogUtil.logger.info("REDIS渠道收到消息>>" + message);
			RedisSuberEntity entity = JSON.parseObject(message, RedisSuberEntity.class);
			AbstractRedisSuber.dispat(entity);
		}

	}
}
