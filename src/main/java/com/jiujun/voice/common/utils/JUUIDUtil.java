package com.jiujun.voice.common.utils;

import java.util.UUID;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
public class JUUIDUtil {

	public synchronized static String createUuid() {
			String str = UUID.randomUUID().toString().replace("-", "");
			return str;
	}

	public static void main(String[] args) {
		System.out.println(createUuid());
	}
}
