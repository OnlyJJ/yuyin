package com.jiujun.voice.common.doc.entity;

import java.lang.reflect.Method;
import java.util.Set;

import com.jiujun.voice.common.model.EntityModel;

/**
 * 动作信息
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ActionDocument extends EntityModel{

	/**
	 * 接口名字
	 */
	private String name;
	/**
	 * 动作
	 */
	private String action;
	/**
	 * 是否需要登录鉴权
	 */
	private Boolean needLogin;
	
	/**
	 *入参
	 */
	private ParamDocument input;
	/**
	 * 出参
	 */
	private ParamDocument output;

	/**
	 * 关联对象
	 */
	private Set<ParamDocument> relotions;
	
	/**
	 * 实际方法
	 */
	private Method method;
	
	/**
	 * 接口指纹，用于校验接口是否已变更
	 */
	private String fingerprint;
	
	
	
	
	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public ParamDocument getInput() {
		return input;
	}

	public void setInput(ParamDocument input) {
		this.input = input;
	}

	public ParamDocument getOutput() {
		return output;
	}

	public void setOutput(ParamDocument output) {
		this.output = output;
	}

	public Set<ParamDocument> getRelotions() {
		return relotions;
	}

	public void setRelotions(Set<ParamDocument> relotions) {
		this.relotions = relotions;
	}

	public Boolean getNeedLogin() {
		return needLogin;
	}

	public void setNeedLogin(Boolean needLogin) {
		this.needLogin = needLogin;
	}
	
	
}
