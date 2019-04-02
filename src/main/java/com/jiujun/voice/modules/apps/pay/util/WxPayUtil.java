package com.jiujun.voice.modules.apps.pay.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import com.jiujun.voice.common.utils.EncryptUtil;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings({ "unchecked" })
public class WxPayUtil {

	public static String createNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String createNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String getSign(Map<String, String> params, String paternerKey) {
		String tempResult=createSign(params);
		tempResult+=( "&key=" + paternerKey);
		tempResult=EncryptUtil.md5(tempResult);
		tempResult=tempResult.toUpperCase();
		return tempResult;
	}

	/**
	 * 构造签名
	 * 
	 * @param params
	 * @param encode
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String createSign(Map<String, String> params) {
		try {
			Map<String, String> tempMap = new TreeMap<String, String>(params);
			List<String> paramTemps = new ArrayList<String>();
			for (String key : tempMap.keySet()) {
				if(key.equals("sign")){
					continue;
				}
				paramTemps.add(key+ "=" +tempMap.get(key));
			}
			String turboResult = StringUtil.collectionMosaic(paramTemps, "&");
			return turboResult;
		} catch (Exception e) {
			PrintException.printException(e);
			return null;
		}
	}

	/**
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters
	 *            请求参数
	 * @return
	 */

	public static String getRequestXml(Map<String, String> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set<?> es = parameters.entrySet();
		Iterator<?> it = es.iterator();
		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			sb.append("<" + k + ">" + v + "</" + k + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * @Description：返回给微信的参数
	 * @param return_code
	 *            返回编码
	 * @param return_msg
	 *            返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			PrintException.printException(e);
		}
		return result;
	}
}