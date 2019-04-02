package com.jiujun.voice.common.container;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程容器
 * @author Coody
 * @date 2018年10月31日
 */
@SuppressWarnings("unchecked")
public class ThreadContainer {

	private static ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<Map<String, Object>>();

	public static void clear() {
		THREAD_LOCAL.remove();
	}

	public static <T> T get(String fieldName) {
		if (THREAD_LOCAL.get() == null) {
			return null;
		}
		return (T) THREAD_LOCAL.get().get(fieldName);
	}

	public static void initThreadContainer() {
		if (THREAD_LOCAL.get() != null) {
			return;
		}
		THREAD_LOCAL.set(new HashMap<String, Object>(8));
	}

	public static void set(String fieldName, Object value) {
		initThreadContainer();
		THREAD_LOCAL.get().put(fieldName, value);
	}

	public static void remove(String fieldName) {
		if (THREAD_LOCAL.get() == null) {
			return;
		}
		THREAD_LOCAL.get().remove(fieldName);
	}

	public static boolean containsKey(String fieldName) {
		if (THREAD_LOCAL.get() == null) {
			return false;
		}
		return THREAD_LOCAL.get().containsKey(fieldName);
	}

}
