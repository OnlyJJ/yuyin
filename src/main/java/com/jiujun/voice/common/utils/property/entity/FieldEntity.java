package com.jiujun.voice.common.utils.property.entity;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.jiujun.voice.common.utils.StringUtil;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@SuppressWarnings("serial")
public class FieldEntity implements Serializable{

	private String fieldName;
	private Object fieldValue;
	/**
	 * 字段内存地址
	 */
	private Long fieldOfffset;
	private Class<?> fieldType;
	private Annotation[] fieldAnnotations;
	private Field sourceField;
	
	
	
	
	public Long getFieldOfffset() {
		return fieldOfffset;
	}
	public void setFieldOfffset(Long fieldOfffset) {
		this.fieldOfffset = fieldOfffset;
	}
	@SuppressWarnings("unchecked")
	public <T extends Annotation> T getAnnotation(Class<?> clazz){
		if(StringUtil.isNullOrEmpty(fieldAnnotations)){
			return null;
		}
		for (Annotation annotation:fieldAnnotations) {
			if(clazz.isAssignableFrom(annotation.annotationType())){
				return (T) annotation;
			}
		}
		return null;
	}
	public Field getSourceField() {
		return sourceField;
	}
	public void setSourceField(Field sourceField) {
		this.sourceField = sourceField;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	@SuppressWarnings("unchecked")
	public <T> T getFieldValue() {
		return (T) fieldValue;
	}
	public void setFieldValue(Object fieldValue) {
		this.fieldValue = fieldValue;
	}
	public Class<?> getFieldType() {
		return fieldType;
	}
	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}
	public Annotation[] getFieldAnnotations() {
		return fieldAnnotations;
	}
	public void setFieldAnnotations(Annotation[] fieldAnnotations) {
		this.fieldAnnotations = fieldAnnotations;
	}
	
	
}
