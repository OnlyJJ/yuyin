package com.jiujun.voice.common.task;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.MessageFormat;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.task.annotation.TaskSingle;
import com.jiujun.voice.common.task.entity.TaskEntity;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.AspectUtil;

/**
 * 定时任务最高超类
 * 
 * @author Coody
 * @date 2018年11月13日
 */
public class BaseTask implements InitializingBean {

	@Resource
	RedisCache redisCache;

	public static final String SERVER_ID = getUnicode();

	public static String getUnicode() {
		String user = System.getProperty("user.name");
		String computer = getHostName();
		String path = BaseTask.class.getResource("/").getPath();
		String serverId = MessageFormat.format("{0}>>{1}>>{2}", user, computer, path);
		serverId = serverId.replace("\\", "/").replace("/", "_");
		return serverId;
	}

	public static String getHostName() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Method[] methods = this.getClass().getDeclaredMethods();
		if (StringUtil.isNullOrEmpty(methods)) {
			return;
		}
		for (Method method : methods) {
			Scheduled handle = method.getAnnotation(Scheduled.class);
			if (StringUtil.isNullOrEmpty(handle)) {
				continue;
			}
			String key = AspectUtil.getMethodKey(this.getClass(), method);
			String json = redisCache.hBeanGetString(CacheConstants.TASK_MAP, key);
			TaskEntity task = null;
			if (!StringUtil.isNullOrEmpty(json)) {
				task = JSON.parseObject(json, TaskEntity.class);
			}
			Scheduled scheduled = method.getAnnotation(Scheduled.class);
			if (task != null && task.getServerId() != null) {
				if (SERVER_ID.equals(task.getServerId())) {
					// 本机注册，启用方法
					task.setCron(scheduled.cron());
					task.setStatus(1);
					task.setServerId(SERVER_ID);
					redisCache.hBeanPushString(CacheConstants.TASK_MAP, key, JSON.toJSONString(task,SerializerFeature.PrettyFormat));
					return;
				}
				LogUtil.logger.info("TASK_MAP:该方法已经被注册:" + key);
				// 该方法已经被其他机器注册
				continue;
			}
			task = new TaskEntity();
			task.setRunNum(0);
			task.setCron(scheduled.cron());
			task.setStatus(1);
			task.setMethodKey(key);
			task.setServerId(SERVER_ID);
			TaskSingle single = method.getAnnotation(TaskSingle.class);
			if (single != null) {
				// 单机任务
				task.setRunType(1);
			}
			LogUtil.logger.info("TASK_MAP:注册定时任务:" + key + ";" + task);
			// 注册方法
			redisCache.hBeanPushString(CacheConstants.TASK_MAP, key, JSON.toJSONString(task,SerializerFeature.PrettyFormat));
		}
	}

}
