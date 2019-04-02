package com.jiujun.voice.common.jdbc.constant;

/**
 * 数据源枚举
 * @author Coody
 * 本类内容需与applicationContext-db.xml所配置的数据源对应
 */
public class DataSourceConstant {

	/**
	 * 主数据源
	 */
	public static final String CORE="dataSource-core";
	
	/**
	 * 从库A
	 */
	public static final String SLAVE_A="dataSource-slaveA";
}
