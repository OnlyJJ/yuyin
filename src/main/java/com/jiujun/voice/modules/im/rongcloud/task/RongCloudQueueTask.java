package com.jiujun.voice.modules.im.rongcloud.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.task.BaseTask;
import com.jiujun.voice.common.threadpool.ThreadBlockHandle;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.entity.RongCloudMessageEntity;
import com.jiujun.voice.modules.im.rongcloud.util.RongCloudBuilder;

/**
 * 
 * @author Coody
 *
 */
@Component
public class RongCloudQueueTask extends BaseTask {

	@Resource
	RedisCache redisCache;

	@Scheduled(cron = "0/1 * * * * ? ")
	@LogFlag("消息处理")
	public synchronized void trigger() throws InterruptedException {
		String content = redisCache.lBeanProp(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE);
		if(StringUtil.isNullOrEmpty(content)){
			TimeUnit.MILLISECONDS.sleep(100L);
		}
		List<Runnable> runnables = new ArrayList<Runnable>();
		for (int i = 0; i < 30; i++) {
			try {
				RongCloudMessageEntity queue = JSON.parseObject(content, RongCloudMessageEntity.class);
				if(queue==null){
					return;
				}
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						try {
							sendMessage(queue);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
				runnables.add(runnable);
			} catch (Exception e) {
				// TODO: handle exception
			}
			content = redisCache.lBeanProp(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE);
			if(StringUtil.isNullOrEmpty(content)){
				break;
			}
		}
		/**
		 * 多线程阻塞执行
		 */
		ThreadBlockHandle threadPool=new ThreadBlockHandle(30);
		threadPool.execute(runnables);
	}

	public void sendMessage(RongCloudMessageEntity queue) throws InterruptedException {
		if(queue==null){
			return;
		}
		HttpEntity entity = null;
		try {
			Long starTime=System.currentTimeMillis();
			Map<String, String> headMap = RongCloudBuilder.buildHeader();
			String data=RongCloudBuilder.builderData(queue.getData());
			entity = HttpUtil.post(queue.getUrl(), data, headMap);
			Map<String, Object> result = JSON.parseObject(entity.getHtml(), new TypeReference<Map<String, Object>>() {
			});
			Long endTime=System.currentTimeMillis();
			if (result.get("code").toString().trim().equals("200")) {
				if(queue.getMessage()==null){
					return;
				}
				LogUtil.logger.info("消息发送成功>>耗时:"+(endTime-starTime) +",类型:"+ queue.getMessage().getType()+"("+PropertUtil.loadEnumByField(MsgEnum.class, "type", queue.getMessage().getType()).getRemark()+")"+",响应:"+result + ",内容:" + queue.getMessage().getContent()+",请求信息:"+data);
				return;
			}
			if(queue.getMessage()==null){
				return;
			}
			LogUtil.logger.info("消息发送失败>>耗时:"+(endTime-starTime) +",类型:"+ queue.getMessage().getType() +"("+PropertUtil.loadEnumByField(MsgEnum.class, "type", queue.getMessage().getType()).getRemark()+")" +",响应:"+result+ ",内容:" + queue.getMessage().getContent()+",请求信息:"+data);
			TimeUnit.MILLISECONDS.sleep(1L);
		} catch (Exception e) {
			redisCache.lBeanPushHead(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE, JSON.toJSONString(queue));
			PrintException.printException(e);
			TimeUnit.MILLISECONDS.sleep(10L);
			if(queue.getMessage()==null){
				return;
			}
			LogUtil.logger.info("消息发送失败>>类型:"+ queue.getMessage().getType() +"("+PropertUtil.loadEnumByField(MsgEnum.class, "type", queue.getMessage().getType()).getRemark()+")" + ",内容:" + queue.getMessage().getContent());
		} 
	}
}
