package com.jiujun.voice.modules.apps.user.userid.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.utils.StringUtil;
/**
 * 
 * @author Coody
 *
 */
@Component
public class UserIdPrettyHandle {

	public  String getPrettyFormat(Integer prettyCode) {

		if (StringUtil.isNullOrEmpty(prettyCode)) {
			return "";
		}
		// 判断是否是6A
		String[] attrs = StringUtil.splitString(prettyCode.toString());
		Double firstAttr = StringUtil.toDouble(attrs[0]);
		if (prettyCode / firstAttr == 111111d) {
			return "AAAAAA";
		}
		// 判断是否6连
		if (prettyCode == 123456 || prettyCode == 654321) {
			return "ABCDEF";
		}
		// 判断是否5A
		Map<String, Integer> attrMap = getAppearNum(attrs);
		for (String attr : attrMap.keySet()) {
			if (attrMap.get(attr) < 5) {
				continue;
			}
			String tag = attr + attr + attr + attr + attr;
			if (prettyCode.toString().contains(tag)) {
				return "AAAAA";
			}
		}
		// 判断是否5连
		List<String> continuitys = getContinuity(prettyCode, 5);
		if (!StringUtil.isNullOrEmpty(continuitys)) {
			for (String continuity : continuitys) {
				if (isContinuous(continuity)) {
					return "ABCDE";
				}
			}
		}
		// 判断1314
		if (prettyCode.toString().contains("1314")) {
			return "1314";
		}
		// 判断是否4A
		for (String attr : attrMap.keySet()) {
			if (attrMap.get(attr) < 4) {
				continue;
			}
			String tag = attr + attr + attr + attr;
			if (prettyCode.toString().contains(tag)) {
				return "AAAA";
			}
		}
		// 判断是否4连
		continuitys = getContinuity(prettyCode, 4);
		if (!StringUtil.isNullOrEmpty(continuitys)) {
			for (String continuity : continuitys) {
				if (isContinuous(continuity)) {
					return "ABCD";
				}
			}
		}
		// 判断520
		if (prettyCode.toString().contains("520")) {
			return "520";
		}
		// 判断是否3A
		for (String attr : attrMap.keySet()) {
			if (attrMap.get(attr) < 3) {
				continue;
			}
			String tag = attr + attr + attr ;
			if (prettyCode.toString().contains(tag)) {
				return "AAA";
			}
		}
		// 判断是否3连
		continuitys = getContinuity(prettyCode, 3);
		if (!StringUtil.isNullOrEmpty(continuitys)) {
			for (String continuity : continuitys) {
				if (isContinuous(continuity)) {
					return "ABC";
				}
			}
		}
		// 判断520
		if (prettyCode.toString().contains("168")) {
			return "168";
		}
		return "";
	}

	/**
	 * 获取每个字符出现频次
	 * 
	 * @return
	 */
	private  Map<String, Integer> getAppearNum(String[] attrs) {
		Map<String, Integer> attrMap = new HashMap<>();
		for (String attr : attrs) {
			if (attrMap.containsKey(attr)) {
				attrMap.put(attr, attrMap.get(attr) + 1);
				continue;
			}
			attrMap.put(attr, 1);
		}
		return attrMap;
	}

	/**
	 * 取连续个数所有值
	 */
	private  List<String> getContinuity(Integer prettyCode, Integer num) {
		String[] attrs = StringUtil.splitString(prettyCode.toString());
		if (attrs.length < num) {
			return null;
		}
		if (attrs.length == num) {
			List<String> continuitys = new ArrayList<>();
			continuitys.add(prettyCode.toString());
			return continuitys;
		}
		List<String> continuitys = new ArrayList<>();
		for (int i = 0; i < attrs.length - num + 1; i++) {
			StringBuilder line = new StringBuilder("");
			for (int m = 0; m < num; m++) {
				line.append(attrs[i + m]);
			}
			continuitys.add(line.toString());
		}
		return continuitys;

	}

	/**
	 * 判断是否是顺子
	 * 
	 * @param attrs
	 * @return
	 */
	private  boolean isContinuous(String prettyCode) {
		if(prettyCode.length()<2) {
			return false;
		}
		String[] attrs = StringUtil.splitString(prettyCode);
		Integer differ = null;
		for (int i = 0; i < attrs.length; i++) {
			if (i == attrs.length - 1) {
				continue;
			}
			Integer current = Integer.valueOf(attrs[i]);
			Integer next = Integer.valueOf(attrs[i + 1]);
			if (differ == null) {
				differ = next - current;
				continue;
			}
			if (next - current != differ.intValue()) {
				return false;
			}
		}
		if (Math.abs(differ) != 1) {
			return false;
		}
		return true;
	}

}
