package com.jiujun.voice.common.logger.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.jiujun.voice.common.logger.process.LoggerProcess;
import com.jiujun.voice.common.utils.StringUtil;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@Aspect
@Component
public class LoggerAspect {

	@Value("${logging.snake}")
	private Boolean loggerSnake;

	/**
	 * 日志溯源
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.jiujun.voice.common.logger.annotation.LogFlag) || @annotation(com.jiujun.voice.common.doc.annotation.DocFlag)")
	public Object gLoggerMonitor(ProceedingJoinPoint pjp) throws Throwable {
		if (loggerSnake==null||loggerSnake==false) {
			return pjp.proceed();
		}
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			Class<?> clazz = methodSignature.getDeclaringType();
			String module = LoggerProcess.getCurrentFlag();
			if (module == null) {
				module = "";
			}
			if (!StringUtil.isNullOrEmpty(module)) {
				module += ">";
			}
			String classLog = LoggerProcess.getClassFlag(clazz);
			if (!StringUtil.isNullOrEmpty(classLog)) {
				module += classLog;
			}
			if (!StringUtil.isNullOrEmpty(module)) {
				module += ".";
			}
			String methodLog = LoggerProcess.getMethodFlag(method);
			if (!StringUtil.isNullOrEmpty(methodLog)) {
				module += methodLog;
			} else {
				module += method.getName();
			}
			LoggerProcess.writeLogFlag(module);
			return pjp.proceed();
		} finally {
			LoggerProcess.wipeCurrentFlag();
			sw.stop();
		}
	}
}
