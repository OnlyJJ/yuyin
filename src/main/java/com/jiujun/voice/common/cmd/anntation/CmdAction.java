package com.jiujun.voice.common.cmd.anntation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记CMD下一个API方法
 * @author Coody
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CmdAction {
	/**
	 * API方法的action名称
	 * @return
	 */
	 String value() default "";
}
