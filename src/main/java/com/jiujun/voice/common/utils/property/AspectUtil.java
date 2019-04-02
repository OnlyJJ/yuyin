package com.jiujun.voice.common.utils.property;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.utils.EncryptUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.entity.FieldEntity;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class AspectUtil {


	
	
	public static String getMethodKey(Class<?> clazz, Method method) {
		StringBuilder sb = new StringBuilder();
		sb.append(clazz.getName()).append(".").append(method.getName());
		Class<?>[] paraTypes = method.getParameterTypes();
		sb.append("(");
		if (paraTypes != null) {
			for (int i = 0; i < paraTypes.length; i++) {
				sb.append(paraTypes[i].getName());
				if (i < paraTypes.length - 1) {
					sb.append(",");
				}
			}
		}
		sb.append(")");
		return sb.toString();
	}

	public static String getFieldKey(Method method, Object[] paras, String[] fields)  {
		if (StringUtil.isNullOrEmpty(fields)) {
			return "";
		}
		List<Object> fieldValues = new ArrayList<Object>();
		for (String field : fields) {
			Object paraValue = getMethodPara(method, field, paras);
			if (StringUtil.isNullOrEmpty(paraValue)) {
				continue;
			}
			fieldValues.add(paraValue);
		}
		if (StringUtil.isNullOrEmpty(fieldValues)) {
			return "";
		}
		if(needEncode(fieldValues)){
			String json = JSON.toJSONString(fieldValues);
			return EncryptUtil.md5(json);
		}
		String key=StringUtil.collectionMosaic(fieldValues, "_");
		if(key.length()>48){
			return EncryptUtil.md5(key);
		}
		return key;
	}

	private static boolean needEncode(List<Object> fieldValues) {
		if (fieldValues.size() > 3) {
			return true;
		}
		for (Object value : fieldValues) {
			if (!isGeneralType(value.getClass())) {
				return true;
			}
		}
		return false;
	}

	private static Class<?>[] generalType = { Integer.class, String.class, Short.class, Double.class, Float.class };

	private static boolean isGeneralType(Class<?> clazz) {
		for (Class<?> type : generalType) {
			if (type.isAssignableFrom(clazz)) {
				return true;
			}
		}
		return false;
	}

	public static String getFieldKey(Method method, Object[] paras){
		List<String> fields = PropertUtil.getMethodParaNames(method);
		if (StringUtil.isNullOrEmpty(fields)) {
			return "";
		}
		return getFieldKey(method, paras, fields.toArray(new String[] {}));
	}
	
	public static Object getMethodPara(Method method, String fieldName, Object[] args) {
		List<FieldEntity> beanEntitys = PropertUtil.getMethodParas(method);
		if (StringUtil.isNullOrEmpty(beanEntitys)) {
			return "";
		}
		String[] fields = fieldName.split("\\.");
		FieldEntity entity = (FieldEntity) PropertUtil.getByList(beanEntitys, "fieldName", fields[0]);
		if (StringUtil.isNullOrEmpty(entity)) {
			return "";
		}
		Object para = args[beanEntitys.indexOf(entity)];
		if (fields.length > 1 && para != null) {
			for (int i = 1; i < fields.length; i++) {
				para = PropertUtil.getFieldValue(para, fields[i]);
			}
		}
		return para;
	}
}
