package com.jiujun.voice.common.jdbc.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Transactional
public @interface Transacted {
	@AliasFor("transactionManager")
	String value() default "";

	@AliasFor("value")
	String transactionManager() default "";

	Propagation propagation() default Propagation.REQUIRED;

	Isolation isolation() default Isolation.DEFAULT;

	int timeout() default -1;

	boolean readOnly() default false;

	Class<? extends Throwable>[] rollbackFor() default { Exception.class };

	String[] rollbackForClassName() default {};

	Class<? extends Throwable>[] noRollbackFor() default {};

	String[] noRollbackForClassName() default {};
}

/*
 * Location:
 * D:\java\maven_repo\org\springframework\spring-tx\5.0.7.RELEASE\spring-tx-5.0.
 * 7.RELEASE.jar Qualified Name:
 * org.springframework.transaction.annotation.Transactional Java Class Version:
 * 8 (52.0) JD-Core Version: 0.7.0.1
 */