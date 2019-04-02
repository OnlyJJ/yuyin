package com.jiujun.voice.common.cache.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.cache.instance.LocalCache;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.cache.instance.iface.AspectCacheFace;
import com.jiujun.voice.common.container.BeanContainer;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.AspectUtil;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@Component
@Aspect
public class CacheAspect {

	@Resource(name = "redisCache")
	RedisCache redisCache;

	@Resource(name = "localCache")
	LocalCache localCache;

	/**
	 * 写缓存操作
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.jiujun.voice.common.cache.annotation.CacheWrite)")
	public Object cacheWrite(ProceedingJoinPoint pjp) throws Throwable {
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
			// 获取注解
			CacheWrite cacheWrite = method.getAnnotation(CacheWrite.class);
			if (cacheWrite == null) {
				return pjp.proceed();
			}
			AspectCacheFace cacheHandle = getCacheFace(cacheWrite.instance());
			// 封装缓存KEY
			Object[] paras = pjp.getArgs();
			String key = cacheWrite.key();
			try {
				if (StringUtil.isNullOrEmpty(key)) {
					key = AspectUtil.getMethodKey(clazz, method);
				}
				if (StringUtil.isNullOrEmpty(cacheWrite.fields())) {
					String paraKey = AspectUtil.getFieldKey(method, paras);
					if (!StringUtil.isNullOrEmpty(paraKey)) {
						key += ":";
						key += paraKey;
					}
				}
				if (!StringUtil.isNullOrEmpty(cacheWrite.fields())) {
					String paraKey = AspectUtil.getFieldKey(method, paras, cacheWrite.fields());
					if (!StringUtil.isNullOrEmpty(paraKey)) {
						key += ":";
						key += paraKey;
					}
				}
			} catch (Exception e) {
				PrintException.printException(e);
			}
			Integer cacheTimer = ((cacheWrite.time() == 0) ? 24 * 3600 : cacheWrite.time());
			// 获取缓存
			if (cacheWrite.read()) {
				try {
					Object result = cacheHandle.getCache(key);
					if (!LogUtil.isProd()) {
						LogUtil.logger.debug("获取缓存:" + key + ",结果:" + result);
					}
					if (result != null) {
						return result;
					}
				} catch (Exception e) {
					PrintException.printException(e);
				}
			}
			Object result = pjp.proceed();
			if (cacheWrite.write()) {
				if (result != null) {
					try {
						cacheHandle.setCache(key, result, cacheTimer);
						if (!LogUtil.isProd()) {
							LogUtil.logger.debug("设置缓存:" + key + ",结果:" + result + ",缓存时间:" + cacheTimer);
						}
					} catch (Exception e) {
					}
				}
			}
			return result;
		} finally {
			sw.stop();
		}
	}

	/**
	 * 缓存清理
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.jiujun.voice.common.cache.annotation.CacheWipe)||@annotation(com.jiujun.voice.common.cache.annotation.CacheWipes)")
	public Object cacheWipe(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// 启动监听
			sw.start(pjp.getSignature().toShortString());
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Class<?> clazz = pjp.getTarget().getClass();
			Method method = methodSignature.getMethod();
			if (method == null) {
				return pjp.proceed();
			}
			Object[] paras = pjp.getArgs();
			Object result = pjp.proceed();
			CacheWipe[] cacheWipes = method.getAnnotationsByType(CacheWipe.class);
			if (StringUtil.isNullOrEmpty(cacheWipes)) {
				return result;
			}
			for (CacheWipe cacheWipe : cacheWipes) {
				try {
					AspectCacheFace cacheHandle = getCacheFace(cacheWipe.instance());
					String key = cacheWipe.key();
					if (StringUtil.isNullOrEmpty(cacheWipe.key())) {
						key = AspectUtil.getMethodKey(clazz, method);
					}
					if (!StringUtil.isNullOrEmpty(cacheWipe.fields())) {
						String paraKey = AspectUtil.getFieldKey(method, paras, cacheWipe.fields());
						if (!StringUtil.isNullOrEmpty(paraKey)) {
							key += ":";
							key += paraKey;
						}
					}
					if (!LogUtil.isProd()) {
						LogUtil.logger.debug("删除缓存:" + key);
					}
					cacheHandle.delCache(key);
				} catch (Exception e) {
					PrintException.printException(e);
				}
			}
			return result;
		} finally {
			sw.stop();
		}
	}

	private AspectCacheFace getCacheFace(Class<? extends AspectCacheFace> clazz) {
		if (RedisCache.class.isAssignableFrom(clazz)) {
			return redisCache;
		}
		if (LocalCache.class.isAssignableFrom(clazz)) {
			return localCache;
		}
		AspectCacheFace bean = (AspectCacheFace) BeanContainer.getBean(clazz);
		return bean;
	}

}
