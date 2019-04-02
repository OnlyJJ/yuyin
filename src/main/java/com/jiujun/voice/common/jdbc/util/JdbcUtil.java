package com.jiujun.voice.common.jdbc.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.exception.VoiceException;
import com.jiujun.voice.common.filting.FiltingProcess;
import com.jiujun.voice.common.jdbc.entity.BeanDataBiller;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.jdbc.factory.BeanDataBillerFactory;
import com.jiujun.voice.common.jdbc.factory.BeanDataBillerFactory.DataFieldEntity;
import com.jiujun.voice.common.model.DBModel;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.common.utils.property.UnsafeUtil;
import com.jiujun.voice.common.utils.property.entity.FieldEntity;

/**
 * 
 * @author Coody
 * @date 2018年11月14日
 */
public class JdbcUtil {



	/**
	 * List<map>转models
	 * 
	 * @param obj
	 * @return
	 */
	public static <T> List<T> buildModels(Class<? extends DBModel> clazz, List<Map<String, Object>> maps) {
		try {
			if (StringUtil.isNullOrEmpty(maps)) {
				return null;
			}
			BeanDataBiller beanDataBiller = BeanDataBillerFactory.getBiller(clazz);
			if (StringUtil.isNullOrEmpty(beanDataBiller.getFields())) {
				return null;
			}
			List<T> results = new ArrayList<T>();
			for (Map<String, Object> map : maps) {
				if (StringUtil.isNullOrEmpty(map)) {
					continue;
				}
				//T model = (T) clazz.newInstance();
				T model =  UnsafeUtil.createInstance(clazz);
				for (FieldEntity field : beanDataBiller.getFields().keySet()) {
					String columnName = beanDataBiller.getFields().get(field);
					if (!map.containsKey(columnName)) {
						continue;
					}
					Object value= PropertUtil.parseValue(map.get(columnName), field.getFieldType());
					UnsafeUtil.setFieldValue(model, field, value);
					//field.getSourceField().set(model, value);
				}
				results.add(model);
			}
			return results;
		} catch (Exception e) {
			PrintException.printException(e);
			return null;
		}
	}

	/**
	 * 解析分页条件
	 * 
	 * @param pager
	 * @return
	 */
	public static String buildPagerSQL(Pager pager) {
		// 封装分页条件
		if (StringUtil.isNullOrEmpty(pager.getPageNo())) {
			pager.setPageNo(1);
		}
		if (StringUtil.isNullOrEmpty(pager.getPageSize())) {
			pager.setPageSize(10);
		}
		Integer startRows = (pager.getPageNo() - 1) * pager.getPageSize();
		return MessageFormat.format(" limit {0},{1} ", String.valueOf(startRows), String.valueOf(pager.getPageSize()));
	}

	public static String buildBeanSetSql(BeanDataBiller beanDataBiller, DBModel model, List<Object> params) {
		return buildBeanSetSql(beanDataBiller, model, params, new String[]{});
	}
	public static String buildBeanSetSql(BeanDataBiller beanDataBiller, DBModel model, List<Object> params,
			String... addFields) {

		List<String> addFieldList = new ArrayList<String>();
		if (!StringUtil.isNullOrEmpty(addFields)) {
			addFieldList = Arrays.asList(addFields);
		}
		StringBuilder sqlBuilder = new StringBuilder();
		for (DataFieldEntity field : beanDataBiller.getFields().keySet()) {
			try {
				String columnName = beanDataBiller.getFields().get(field);
				if (StringUtil.isNullOrEmpty(columnName)) {
					continue;
				}
				Object fieldValue = UnsafeUtil.getFieldValue(model, field);
				//Object fieldValue =field.getSourceField().get(model);
				if (fieldValue == null) {
					continue;
				}
				if(String.class.isAssignableFrom(field.getFieldType())){
					if(field.getFilting()!=null){
						if(FiltingProcess.getFilting((String)fieldValue)!=null){
							throw new CmdException(ErrorCode.ERROR_1056.getCode(),field.getFiltingError());
						}
					}
				}
				params.add(fieldValue);
				if (addFieldList.contains(columnName)) {
					sqlBuilder.append(columnName).append("=").append( "`").append(columnName).append( "`").append("+").append("?").append(",");
					continue;
				}
				sqlBuilder.append( "`").append(columnName).append( "`").append("=?").append(",");
				continue;
			} catch (VoiceException e) {
				throw e;
			}catch (Exception e) {
				PrintException.printException(e);
			}
		}
		sqlBuilder = new StringBuilder(sqlBuilder.toString().substring(0, sqlBuilder.toString().length() - 1));
		return sqlBuilder.toString();
	}
}
