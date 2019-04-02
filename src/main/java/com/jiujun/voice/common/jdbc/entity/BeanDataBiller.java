package com.jiujun.voice.common.jdbc.entity;

import java.util.Map;

import com.jiujun.voice.common.jdbc.factory.BeanDataBillerFactory.DataFieldEntity;
import com.jiujun.voice.common.model.EntityModel;

@SuppressWarnings("serial")
public class BeanDataBiller extends EntityModel {

	/**
	 * 表名
	 */
	private String table;
	/**
	 * 是否可以通过唯一主键保存
	 */
	private Boolean onekeyWrite;

	/**
	 * 字段列表  key代表字段  value代表数据库列名
	 */
	private Map<DataFieldEntity, String> fields;
	
	/**
	 * 是否需要过滤关键词
	 * @return
	 */
	private String filting;
	
	
	


	public String getFilting() {
		return filting;
	}

	public void setFilting(String filting) {
		this.filting = filting;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Boolean getOnekeyWrite() {
		return onekeyWrite;
	}

	public void setOnekeyWrite(Boolean onekeyWrite) {
		this.onekeyWrite = onekeyWrite;
	}

	public Map<DataFieldEntity, String> getFields() {
		return fields;
	}

	public void setFields(Map<DataFieldEntity, String> fields) {
		this.fields = fields;
	}

	
}
