package com.jiujun.voice.common.doc.entity;

import java.util.List;

import com.jiujun.voice.common.model.EntityModel;

/**
 * 指令
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class CmdDocument extends EntityModel{
	
	/**
	 * 指令
	 */
	private String cmd;
	/**
	 * 指令名称
	 */
	private String name;
	/**
	 * 所属类
	 */
	private Class<?> clazz;
	/**
	 * 动作列表
	 */
	private List<ActionDocument> actions;
	
	/**
	 * 交易编码列表
	 */
	public List<ResultCodeDocument> resultCodes;
	
	
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	public List<ActionDocument> getActions() {
		return actions;
	}
	public void setActions(List<ActionDocument> actions) {
		this.actions = actions;
	}
	public List<ResultCodeDocument> getResultCodes() {
		return resultCodes;
	}
	public void setResultCodes(List<ResultCodeDocument> resultCodes) {
		this.resultCodes = resultCodes;
	}
	

	
}
