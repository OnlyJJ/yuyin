package com.jiujun.voice.modules.im.rongcloud.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.im.rongcloud.config.RongCloudConfig;

import io.rong.util.CodeUtil;
/**
 * 
 * @author Coody
 *
 */
public class RongCloudBuilder {

	@SuppressWarnings("unchecked")
	public static String builderData(Map<String, Object> params) throws UnsupportedEncodingException {

		StringBuilder context = new StringBuilder();
		for (String key : params.keySet()) {
			Object value = params.get(key);
			if (StringUtil.isNullOrEmpty(value)) {
				continue;
			}
			if (value.getClass().isArray()) {
				value = Arrays.asList((Object[]) value);
			}
			if (value instanceof Collection) {
				for (Object line : (Collection<Object>) value) {
					context.append(key);
					context.append("=");
					context.append(URLEncoder.encode(StringUtil.toString(line),"UTF-8"));
					context.append("&");
				}
				continue;
			}
			context.append(key);
			context.append("=");
			context.append(URLEncoder.encode(StringUtil.toString(value),"UTF-8"));
			context.append("&");
		}
		String result= context.toString();
		return result;
	}
	
	public static Map<String, String> buildHeader(){
		String nonce = String.valueOf(Math.random() * 1000000);
		String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
		StringBuilder toSign = new StringBuilder(RongCloudConfig.APP_SECRET).append(nonce).append(timestamp);
		String sign = CodeUtil.hexSHA1(toSign.toString());
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("App-Key", RongCloudConfig.APP_KEY);
		headMap.put("Nonce", nonce);
		headMap.put("Timestamp", timestamp);
		headMap.put("Signature", sign);
		headMap.put("Content-Type", "application/x-www-form-urlencoded");
		return headMap;
	}
}
