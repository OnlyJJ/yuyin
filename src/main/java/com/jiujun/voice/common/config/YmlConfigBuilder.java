package com.jiujun.voice.common.config;

import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.core.env.ConfigurablePropertyResolver;

import com.jiujun.voice.common.container.BeanContainer;


/**
 * 配置文件加载器
 * @author Coody
 *
 */
public class YmlConfigBuilder {

	private static ConfigurablePropertyResolver configurablePropertyResolver;

	static void initConfigBuilder() {
		if (configurablePropertyResolver != null) {
			return;
		}
		configurablePropertyResolver = BeanContainer.getBean(ConfigurablePropertyResolver.class);
	}

	public static boolean containsProperty(String key) {
		initConfigBuilder();
		return configurablePropertyResolver.containsProperty(key);
	}

	public static String getProperty(String key) {
		initConfigBuilder();
		return configurablePropertyResolver.getProperty(key);
	}

	public static String getProperty(String key, String defaultValue) {
		initConfigBuilder();
		return configurablePropertyResolver.getProperty(key, defaultValue);
	}

	public static <T> T getProperty(String key, Class<T> targetType) {
		initConfigBuilder();
		return configurablePropertyResolver.getProperty(key, targetType);
	}

	public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		initConfigBuilder();
		return configurablePropertyResolver.getProperty(key, targetType, defaultValue);
	}

	public static String getRequiredProperty(String key) throws IllegalStateException {
		initConfigBuilder();
		return configurablePropertyResolver.getRequiredProperty(key);
	}

	public static <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
		initConfigBuilder();
		return configurablePropertyResolver.getRequiredProperty(key, targetType);
	}

	public static String resolvePlaceholders(String text) {
		initConfigBuilder();
		return configurablePropertyResolver.resolvePlaceholders(text);
	}

	public static String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
		initConfigBuilder();
		return configurablePropertyResolver.resolveRequiredPlaceholders(text);
	}

	public static ConfigurableConversionService getConversionService() {
		initConfigBuilder();
		return configurablePropertyResolver.getConversionService();
	}

}
