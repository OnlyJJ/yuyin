package com.jiujun.voice.modules.apps.room.cmd.vo;


import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class CheckRespVO extends BaseRespVO {
	@ParamCheck
	@DocFlag("房间是否上锁，0-否，1-是")
	private int lockFlag;
	
	@ParamCheck
	@DocFlag("房间是否满员，0-否，1-是")
	private int isFull;
	
	@ParamCheck
	@DocFlag("是否被踢，0-否，1-是")
	private int isForceOut;

	public int getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(int lockFlag) {
		this.lockFlag = lockFlag;
	}

	public int getIsFull() {
		return isFull;
	}

	public void setIsFull(int isFull) {
		this.isFull = isFull;
	}

	public int getIsForceOut() {
		return isForceOut;
	}

	public void setIsForceOut(int isForceOut) {
		this.isForceOut = isForceOut;
	}
	
	
}
