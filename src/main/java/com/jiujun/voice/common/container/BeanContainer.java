package com.jiujun.voice.common.container;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@Component
public class BeanContainer implements ApplicationContextAware {

	
	public static ApplicationContext context;

	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context = context;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) {
		return (T) context.getBean(beanName);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<?> beanType) {
		try {
			return (T) context.getBean(beanType);
		} catch (Exception e) {
			return null;
		}
	}
}
