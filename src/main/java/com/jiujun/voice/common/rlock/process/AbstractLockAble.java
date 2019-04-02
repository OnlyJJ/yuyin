package com.jiujun.voice.common.rlock.process;

import com.jiujun.voice.common.container.BeanContainer;
import com.jiujun.voice.common.rlock.handle.LockHandle;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public abstract class AbstractLockAble {
	
	private static LockHandle lockHandle;
	
	
	private LockHandle getLockHandle() {
		if(lockHandle==null) {
			lockHandle=BeanContainer.getBean(LockHandle.class);
		}
		return lockHandle;
	}

	private String lockName;
	
	/**
	 * 等待时间
	 * @return
	 */
	private Integer waittime;
	/**
	 * 默认释放时间
	 * @return
	 */
	private Integer leasetime;
	/**
	 * 屏蔽错误，为true则不抛出
	 * @return
	 */
	
	
	public AbstractLockAble(String lockName){
		this.lockName=lockName;
		this.waittime=30;
		this.leasetime=20;
	}
	
	public AbstractLockAble(String lockName,Integer waittime,Integer leasetime){
		this.lockName=lockName;
		this.waittime=waittime;
		this.leasetime=leasetime;
	}
	
	/**
	 * 需要加锁执行的代码块
	 * @return
	 */
	public abstract Object doService();
	
	@SuppressWarnings("unchecked")
	public <T> T invoke() throws InterruptedException{
		try {
			getLockHandle().lock(lockName,waittime,leasetime);
			return (T) doService();
		} finally {
			getLockHandle().unlock(lockName);
		}
	}
}
