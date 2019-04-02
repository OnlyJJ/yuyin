package com.jiujun.voice.common.utils.property.entity;

import java.util.ArrayList;
import java.util.List;

import com.jiujun.voice.common.model.EntityModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class TypeEntity extends EntityModel {

	/**
	 * 当前类型
	 */
	private Class<?> type;

	/**
	 * 实际类型
	 */
	private List<TypeEntity> genericTypes;

	/**
	 * 表达式
	 */
	private String express;

	/**
	 * 短表达式
	 */
	private String shortExpress;
	
	/**
	 * 实现类型
	 */
	private Class<?> typeImpled;

	/**
	 * 是否是数组
	 */
	private Boolean isArray = false;
	
	/**
	 * 受影响的类型
	 * @return
	 */
	private List<Class<?>> relations=new ArrayList<Class<?>>();
	
	
	
	
	
	public List<Class<?>> getRelations() {
		return relations;
	}

	public void setRelations(List<Class<?>> relations) {
		this.relations = relations;
	}

	public Boolean getIsArray() {
		return isArray;
	}

	public void setIsArray(Boolean isArray) {
		this.isArray = isArray;
	}

	public String getShortExpress() {
		return shortExpress;
	}

	public void setShortExpress(String shortExpress) {
		this.shortExpress = shortExpress;
	}

	public Class<?> getTypeImpled() {
		return typeImpled;
	}

	public void setTypeImpled(Class<?> typeImpled) {
		this.typeImpled = typeImpled;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public List<TypeEntity> getGenericTypes() {
		return genericTypes;
	}

	public void setGenericTypes(List<TypeEntity> genericTypes) {
		this.genericTypes = genericTypes;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		// 构造短表达式
		this.shortExpress =buildTypeExpress(express);
		this.express = express;
	}

	public static void main(String[] args) {
		String temp = "java.util.Map<java.util.Map<java.lang.String, ? extends com.jiujun.voice.apps.user.domain.UserInfo>[], ? extends com.jiujun.voice.apps.user.domain.UserInfo>[]";
		System.out.println(buildTypeExpress(temp));
	}

	public static String buildTypeExpress(String express) {
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
}
