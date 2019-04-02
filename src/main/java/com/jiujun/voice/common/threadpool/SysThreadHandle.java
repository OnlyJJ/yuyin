package com.jiujun.voice.common.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 系统线程池，用于多线程，异步线程处理统一管理
 * @author Coody
 * @date 2018年10月31日
 */
public class SysThreadHandle {
	
	public static final int CORESIZE_NORMAL=20;
	public static final int MAXCORESIZE = 100;
	public static final int KEEPALIVETIME = 10;  //10s
	public static  ThreadFactory threadFactory = new ThreadFactory() {
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "t_sys_pool_" + r.hashCode());
		}
	};
	public static final ExecutorService  THREAD_POOL =new ThreadPoolExecutor(CORESIZE_NORMAL, MAXCORESIZE, KEEPALIVETIME, TimeUnit.MILLISECONDS,
			new ArrayBlockingQueue<Runnable>(MAXCORESIZE), threadFactory,new ThreadPoolExecutor.DiscardOldestPolicy());
	
	
}
