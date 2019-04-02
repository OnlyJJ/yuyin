package com.jiujun.voice.common.doc;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.cache.instance.LocalCache;
import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.container.BeanContainer;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.doc.entity.ActionDocument;
import com.jiujun.voice.common.doc.entity.CmdDocument;
import com.jiujun.voice.common.doc.entity.FieldDocument;
import com.jiujun.voice.common.doc.entity.ParamDocument;
import com.jiujun.voice.common.doc.entity.ResultCodeDocument;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.common.model.RootModel;
import com.jiujun.voice.common.utils.EncryptUtil;
import com.jiujun.voice.common.utils.FileUtils;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.common.utils.property.entity.FieldEntity;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 
 * @author Coody
 *
 */
@Component
@SuppressWarnings("unchecked")
public class DocHandle implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * 获取系统中所有的Cmd实例
	 * 
	 * @return
	 */
	public List<RootCmd> getCmdInstance() {
		String[] beans = BeanContainer.context.getBeanDefinitionNames();
		if (StringUtil.isNullOrEmpty(beans)) {
			return null;
		}
		List<RootCmd> instances = new ArrayList<RootCmd>();
		for (String bean : beans) {
			Object instance = BeanContainer.getBean(bean);
			if (bean == null) {
				continue;
			}
			if (!RootCmd.class.isAssignableFrom(instance.getClass())) {
				continue;
			}
			instances.add((RootCmd) instance);
		}
		return instances;
	}

	/**
	 * 解释一个cmd的文档
	 * 
	 * @param cmd
	 * @return
	 */
	@CacheWrite(key = CacheConstants.DOC_INFO, time = 3600 * 72, instance = LocalCache.class)
	public CmdDocument builderCmdDocument(RootCmd cmd) {
		CmdDocument cmdDocument = new CmdDocument();
		cmdDocument.setClazz(getSourceClass(cmd.getClass()));
		cmdDocument.setName(getDocName(cmdDocument.getClazz()));
		CmdOpen cmdOpen = cmdDocument.getClazz().getAnnotation(CmdOpen.class);
		if (cmdOpen == null) {
			return null;
		}
		cmdDocument.setCmd(cmdOpen.value());
		/**
		 * 加载action列表
		 */
		Map<String, Method> actionMap = cmd.getAPIMethods();
		if (StringUtil.isNullOrEmpty(actionMap)) {
			return null;
		}
		List<ActionDocument> actionDocuments = new ArrayList<ActionDocument>();
		for (String actionKey : actionMap.keySet()) {
			Method actionMethod = actionMap.get(actionKey);
			ActionDocument actionDocument = builderActionDocument(cmdDocument, actionMethod, actionKey);
			if (actionDocument == null) {
				continue;
			}
			actionDocuments.add(actionDocument);
		}
		cmdDocument.setActions(PropertUtil.doSeq(actionDocuments, "name"));
		cmdDocument.setResultCodes(builderCodeDocuments(cmdDocument.getClazz()));
		return cmdDocument;
	}

	/**
	 * 获取Cmd下所有的Action
	 * 
	 * @param cmd
	 * @return
	 */
	private ActionDocument builderActionDocument(CmdDocument cmdDocument, Method method, String action) {
		ActionDocument actionDocument = new ActionDocument();

		actionDocument.setName(getDocName(method));
		actionDocument.setAction(cmdDocument.getCmd() + "." + action);
		actionDocument.setNeedLogin(true);
		actionDocument.setMethod(method);
		LoginIgnore loginIgnore = method.getAnnotation(LoginIgnore.class);
		if (loginIgnore != null) {
			actionDocument.setNeedLogin(false);
		}
		/**
		 * 加载入参、出参、关联参数
		 */
		actionDocument.setInput(getInputDocument(method));
		actionDocument.setOutput(getOutputDocument(method));

		Set<Class<? extends RootModel>> relationSet = getRelations(method);
		if (StringUtil.isNullOrEmpty(relationSet)) {
			return actionDocument;
		}
		Set<ParamDocument> paramDocumentSet = new HashSet<ParamDocument>();
		for (Class<? extends RootModel> clazz : relationSet) {
			ParamDocument paramDocument = builderParamDocument(clazz);
			if (paramDocument == null) {
				continue;
			}
			paramDocumentSet.add(paramDocument);
		}
		actionDocument.setRelotions(paramDocumentSet);
		String fingerprint = EncryptUtil.md5(JSON.toJSONString(actionDocument));
		actionDocument.setFingerprint(actionDocument.getAction() + ":" + fingerprint);
		return actionDocument;
	}

	/**
	 * 获取一个模型的所有关联参数
	 */
	private static Set<Class<? extends RootModel>> getRelations(Class<? extends RootModel> clazz) {
		List<FieldEntity> beanEntitys = PropertUtil.getBeanFields(clazz);
		if (StringUtil.isNullOrEmpty(beanEntitys)) {
			return null;
		}
		Set<Class<? extends RootModel>> relationsClasses = new HashSet<Class<? extends RootModel>>();
		for (FieldEntity beanEntity : beanEntitys) {
			if (RootModel.class.isAssignableFrom(beanEntity.getFieldType())) {
				relationsClasses.add((Class<? extends RootModel>) beanEntity.getFieldType());
				Set<Class<? extends RootModel>> relationTemps = getRelations(
						(Class<? extends RootModel>) beanEntity.getFieldType());
				if (StringUtil.isNullOrEmpty(relationTemps)) {
					continue;
				}
				relationsClasses.addAll(relationTemps);
				continue;
			}
			if (beanEntity.getSourceField().getGenericType() instanceof ParameterizedType) {
				Set<Class<? extends RootModel>> relationTemps = getRelations(clazz,
						(ParameterizedType) beanEntity.getSourceField().getGenericType());
				if (StringUtil.isNullOrEmpty(relationTemps)) {
					continue;
				}
				relationsClasses.addAll(relationTemps);
				continue;
			}
			if (beanEntity.getSourceField().getGenericType() instanceof GenericArrayType) {
				Set<Class<? extends RootModel>> relationTemps = getRelations(clazz,
						(GenericArrayType) beanEntity.getSourceField().getGenericType());
				if (StringUtil.isNullOrEmpty(relationTemps)) {
					continue;
				}
				relationsClasses.addAll(relationTemps);
				continue;
			}
		}
		return relationsClasses;
	}

	/**
	 * 获取一个泛型的所有关联参数
	 */
	private static Set<Class<? extends RootModel>> getRelations(Class<?> clazz, ParameterizedType parameterizedType) {
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		if (StringUtil.isNullOrEmpty(typeArguments)) {
			return null;
		}
		Set<Class<? extends RootModel>> relationsClasses = new HashSet<Class<? extends RootModel>>();
		for (Type type : typeArguments) {
			Class<?> clazzClass = null;
			// 类型变量，无法实例化
			if (type instanceof TypeVariable || type instanceof WildcardType) {
				Map<String, String> map = getClassActualTypes(clazz);
				String typeName = type.getTypeName();
				while (map.containsKey(typeName)) {
					typeName = map.get(typeName);
				}
				clazzClass = getClass(typeName);
			}
			if (clazzClass == null) {
				try {
					clazzClass = (Class<?>) type;
				} catch (Exception e) {
					continue;
				}
			}
			if (RootModel.class.isAssignableFrom(clazzClass)) {
				relationsClasses.add((Class<? extends RootModel>) clazzClass);
				Set<Class<? extends RootModel>> relationTemps = getRelations((Class<? extends RootModel>) clazzClass);
				if (StringUtil.isNullOrEmpty(relationTemps)) {
					continue;
				}
				relationsClasses.addAll(relationTemps);
			}
		}
		return relationsClasses;
	}

	/**
	 * 获取一个泛型的所有关联参数
	 */
	private static Set<Class<? extends RootModel>> getRelations(Method method, ParameterizedType parameterizedType) {
		Type[] typeArguments = parameterizedType.getActualTypeArguments();
		if (StringUtil.isNullOrEmpty(typeArguments)) {
			return null;
		}
		Set<Class<? extends RootModel>> relationsClasses = new HashSet<Class<? extends RootModel>>();
		for (Type type : typeArguments) {
			Class<?> clazzClass = null;
			// 类型变量，无法实例化
			if (type instanceof TypeVariable || type instanceof WildcardType) {
				Map<String, String> map = getClassActualTypes(method.getReturnType());
				Map<String, String> methodType = getMethodReturnActualTypes(method);
				map.putAll(methodType);
				String typeName = type.getTypeName();
				while (map.containsKey(typeName)) {
					typeName = map.get(typeName);
				}
				clazzClass = getClass(typeName);
			}
			if (clazzClass == null) {
				clazzClass = (Class<?>) type;
			}
			if (RootModel.class.isAssignableFrom(clazzClass)) {
				relationsClasses.add((Class<? extends RootModel>) clazzClass);
				Set<Class<? extends RootModel>> relationTemps = getRelations((Class<? extends RootModel>) clazzClass);
				if (StringUtil.isNullOrEmpty(relationTemps)) {
					continue;
				}
				relationsClasses.addAll(relationTemps);
			}
		}
		return relationsClasses;
	}

	/**
	 * 获取一个泛型的所有关联参数
	 */
	private static Set<Class<? extends RootModel>> getRelations(Class<?> clazz, GenericArrayType genericArrayType) {
		Type componentType = genericArrayType.getGenericComponentType();
		while (componentType instanceof GenericArrayType) {
			componentType = ((GenericArrayType) componentType).getGenericComponentType();
		}
		return getRelations(clazz, (ParameterizedType) componentType);
	}

	/**
	 * 取一个方法入参和出参的关联参数
	 * 
	 * @param method
	 * @return
	 */
	private static Set<Class<? extends RootModel>> getRelations(Method method) {
		Class<?>[] inputClasses = method.getParameterTypes();
		Set<Class<? extends RootModel>> relationsClasses = new HashSet<Class<? extends RootModel>>();
		if (!StringUtil.isNullOrEmpty(inputClasses)) {
			if (RootModel.class.isAssignableFrom(inputClasses[0])) {
				Set<Class<? extends RootModel>> relationTemps = getRelations((Class<RootModel>) inputClasses[0]);
				if (!StringUtil.isNullOrEmpty(relationTemps)) {
					relationsClasses.addAll(relationTemps);
				}
			}
		}
		if (method.getGenericReturnType() instanceof ParameterizedType) {
			Class<?> outputClass = method.getReturnType();
			if (RootModel.class.isAssignableFrom(outputClass)) {
				Set<Class<? extends RootModel>> relationTemps = getRelations(method,
						(ParameterizedType) method.getGenericReturnType());
				if (!StringUtil.isNullOrEmpty(relationTemps)) {
					relationsClasses.addAll(relationTemps);
				}
			}
		}

		Class<?> outputClass = method.getReturnType();
		if (RootModel.class.isAssignableFrom(outputClass)) {
			Set<Class<? extends RootModel>> relationTemps = getRelations((Class<RootModel>) outputClass);
			if (!StringUtil.isNullOrEmpty(relationTemps)) {
				relationsClasses.addAll(relationTemps);
			}
		}

		return relationsClasses;
	}

	/**
	 * 获取方法的出参
	 */
	private static ParamDocument getOutputDocument(Method method) {
		if (!BaseRespVO.class.isAssignableFrom(method.getReturnType())) {
			return null;
		}
		return builderParamDocument(method);
	}

	/**
	 * 获取方法的入参
	 */
	private static ParamDocument getInputDocument(Method method) {
		Class<?>[] clazzs = method.getParameterTypes();
		if (StringUtil.isNullOrEmpty(clazzs)) {
			return null;
		}
		if (!BaseReqVO.class.isAssignableFrom(clazzs[0])) {
			return null;
		}
		Class<? extends RootModel> clazz = (Class<? extends RootModel>) clazzs[0];
		return builderParamDocument(clazz);
	}

	/**
	 * 构建一个模型的字段列表
	 * 
	 * @param method
	 * @return
	 */
	private static ParamDocument builderParamDocument(Class<? extends RootModel> clazz) {
		if (Header.class.isAssignableFrom(clazz)) {
			return null;
		}
		List<FieldEntity> beanEntitys = PropertUtil.getBeanFields(clazz);
		if (StringUtil.isNullOrEmpty(beanEntitys)) {
			return null;
		}
		ParamDocument paramDocument = new ParamDocument();
		paramDocument.setClazz(clazz);
		paramDocument.setExpress(clazz.getSimpleName());
		List<FieldDocument> fieldDocuments = new ArrayList<FieldDocument>();
		for (FieldEntity entity : beanEntitys) {
			FieldDocument fieldDocument = builderFieldDocument(clazz, entity.getSourceField());
			if (fieldDocument == null) {
				continue;
			}
			fieldDocuments.add(fieldDocument);
		}
		paramDocument.setFields(fieldDocuments);
		return paramDocument;
	}

	/**
	 * 构建一个模型的字段列表
	 * 
	 * @param method
	 * @return
	 */
	private static ParamDocument builderParamDocument(Method method) {
		if (Header.class.isAssignableFrom(method.getReturnType())) {
			return null;
		}
		List<FieldEntity> beanEntitys = PropertUtil.getBeanFields(method.getReturnType());
		if (StringUtil.isNullOrEmpty(beanEntitys)) {
			return null;
		}
		ParamDocument paramDocument = new ParamDocument();
		paramDocument.setClazz(method.getReturnType());
		paramDocument.setExpress(formatExpress(method.getGenericReturnType().getTypeName()));
		List<FieldDocument> fieldDocuments = new ArrayList<FieldDocument>();
		for (FieldEntity entity : beanEntitys) {
			FieldDocument fieldDocument = builderFieldDocument(method, entity.getSourceField());
			if (fieldDocument == null) {
				continue;
			}
			fieldDocuments.add(fieldDocument);
		}
		paramDocument.setFields(fieldDocuments);
		return paramDocument;
	}

	/**
	 * 构建一个字段详情
	 */
	private static FieldDocument builderFieldDocument(Class<?> clazz, Field field) {
		if (Header.class.isAssignableFrom(field.getType())) {
			return null;
		}
		FieldDocument fieldDocument = new FieldDocument();
		fieldDocument.setClazz(field.getType());
		fieldDocument.setExpress(getTypeName(clazz, field));
		ParamCheck paramCheck = field.getAnnotation(ParamCheck.class);
		fieldDocument.setAllowNull(true);
		if (paramCheck != null) {
			fieldDocument.setAllowNull(paramCheck.allowNull());
			fieldDocument.setFormat(StringUtil.collectionMosaic(paramCheck.format(), " 或 "));
		}
		fieldDocument.setName(field.getName());
		fieldDocument.setRemark(getDocName(field));
		return fieldDocument;
	}

	/**
	 * 构建一个字段详情
	 */
	private static FieldDocument builderFieldDocument(Method method, Field field) {
		if (Header.class.isAssignableFrom(field.getType())) {
			return null;
		}
		FieldDocument fieldDocument = new FieldDocument();
		fieldDocument.setClazz(field.getType());
		fieldDocument.setExpress(getTypeName(method, field));
		ParamCheck paramCheck = field.getAnnotation(ParamCheck.class);
		fieldDocument.setAllowNull(true);
		if (paramCheck != null) {
			fieldDocument.setAllowNull(paramCheck.allowNull());
			fieldDocument.setFormat(StringUtil.collectionMosaic(paramCheck.format(), " 或 "));
		}
		fieldDocument.setName(field.getName());
		fieldDocument.setRemark(getDocName(field));
		return fieldDocument;
	}

	private static String getTypeName(Method method, Field field) {
		Type type = field.getGenericType();
		String express = type.getTypeName();
		if (!(type instanceof ParameterizedType || type instanceof GenericArrayType)) {
			return formatExpress(express);
		}
		Map<String, String> tableMap = getClassActualTypes(method.getReturnType());
		Map<String, String> methodMap = getMethodReturnActualTypes(method);
		tableMap.putAll(methodMap);
		if (StringUtil.isNullOrEmpty(tableMap)) {
			return formatExpress(express);
		}
		express = builderTypeName(express, tableMap);
		return formatExpress(express);
	}

	private static String getTypeName(Class<?> clazz, Field field) {
		Type type = field.getGenericType();
		String express = type.getTypeName();
		if (!(type instanceof ParameterizedType || type instanceof GenericArrayType)) {
			return formatExpress(express);
		}
		Map<String, String> tableMap = getClassActualTypes(clazz);
		if (StringUtil.isNullOrEmpty(tableMap)) {
			return formatExpress(express);
		}
		express = builderTypeName(express, tableMap);
		return formatExpress(express);
	}

	/**
	 * 获取一个Bean的原始类
	 * 
	 * @param clazz
	 * @return
	 */
	private static Class<?> getSourceClass(Class<?> clazz) {
		while (clazz != null && clazz.getName().contains("$")) {
			clazz = clazz.getSuperclass();
		}
		return clazz;
	}

	/**
	 * 获取文档名
	 * 
	 * @param field
	 * @return
	 */
	private static String getDocName(Field field) {
		DocFlag doc = field.getAnnotation(DocFlag.class);
		if (doc == null) {
			return field.getName();
		}
		return doc.value();
	}

	/**
	 * 获取文档名
	 * 
	 * @param method
	 * @return
	 */
	private static String getDocName(Method method) {
		DocFlag doc = method.getAnnotation(DocFlag.class);
		if (doc == null) {
			return method.getName();
		}
		return doc.value();
	}

	/**
	 * 获取文档名
	 * 
	 * @param clazz
	 * @return
	 */
	private static String getDocName(Class<?> clazz) {
		DocFlag doc = clazz.getAnnotation(DocFlag.class);
		if (doc == null) {
			return clazz.getSimpleName();
		}
		return doc.value();
	}

	private static String formatExpress(String express) {
		StringBuilder builder = new StringBuilder();

		String[] section = express.split("<");
		for (String temp : section) {
			if (temp.contains(",")) {
				String[] sample = temp.split(",");
				for (String attr : sample) {
					String[] piece = attr.split("[.]");
					builder.append(piece[piece.length - 1] + ",");
				}
				builder.deleteCharAt(builder.length() - 1).append("<");
				continue;
			}
			String[] sample = temp.split("[.]");
			builder.append(sample[sample.length - 1] + "<");
		}
		return builder.deleteCharAt(builder.length() - 1).toString();
	}

	private static List<ResultCodeDocument> builderCodeDocuments(Class<?> clazz) {
		List<ResultCodeDocument> codeDocuments = new ArrayList<ResultCodeDocument>();
		ResultCodeDocument codeDocument = new ResultCodeDocument();
		codeDocument.setCode(ErrorCode.SUCCESS_0.getCode());
		codeDocument.setMsg(ErrorCode.SUCCESS_0.getMsg());
		codeDocuments.add(codeDocument);
		try {
			InputStream iStream = clazz.getResourceAsStream(clazz.getSimpleName() + ".class");
			String text = FileUtils.inputStream2String(iStream, "UTF-8");
			List<String> errors = StringUtil.doMatcher(text, "ERROR_\\d{1,6}");
			if (StringUtil.isNullOrEmpty(errors)) {
				return null;
			}
			ErrorCode[] enumConstants = ErrorCode.class.getEnumConstants();
			if (StringUtil.isNullOrEmpty(enumConstants)) {
				return null;
			}
			for (ErrorCode error : enumConstants) {
				if (!errors.contains(error.name())) {
					continue;
				}
				codeDocument = new ResultCodeDocument();
				codeDocument.setCode(error.getCode());
				codeDocument.setMsg(error.getMsg());
				codeDocuments.add(codeDocument);
			}
			return codeDocuments;
		} catch (Exception e) {
			LogUtil.logger.error(clazz.getName() + ">>交易编码读取失败");
			PrintException.printException(e);
		}
		return codeDocuments;
	}

	private static String builderKeyName(String attr, Map<String, String> map) {
		attr = attr.replace(" ", "").trim();
		String keyName = attr.replace(">", "");
		StringBuilder realName = new StringBuilder(attr);
		if (!map.containsKey(keyName)) {
			return attr;
		}
		realName = new StringBuilder(map.get(keyName.toString()));
		for (int i = 0; i < attr.length() - keyName.length(); i++) {
			realName.append(">");
		}
		return realName.toString();
	}

	private static String builderTypeName(String express, Map<String, String> map) {
		String[] sections = express.split("<");
		StringBuilder builder = new StringBuilder();
		for (String temp : sections) {
			if (temp.contains(",")) {
				String[] sample = temp.split(",");
				for (String attr : sample) {
					builder.append(builderKeyName(attr, map)).append(",");
				}
				builder.deleteCharAt(builder.length() - 1).append("<");
				continue;
			}
			builder.append(builderKeyName(temp, map) + "<");
		}
		String result = builder.deleteCharAt(builder.length() - 1).toString();
		return result;
	}

	private static Map<String, String> getClassActualTypes(Class<?> clazz) {
		try {
			Type currentType = clazz.getGenericSuperclass();
			if (currentType instanceof ParameterizedType) {
				ParameterizedType currentParameterizedType = (ParameterizedType) currentType;
				Type[] currentActualTypes = currentParameterizedType.getActualTypeArguments();
				if (StringUtil.isNullOrEmpty(currentActualTypes)) {
					return new HashMap<String, String>(2);
				}
				Type superType = clazz.getSuperclass().getGenericSuperclass();
				if (!(superType instanceof ParameterizedType)) {
					return new HashMap<String, String>(2);
				}
				ParameterizedType superParameterizedType = (ParameterizedType) superType;
				Type[] superActualTypes = superParameterizedType.getActualTypeArguments();
				if (StringUtil.isNullOrEmpty(superActualTypes)) {
					return new HashMap<String, String>(2);
				}
				if (superActualTypes.length != currentActualTypes.length) {
					return new HashMap<String, String>(2);
				}
				Map<String, String> result = new HashMap<String, String>(superActualTypes.length);
				for (int i = 0; i < superActualTypes.length; i++) {
					result.put(superActualTypes[i].getTypeName(), currentActualTypes[i].getTypeName());
				}
				return result;
			}
			return new HashMap<String, String>(2);
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>(2);
		}
	}

	private static Map<String, String> getMethodReturnActualTypes(Method method) {
		try {
			if (!(method.getGenericReturnType() instanceof ParameterizedType)) {
				return new HashMap<String, String>(2);
			}
			String baseType = method.getReturnType().getClass().getTypeName();
			String genericType = method.getGenericReturnType().getTypeName();
			List<String> baseParameterTypes = getParameterTypeKeys(baseType);
			if (StringUtil.isNullOrEmpty(baseParameterTypes)) {
				if (!StringUtil.isNullOrEmpty(method.getReturnType().getTypeParameters())) {
					for (TypeVariable<?> typeVariable : method.getReturnType().getTypeParameters()) {
						baseParameterTypes.add(typeVariable.getName());
					}
				}
			}
			List<String> genericParameterTypes = getParameterTypeKeys(genericType);
			if (StringUtil.hasNull(baseParameterTypes, genericParameterTypes)) {
				return new HashMap<String, String>(2);
			}
			Map<String, String> typeMapping = new HashMap<String, String>(baseParameterTypes.size());
			for (int i = 0; i < baseParameterTypes.size(); i++) {
				typeMapping.put(baseParameterTypes.get(i), genericParameterTypes.get(i));
			}
			return typeMapping;
		} catch (Exception e) {
			e.printStackTrace();
			return new HashMap<String, String>(2);
		}
	}

	private static List<String> getParameterTypeKeys(String express) {
		String[] sections = express.split("<");
		List<String> parameterTypes = new ArrayList<String>();
		for (int i = 1; i < sections.length; i++) {
			String section = sections[i];
			section = section.replace(">", "").trim();
			if (!section.contains(",")) {
				parameterTypes.add(section);
				continue;
			}
			String[] attrs = section.split(",");
			for (String temp : attrs) {
				parameterTypes.add(temp.trim());
			}
		}
		return parameterTypes;
	}

	private static Class<?> getClass(String className) {
		try {
			return Class.forName(className);
		} catch (Exception e) {
		}
		return null;
	}

}
