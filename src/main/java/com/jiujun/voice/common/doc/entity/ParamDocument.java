package com.jiujun.voice.common.doc.entity;

import java.util.List;

import com.jiujun.voice.common.model.EntityModel;

/**
 * 参数信息
 * 
 * @author
 *
 */
@SuppressWarnings("serial")
public class ParamDocument extends EntityModel {

	/**
	 * 类型
	 */
	private String express;

	/**
	 * Class
	 */
	private Class<?> clazz;

	/**
	 * 字段列表
	 */
	private List<FieldDocument> fields;

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public List<FieldDocument> getFields() {
		return fields;
	}

	public void setFields(List<FieldDocument> fields) {
		this.fields = fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((express == null) ? 0 : express.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ParamDocument other = (ParamDocument) obj;
		if (clazz == null) {
			if (other.clazz != null) {
				return false;
			}
		} else if (!clazz.equals(other.clazz)) {
			return false;
		}
		if (express == null) {
			if (other.express != null) {
				return false;
			}
		} else if (!express.equals(other.express)) {
			return false;
		}
		if (fields == null) {
			if (other.fields != null) {
				return false;
			}
		} else if (!fields.equals(other.fields)) {
			return false;
		}
		return true;
	}

}
