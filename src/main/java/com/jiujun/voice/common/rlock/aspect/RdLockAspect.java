package com.jiujun.voice.common.rlock.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import com.jiujun.voice.common.rlock.annotation.DLock;
import com.jiujun.voice.common.rlock.handle.LockHandle;
import com.jiujun.voice.common.utils.property.AspectUtil;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@Aspect
@Component
public class RdLockAspect {
	
	@Resource
	LockHandle lockHandle;

	/**
	 * 分布式锁
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.jiujun.voice.common.rlock.annotation.DLock)")
	public Object rdLock(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		String lockName = null;
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			Class<?> clazz = pjp.getTarget().getClass();

			DLock dlock = method.getAnnotation(DLock.class);
			lockName = dlock.name();
			if (StringUtils.isEmpty(lockName)) {
				lockName = AspectUtil.getMethodKey(clazz, method);
			}
			Object[] paras = pjp.getArgs();
			if (dlock.fields() != null && dlock.fields().length > 0) {
				String fieldCode = AspectUtil.getFieldKey(method, paras, dlock.fields());
				lockName += fieldCode;
			}
			lockHandle.lock(lockName,dlock.waittime(),dlock.leasetime());
			try {
				return pjp.proceed();
			} catch (Exception e) {
				if (!dlock.igonreException()) {
					throw e;
				}
				return null;
			}
		} finally {
			lockHandle.unlock(lockName);
			sw.stop();
		}
	}

	
	
	public static void main(String[] args) {
		System.out.println("\u4ea4\u6613\u91cf\u8f93\u5165\u9519\u8bef\uff0c100-50000\u4e4b\u95f4");
	}

}
