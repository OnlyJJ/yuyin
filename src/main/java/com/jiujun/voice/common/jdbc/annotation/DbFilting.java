package com.jiujun.voice.common.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 存储前关键词过滤
 * 
 * @author Coody
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DbFilting {

	/**
	 * 用于替换的词
	 * 
	 * @return
	 */
	String seize() default "*";
	
	String error();
}
