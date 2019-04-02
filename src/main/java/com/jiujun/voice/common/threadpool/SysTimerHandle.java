package com.jiujun.voice.common.threadpool;

import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 定时器线程池，用于取代timer执行定时任务
 * @author Coody
 * @date 2018年10月31日
 */
public class SysTimerHandle {
	
	
	public static ThreadFactory THREADFACTORY = new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "t_timer_pool_" + r.hashCode());
		}
	};
	public static final ScheduledExecutorService SYSTEM_TIMER =new ScheduledThreadPoolExecutor(30, THREADFACTORY);
	
	public static void execute(TimerTask task,Integer delayed){
		try {
			SYSTEM_TIMER.schedule(task, delayed*1000,TimeUnit.MILLISECONDS);
		} catch (Exception e) {
		}
		
	}
}
