package com.jiujun.voice.common.monitor.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.monitor.annotation.RunTimeFlag;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;

/**
 * 
 * @author Coody
 *
 */
@Component
@Aspect
public class LogAspect {

	/**
	 *  监听执行时间
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.jiujun.voice.common.monitor.annotation.RunTimeFlag)")
	public Object cacheWrite(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			RunTimeFlag logFlag = method.getAnnotation(RunTimeFlag.class);
			String flag = logFlag.value();
			if (StringUtil.isNullOrEmpty(flag)) {
				flag = method.getName();
			}
			Object args = pjp.getArgs();
			String log = "执行开始:" + flag;
			if (logFlag.outContext()) {
				log += ("|入参:" + getJson(args));
			}
			LogUtil.logger.info(log);
			Long start = System.currentTimeMillis();
			Object result = null;
			try {
				result = pjp.proceed();
			} catch (Exception e) {
				Long end = System.currentTimeMillis();
				log = "执行结束:" + flag + ",耗时:" + (end - start) + "(异常信息:" + PrintException.getErrorStack(e) + ")";
				if (logFlag.outContext()) {
					log += ("|出参:" + getJson(result));
				}
				LogUtil.logger.info(log);
				throw e;
			}
			Long end = System.currentTimeMillis();
			log = "执行结束:" + flag + ",耗时:" + (end - start);
			if (logFlag.outContext()) {
				log += ("|出参:" + getJson(result));
			}

			LogUtil.logger.info(log);
			return result;
		} finally {
			sw.stop();
		}
	}

	private static String getJson(Object... args) {
		if (StringUtil.isNullOrEmpty(args)) {
			return "";
		}
		try {
			List<Object> newArgs = new ArrayList<Object>();
			for (Object arg : args) {
				Object tmp = arg;
				if (arg != null) {
					if (ServletRequest.class.isAssignableFrom(arg.getClass())
							|| ServletResponse.class.isAssignableFrom(arg.getClass())) {
						tmp = arg.getClass();
					}
				}
				newArgs.add(tmp);
			}
			return JSON.toJSONString(newArgs);
		} catch (Exception e) {
			return null;
		}

	}
}
