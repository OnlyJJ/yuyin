package com.jiujun.voice.common.task.entity;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.jiujun.voice.common.model.EntityModel;
import com.jiujun.voice.common.utils.DateUtils;

/**
 * 定时任务模型
 * @author Coody
 * @date 2018年11月14日
 */
@SuppressWarnings("serial")
public class TaskEntity extends EntityModel{

	/**
	 * 注册者服务器ID 
	 */
	private String serverId;
	/**
	 * 方法唯一标识
	 */
	private String methodKey;
	/**
	 * 状态，0停用 1启用
	 */
	private Integer status=1;
	/**
	 * cron表达式
	 */
	private String cron;
	/**
	 * 上次执行时间
	 */
	@JSONField(format=DateUtils.DATETIME_PATTERN)
	private Date runTime;
	/**
	 * 执行次数
	 */
	private Integer runNum=0;
	/**
	 * 执行类型 0缺省任务  1单机任务
	 */
	private Integer runType=0;
	
	
	
	public Integer getRunType() {
		return runType;
	}

	public void setRunType(Integer runType) {
		this.runType = runType;
	}

	public Integer getRunNum() {
		return runNum;
	}

	public void setRunNum(Integer runNum) {
		this.runNum = runNum;
	}

	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getMethodKey() {
		return methodKey;
	}

	public void setMethodKey(String methodKey) {
		this.methodKey = methodKey;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}
	
	
	
}
