package com.jiujun.voice.common.cache.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.cache.instance.iface.AspectCacheFace;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(CacheWipes.class)
public @interface CacheWipe {

	String key();

	String[] fields() default "";
	/**
	 * 使用的缓存实例
	 * @return
	 */
	Class<? extends AspectCacheFace> instance() default RedisCache.class;

}
