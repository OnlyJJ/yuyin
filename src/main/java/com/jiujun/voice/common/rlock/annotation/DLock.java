package com.jiujun.voice.common.rlock.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DLock {

	/**
	 * 锁基础名称
	 * @return
	 */
	String name() default "";
	/**
	 * 可选变量
	 * @return
	 */
	String[] fields() default {};
	/**
	 * 等待时间
	 * @return
	 */
	int waittime() default 30;
	/**
	 * 默认释放时间
	 * @return
	 */
	int leasetime() default 20;
	/**
	 * 屏蔽错误，为true则不抛出
	 * @return
	 */
	boolean igonreException() default false;
}
