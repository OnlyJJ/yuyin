package com.jiujun.voice.common.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.common.utils.property.entity.FieldEntity;

/**
 * 全局模型超类
 * @author Coody
 */
@SuppressWarnings("serial")
public class RootModel implements Serializable {
	

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	/**
	 * 初始化本模型
	 * 
	 * @param cover 是否覆盖原有值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T initModel(Boolean cover) {
		List<FieldEntity> fields = PropertUtil.getBeanFields(this);
		if (StringUtil.isNullOrEmpty(fields)) {
			return (T) this;
		}
		for (FieldEntity entity : fields) {
			Object value = entity.getFieldValue();
			if (!StringUtil.isNullOrEmpty(value)) {
				if (!cover) {
					continue;
				}
			}
			try {
				if(Header.class.isAssignableFrom(entity.getFieldType())) {
					continue;
				}
				if(Header.class.isAssignableFrom(this.getClass())) {
					if("cmd".equals(entity.getFieldName())) {
						continue;
					}
					if("action".equals(entity.getFieldName())) {
						continue;
					}
					if("ipAddress".equals(entity.getFieldName())) {
						continue;
					}
				}
				if (Number.class.isAssignableFrom(entity.getFieldType())) {
					PropertUtil.setFieldValue(this, entity.getSourceField(), 0);
					continue;
				}
				if (String.class.isAssignableFrom(entity.getFieldType())) {
					String temp = "";
					DocFlag flag = entity.getSourceField().getAnnotation(DocFlag.class);
					if (flag != null) {
						temp = flag.value();
					}
					PropertUtil.setFieldValue(this, entity.getSourceField(), temp);
					continue;
				}
				if (Date.class.isAssignableFrom(entity.getFieldType())) {
					PropertUtil.setFieldValue(this, entity.getSourceField(), new Date());
				}
				if (RootModel.class.isAssignableFrom(entity.getFieldType())) {
					PropertUtil.setFieldValue(this, entity.getSourceField(),
							((RootModel) entity.getFieldType().newInstance()).initModel(cover));
				}
				if(byte[].class.isAssignableFrom(entity.getFieldType())){
					PropertUtil.setFieldValue(this, entity.getSourceField(),new byte[0]);
				}
			} catch (Exception e) {
				PrintException.printException(e);
			}
		}
		return (T) this;
	}

	public <T> T initModel() {
		return initModel(false);
	}
}
