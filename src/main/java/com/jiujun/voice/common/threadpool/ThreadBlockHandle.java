package com.jiujun.voice.common.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jiujun.voice.common.logger.util.LogUtil;

/**
 * 阻塞模式线程池，用于阻塞模式下多任务处理
 * 
 * @author Coody
 * @blog http://54sb.org
 *
 */
public class ThreadBlockHandle {

	ExecutorService exePool;
	private List<Runnable> runnables = new ArrayList<Runnable>();
	private boolean isActivity = true;

	private Integer maxThread = 100;

	private Integer timeOutSeconds = 60;

	public ThreadBlockHandle(Integer maxThread) {
		this.maxThread = maxThread;
	}

	public Integer getMaxThread() {
		return maxThread;
	}

	public void setMaxThread(Integer maxThread) {
		this.maxThread = maxThread;
	}

	public ThreadBlockHandle() {

	}

	public ThreadBlockHandle(Integer maxThread, Integer timeOutSeconds) {
		this.maxThread = maxThread;
		this.timeOutSeconds = timeOutSeconds;
	}

	public ThreadBlockHandle(List<Runnable> runnables) {
		this.runnables.addAll(runnables);
	}

	public void execute(List<Runnable> runnables) {
		pushTask(runnables);
		execute();
	}

	public void execute() {
		if (!isActivity) {
			LogUtil.logger.info("ThreadBlockHandle:线程池已销毁==================");
			return;
		}
		isActivity = false;
		if (runnables == null || runnables.isEmpty()) {
			return;
		}
		Integer currThread = runnables.size();
		if (currThread > maxThread) {
			currThread = maxThread;
		}
		ThreadFactory threadFactory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				return new Thread(r, "t_block_pool_" + r.hashCode());
			}
		};
		exePool = new ThreadPoolExecutor(currThread, currThread, currThread, TimeUnit.MILLISECONDS,
				new ArrayBlockingQueue<Runnable>(currThread), threadFactory,new ThreadPoolExecutor.DiscardOldestPolicy());
		LogUtil.logger.info("ThreadBlockHandle:[" + maxThread + "]执行中==================");
		for (Runnable runnable : runnables) {
			exePool.execute(runnable);
		}
		exePool.shutdown();
		try {
			exePool.awaitTermination(timeOutSeconds * 1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		LogUtil.logger.info("ThreadBlockHandle:[" + maxThread + "]执行完毕==================");
	}

	public boolean pushTask(List<Runnable> runnables) {
		if (!isActivity) {
			LogUtil.logger.info("ThreadBlockHandle:线程池已销毁==================");
			return false;
		}
		this.runnables.addAll(runnables);
		return isActivity;
	}

	public boolean pushTask(Runnable runnable) {
		if (!isActivity) {
			LogUtil.logger.info("ThreadBlockHandle:线程池已销毁==================");
			return false;
		}
		runnables.add(runnable);
		return isActivity;
	}

	public static void main(String[] args) {
	}
}
