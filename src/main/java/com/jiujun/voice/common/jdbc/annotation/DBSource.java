package com.jiujun.voice.common.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jiujun.voice.common.jdbc.source.DynamicDataSource;

/**
 * 指定数据源  暂未使用
 * @author Coody
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBSource {
	
	/**
	 * 数据源标记  master和slave
	 * @return
	 */
	String value() default DynamicDataSource.MASTER_FLAG;
	
}
