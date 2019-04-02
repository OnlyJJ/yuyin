package com.jiujun.voice.common.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.cache.instance.iface.AspectCacheFace;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheWrite {
	String key() default "";
	int time() default 10;
	String [] fields() default "";
	
	/**
	 * 使用的缓存实例
	 * @return
	 */
	Class<? extends AspectCacheFace> instance() default RedisCache.class;
	/**
	 * 读缓存
	 */
	boolean read() default true;
	/**
	 * 写缓存
	 * @return
	 */
	boolean write() default true;
	
}
