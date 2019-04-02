package com.jiujun.voice.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;


/**
/**
 *  定时任务并行执行
 * @author Coody
 **/
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {
	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		scheduledTaskRegistrar.setScheduler(setTaskExecutors());
	}

	@Bean(destroyMethod = "shutdown")
	public Executor setTaskExecutors() {
		return new ScheduledThreadPoolExecutor(30, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r);
			}
		});
	}
}
