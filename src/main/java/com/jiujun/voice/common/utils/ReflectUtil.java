package com.jiujun.voice.common.utils;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.esotericsoftware.reflectasm.MethodAccess;

public class ReflectUtil {

	
	
	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Object target, MethodAccess methodAccess, Integer methodIndex, Object... params) {
		return (T) methodAccess.invoke(target, methodIndex, params);
	}

	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Object target, MethodAccess methodAccess, String methodName, Class<?>[] paramTypes,
			Object... params) {
		return (T) methodAccess.invoke(target, methodName, params);
	}
	
	public static void setField(Object target, FieldAccess fieldAccess, String fieldName,Object fieldValue) {
		fieldAccess.set(target, fieldName, fieldValue);
	}
	
	public static void setField(Object target, FieldAccess fieldAccess, Integer fieldIndex,Object fieldValue) {
		fieldAccess.set(target, fieldIndex, fieldValue);
	}

	
}
