package com.jiujun.voice.modules.apps.dic.domain;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_dic_info")
public class DicInfo extends DBModel{

	
	private String fieldName;
	private String fieldValue;
	private String remark;

	
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	

}
