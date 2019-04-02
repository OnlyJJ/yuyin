package com.jiujun.voice.common.jdbc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.jiujun.voice.common.jdbc.source.DynamicDataSource;

/**
 * 多数据源控制
 * @author Coody
 * @date 2018年9月21日
 */
@Aspect
@Component
public class DataSourceAspect {
	

	/**
	 * 为所有CMD指定从库
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.jiujun.voice.common.cmd.anntation.CmdAction)")
	public Object buildCmdDataSource(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			//默认指定从库
			DynamicDataSource.setDataSourceFlag(DynamicDataSource.SLAVE_FLAG);
			return pjp.proceed();
		} finally {
			//释放线程数据源
			DynamicDataSource.clearDataSourceFlag();
			sw.stop();
		}
	}
	
/*	*//**
	 * 为所有CMD指定从库
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 *//*
	@Around("@annotation(com.jiujun.voice.common.jdbc.annotation.DBSource)")
	public Object buildMethodDataSource(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			DBSource dbSource=method.getAnnotation(DBSource.class);
			//默认指定从库
			DynamicDataSource.setDataSourceFlag(dbSource.value());
			return pjp.proceed();
		} finally {
			//释放线程数据源
			sw.stop();
		}
	}*/
}
