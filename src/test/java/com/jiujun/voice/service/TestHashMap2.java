package com.jiujun.voice.service;

import java.util.HashMap;
import java.util.Map;

import com.jiujun.voice.common.utils.StringUtil;

public class TestHashMap2<K,V> {

	public static void main(String[] args) {
		Map<String, String> testMap=new HashMap<String, String>();
		
		for(int i=0;i<100;i++){
			testMap.put(StringUtil.getRanDom(1000, 99999999).toString(), StringUtil.getRanDom(1000, 99999999).toString());
		}
		testMap.put(StringUtil.getRanDom(1000, 99999999).toString(), StringUtil.getRanDom(1000, 99999999).toString());
	}
}
