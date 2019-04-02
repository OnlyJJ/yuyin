package com.jiujun.voice.common.doc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 文档描述
 * @author Coody
 * @date 2018年11月16日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.TYPE,ElementType.PARAMETER})
public @interface DocFlag {

	String value();
	
	/**
	 * 生成自动化文档请求报文默认值
	 * @return
	 */
	String docFieldValue() default "";
	
}
