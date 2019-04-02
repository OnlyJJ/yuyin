package com.jiujun.voice.common.logger.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.config.YmlConfigBuilder;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

/**
 * 
 * @author Coody
 * @date 2018年11月16日
 */
@Component
public class LogUtil implements InitializingBean {


	public static Logger logger = LoggerFactory.getLogger(LogUtil.class);

	/**
	 * 是否是生产环境
	 * 
	 * @return
	 */
	public static boolean isProd() {
		String systemIdentity = YmlConfigBuilder.getProperty("spring.profiles.active");
		return "prod".equals(systemIdentity);
	}

	public static void main(String[] args) {
		System.out.println(LogUtil.class.getPackage().getName());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String systemIdentity = YmlConfigBuilder.getProperty("spring.profiles.active");
		logger.info("日志级别控制.当前环境>>" + systemIdentity);
		if (!"dev".equals(systemIdentity)) {
			return;
		}
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		ch.qos.logback.classic.Logger qosLogger = loggerContext.getLogger("root");
		qosLogger.setLevel(Level.DEBUG);
		logger.info("日志级别控制.修改日志级别>>" + "DEBUG");
		return;
	}
}
