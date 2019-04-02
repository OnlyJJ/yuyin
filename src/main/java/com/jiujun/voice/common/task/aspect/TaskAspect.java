package com.jiujun.voice.common.task.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.exception.GetAwayTaskException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.task.BaseTask;
import com.jiujun.voice.common.task.entity.TaskEntity;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.AspectUtil;

/**
 * 定时任务治理切面
 * 
 * @author Coody
 * @date 2018年11月14日
 */
@Component
@Aspect
public class TaskAspect {

	@Resource
	RedisCache redisCache;


	/**
	 * 定时任务控制
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
	public Object taskTrigger(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Class<?> clazz = pjp.getTarget().getClass();
			Method method = methodSignature.getMethod();
			if (method == null) {
				return pjp.proceed();
			}
			String key = AspectUtil.getMethodKey(clazz, method);
			String json = redisCache.hBeanGetString(CacheConstants.TASK_MAP, key);
			if (StringUtil.isNullOrEmpty(json)) {
				throw new GetAwayTaskException("定时任务分发中心>>该任务已脱离分发中心，请立即分配执行服务。"+key);
			}
			TaskEntity task = JSON.parseObject(json, TaskEntity.class);
			if (task.getStatus() == 0) {
				LogUtil.logger.debug("定时任务分发中心>>" + key + "，已停用");
				return null;
			}
			if (task.getRunType() == 1) {
				if (!task.getServerId().equals(BaseTask.SERVER_ID)) {
					return null;
				}
			}
			task.setRunTime(new Date());
			if(task.getRunNum()>=Integer.MAX_VALUE-10) {
				task.setRunNum(0);
			}
			task.setRunNum(task.getRunNum() + 1);
			redisCache.hBeanPushString(CacheConstants.TASK_MAP, key, JSON.toJSONString(task,SerializerFeature.PrettyFormat));
			Object result= pjp.proceed();
			return result;
		} finally {
			sw.stop();
		}
	}
}
