package com.jiujun.voice.modules.apps.file.util;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HMacSHAUtil {

	/**
	 * HMAC-SHA1加密方案<br>
	 * 
	 * @param content-待加密内容
	 * @param secretKey-密钥
	 * @return HMAC_SHA1加密后的字符串
	 */
	public static String HMACSHA1(String content, String secretKey) {
		try {
			byte[] secretKeyBytes = secretKey.getBytes();
			SecretKey secretKeyObj = new SecretKeySpec(secretKeyBytes, "HmacSHA1");
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(secretKeyObj);
			byte[] text = content.getBytes("UTF-8");
			byte[] encryptContentBytes = mac.doFinal(text);
			// SHA1算法得到的签名长度，都是160位二进制码，换算成十六进制编码字符串表示
			String encryptContent = bytesToHexString(encryptContentBytes);
			return encryptContent;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	/**
	 * 获取字节数组的16进制字符串表示形式<br>
	 * 范例：0xff->'ff'
	 * 
	 * @param bytes
	 *            字节数组
	 * @return string-16进制的字符串表示形式
	 */
	private static String bytesToHexString(byte[] bytes) {
		StringBuilder hexString = new StringBuilder("");
		for (byte ib : bytes) {
			char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			char[] ob = new char[2];
			ob[0] = Digit[(ib >>> 4) & 0X0f];
			ob[1] = Digit[ib & 0X0F];
			hexString.append(ob);
		}
		return hexString.toString();
	}
}
