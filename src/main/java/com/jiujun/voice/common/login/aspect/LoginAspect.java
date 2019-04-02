package com.jiujun.voice.common.login.aspect;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle.TokenEntity;

/**
 * 登录鉴权控制
 * @author Coody
 * @date 2018年11月14日
 */
@Component
@Aspect
public class LoginAspect {

	@Resource
	UserAuthHandle userAuthHandle;
	
	
	/**
	 * 登录鉴权
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("@annotation(com.jiujun.voice.common.cmd.anntation.CmdAction)")
	public Object cacheWrite(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch(getClass().getSimpleName());
		try {
			// AOP启动监听
			sw.start(pjp.getSignature().toShortString());
			// AOP获取方法执行信息
			Signature signature = pjp.getSignature();
			MethodSignature methodSignature = (MethodSignature) signature;
			Method method = methodSignature.getMethod();
			if (method == null) {
				return pjp.proceed();
			}
			// 获取注解
			LoginIgnore loginIgnore = method.getAnnotation(LoginIgnore.class);
			if(loginIgnore!=null) {
				return  pjp.proceed();
			}
			//登录鉴权
			Object[] paras = pjp.getArgs();
			if(StringUtil.isNullOrEmpty(paras)) {
				return null;
			}
			BaseReqVO req=(BaseReqVO) paras[0];
			if(StringUtil.isNullOrEmpty(req)||StringUtil.isNullOrEmpty(req.getHeader())) {
				return null;
			}
			String userId=req.getHeader().getUserId();
			if(StringUtil.isNullOrEmpty(userId)) {
				throw new CmdException(ErrorCode.ERROR_1011);
			}
			TokenEntity token=userAuthHandle.getUserToken(userId);
			if(token==null) {
				throw new CmdException(ErrorCode.ERROR_1011);
			}
			if(!token.getToken().equals(req.getHeader().getToken())) {
				throw new CmdException(ErrorCode.ERROR_1011);
			}
			return pjp.proceed();
		} finally {
			sw.stop();
		}
	}
	
}
