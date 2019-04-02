package com.jiujun.voice.common.jdbc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于domain实体类标记对应表名
 * @author Coody
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
	 String value() default "";
	 
	 /**
	  * 是否允许一键saveOrUpdate
	  * @return
	  */
	 boolean onekeyWrite() default true;
}
