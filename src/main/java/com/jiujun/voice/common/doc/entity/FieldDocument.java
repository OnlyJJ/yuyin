package com.jiujun.voice.common.doc.entity;

import com.jiujun.voice.common.model.EntityModel;

/**
 * 字段信息
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class FieldDocument extends EntityModel{

	/**
	 * 名字
	 */
	private String name;
	/**
	 * 类型
	 */
	private String express;
	/**
	 * 格式
	 */
	private String format;
	
	/**
	 * Class
	 */
	private Class<?> clazz;
	
	/**
	 * 是否可空
	 */
	private Boolean allowNull;
	
	/**
	 * 描述
	 */
	private String remark;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Boolean getAllowNull() {
		return allowNull;
	}

	public void setAllowNull(Boolean allowNull) {
		this.allowNull = allowNull;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
