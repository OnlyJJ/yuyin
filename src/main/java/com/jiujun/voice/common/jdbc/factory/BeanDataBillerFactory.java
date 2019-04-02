package com.jiujun.voice.common.jdbc.factory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeanUtils;

import com.jiujun.voice.common.jdbc.annotation.DBColumn;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.jdbc.annotation.DbFilting;
import com.jiujun.voice.common.jdbc.entity.BeanDataBiller;
import com.jiujun.voice.common.jdbc.exception.JdbcBuilderException;
import com.jiujun.voice.common.model.DBModel;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.magic.iface.DynamicContainer;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.common.utils.property.entity.FieldEntity;

public class BeanDataBillerFactory {

	public static final ConcurrentHashMap<Class<?>, BeanDataBiller> BEANDATABILLER_CONTAINER = new ConcurrentHashMap<Class<?>, BeanDataBiller>();

	public static DynamicContainer dataBillerContainer;
	/**
	 * 是否反驼峰表名
	 */
	private static final boolean IS_UNPARSE_TABLE = true;
	/**
	 * 是否反驼峰字段名
	 */
	private static final boolean IS_UNPARSE_FIELD = false;

	public static BeanDataBiller getBiller(Class<?> clazz) {

		if (StringUtil.isNullOrEmpty(clazz)) {
			throw new JdbcBuilderException("传入class为空");
		}
		BeanDataBiller biller = BEANDATABILLER_CONTAINER.get(clazz);
		if (!StringUtil.isNullOrEmpty(biller)) {
			return biller;
		}
		biller = createBiller(clazz);
		BEANDATABILLER_CONTAINER.put(clazz, biller);
		return biller;
	}

	private static BeanDataBiller createBiller(Class<?> clazz) {
		if (StringUtil.isNullOrEmpty(clazz)) {
			throw new JdbcBuilderException("传入class为空");
		}
		BeanDataBiller biller = new BeanDataBiller();

		biller.setOnekeyWrite(true);
		DBTable table = clazz.getAnnotation(DBTable.class);
		String tableName = "";
		if (!StringUtil.isNullOrEmpty(table)) {
			tableName = table.value();
			biller.setOnekeyWrite(table.onekeyWrite());
		}
		if (StringUtil.isNullOrEmpty(tableName)) {
			tableName = getTableName(clazz.getSimpleName());
		}
		biller.setTable(tableName);
		List<FieldEntity> fields = PropertUtil.getBeanFields(clazz);
		biller.setFields(new HashMap<DataFieldEntity, String>());
		for (FieldEntity field : fields) {
			DataFieldEntity dataField = new DataFieldEntity();
			BeanUtils.copyProperties(field, dataField);
			DbFilting filting = field.getAnnotation(DbFilting.class);
			if (filting != null) {
				dataField.setFilting(filting.seize());
				dataField.setFiltingError(filting.error());
			}
			biller.getFields().put(dataField, getColumnName(field));
		}
		return biller;
	}

	public static void accelerateEngine(Set<Class<?>> initialingClazzs)
			throws InstantiationException, IllegalAccessException {
		Set<Class<?>> dbClazzs = new HashSet<Class<?>>();
		for (Class<?> clazz : initialingClazzs) {
			if (!DBModel.class.isAssignableFrom(clazz)) {
				continue;
			}
			dbClazzs.add(clazz);
		}
		// 创建字段
		Set<String> fields = new HashSet<String>();
		for (Class<?> clazz : dbClazzs) {
			fields.add(clazz.getName());
		}
		// 对象赋值
		for (Class<?> clazz : dbClazzs) {
			BeanDataBiller biller = createBiller(clazz);
			BEANDATABILLER_CONTAINER.put(clazz, biller);
		}
	}

	private static String getTableName(String modelName) {
		try {
			if (IS_UNPARSE_TABLE) {
				return unParsParaName(modelName);
			}
			return modelName;
		} catch (Exception e) {
			PrintException.printException(e);
		}
		return null;
	}

	/**
	 * 根据字段名获取数据库列名
	 * 
	 * @param fieldName
	 * @return
	 */
	private static String getColumnName(String fieldName) {
		if (!IS_UNPARSE_FIELD) {
			return fieldName;
		}
		return unParsParaName(fieldName);
	}

	/**
	 * 驼峰式命名转下划线
	 * 
	 * @param paraName
	 * @return
	 */
	private static String unParsParaName(String paraName) {
		char[] chrs = paraName.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chrs.length; i++) {
			char chr = chrs[i];
			if (i != 0 && Character.isUpperCase(chr)) {
				sb.append("_");
			}
			sb.append(String.valueOf(chr).toLowerCase());
		}
		return sb.toString();
	}

	/**
	 * 获取模型对于数据库字段名
	 * 
	 * @param field
	 * @return
	 */
	private static String getColumnName(FieldEntity field) {
		DBColumn column = field.getSourceField().getAnnotation(DBColumn.class);
		String fieldName = field.getFieldName();
		if (StringUtil.isNullOrEmpty(column)) {
			fieldName = getColumnName(field.getFieldName());
		} else {
			fieldName = column.value();
		}
		return fieldName;
	}

	/**
	 * 首个字符串大写
	 * 
	 * @param s
	 * @return
	 */
	public static String firstUpcase(String s) {
		if (Character.isUpperCase(s.charAt(0))) {
			return s;
		}
		return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 字段属性
	 */
	@SuppressWarnings("serial")
	public static class DataFieldEntity extends FieldEntity {

		private String filting;

		private String filtingError;

		public String getFiltingError() {
			return filtingError;
		}

		public void setFiltingError(String filtingError) {
			this.filtingError = filtingError;
		}

		public String getFilting() {
			return filting;
		}

		public void setFilting(String filting) {
			this.filting = filting;
		}

	}
}
