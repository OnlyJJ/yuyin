package com.jiujun.voice.common.jdbc.entity;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class SQLEntity {

	private String sql;
	
	private Object[]params;
	
	public SQLEntity(String sql,Object[] params){
		this.sql=sql;
		this.params=params;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}

	
	
}
