package com.jiujun.voice.common.verification;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.model.CmdModel;
import com.jiujun.voice.common.model.EntityModel;
import com.jiujun.voice.common.model.RootModel;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.common.utils.property.UnsafeUtil;
import com.jiujun.voice.common.utils.property.entity.FieldEntity;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class VerficationHandle {

	private static Map<Class<?>, Map<String, CheckEntity>> VERFICATION_CONTAINER = new ConcurrentHashMap<Class<?>, Map<String, CheckEntity>>();

	private static Map<String, CheckEntity> getVerficationInfo(Class<?> clazz) {
		if (VERFICATION_CONTAINER.containsKey(clazz)) {
			return VERFICATION_CONTAINER.get(clazz);
		}
		List<FieldEntity> entitys = PropertUtil.getBeanFields(clazz);
		Map<String, CheckEntity> checkMap = new HashMap<String, CheckEntity>(8);
		for (FieldEntity field : entitys) {
			ParamCheck check = field.getSourceField().getAnnotation(ParamCheck.class);
			if (check == null) {
				continue;
			}
			CheckEntity checkEntity = new CheckEntity();
			checkEntity.setField(field);
			checkEntity.setParamCheck(check);
			checkMap.put(field.getFieldName(), checkEntity);
			if (RootModel.class.isAssignableFrom(field.getFieldType())) {
				Map<String, CheckEntity> childCheckMap = getVerficationInfo(field.getFieldType());
				if (!StringUtil.isNullOrEmpty(childCheckMap)) {
					for (String key : childCheckMap.keySet()) {
						checkMap.put(field.getFieldName() + "." + key, childCheckMap.get(key));
					}
				}
				continue;
			}
		}
		VERFICATION_CONTAINER.put(clazz, checkMap);
		return checkMap;
	}

	public static void doVerfication(CmdModel model) throws IllegalArgumentException, IllegalAccessException {
		if (model == null) {
			return;
		}
		Map<String, CheckEntity> checkInfo = getVerficationInfo(model.getClass());
		for (String fieldName : checkInfo.keySet()) {
			CheckEntity checkEntity = checkInfo.get(fieldName);
			Object obj = UnsafeUtil.getFieldValue(model, checkEntity.getField());
			// Object obj =checkEntity.getField().getSourceField().get(model);
			String error = checkEntity.getParamCheck().errorMsg();
			// 数据可空验证
			if (!checkEntity.getParamCheck().allowNull()) {
				if (StringUtil.isNullOrEmpty(obj)) {
					if (StringUtil.isNullOrEmpty(error)) {
						error = ErrorCode.ERROR_1005.getMsg();
					}
					String errorMsg = error + ":" + fieldName;
					throw ErrorCode.ERROR_1005.builderException(error, errorMsg);

				}
			}
			if (StringUtil.isNullOrEmpty(obj)) {
				if (!StringUtil.isNullOrEmpty(checkEntity.getParamCheck().orNulls())) {
					String[] orNulls = checkEntity.getParamCheck().orNulls();
					String currentNode = getCurrentNode(fieldName);
					if (!StringUtil.isNullOrEmpty(currentNode)) {
						currentNode += ".";
					}
					for (int i = 0; i < orNulls.length; i++) {
						orNulls[i] = currentNode + orNulls[i];
					}
					List<Object> values = PropertUtil.getFieldValues(model, orNulls);
					if (StringUtil.isAllNull(values)) {
						if (StringUtil.isNullOrEmpty(error)) {
							error = ErrorCode.ERROR_1005.getMsg();
						}
						String errorMsg = error + ":" + fieldName + ":"
								+ checkEntity.getParamCheck().orNulls().toString();
						throw ErrorCode.ERROR_1005.builderException(error, errorMsg);
					}
				}
				continue;
			}
			if (StringUtil.isNullOrEmpty(checkEntity.getParamCheck().format())) {
				continue;
			}
			// 数据格式验证
			String currMatcher = null;
			for (String matcher : checkEntity.getParamCheck().format()) {
				if (StringUtil.isMatcher(obj.toString(), matcher)) {
					currMatcher = null;
					break;
				}
				currMatcher = matcher;
			}
			if (!StringUtil.isNullOrEmpty(currMatcher)) {
				if (StringUtil.isNullOrEmpty(error)) {
					error = ErrorCode.ERROR_1006.getMsg();
				}
				String errorMsg = error + ":" + fieldName + ":" + obj.toString() + ",format:(" + currMatcher + ")";
				throw ErrorCode.ERROR_1006.builderException(error, errorMsg);
			}
		}
		return;
	}

	private static String getCurrentNode(String fieldName) {
		if (!fieldName.contains(".")) {
			return "";
		}
		return fieldName.substring(0, fieldName.lastIndexOf("."));
	}

	@SuppressWarnings({ "serial" })
	private static class CheckEntity extends EntityModel {

		public CheckEntity() {
		}

		private ParamCheck paramCheck;

		private FieldEntity field;

		public ParamCheck getParamCheck() {
			return paramCheck;
		}

		public void setParamCheck(ParamCheck paramCheck) {
			this.paramCheck = paramCheck;
		}

		public FieldEntity getField() {
			return field;
		}

		public void setField(FieldEntity field) {
			this.field = field;
		}
	}

	public static void main(String[] args) throws IOException {
	}
}
