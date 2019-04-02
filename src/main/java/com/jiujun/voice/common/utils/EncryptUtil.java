package com.jiujun.voice.common.utils;

import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * 系统常用加密、编码工具
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public final class EncryptUtil {

	public final static String CHARSET = "UTF-8";

	private EncryptUtil() {
		throw new Error("Utility classes should not instantiated!");
	}

	public static String md5(String pwd) {
		// 定义一个字节数组
		byte[] secretBytes = null;
		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 对字符串进行加密
			md.update(pwd.getBytes("UTF-8"));
			// 获得加密后的数据
			secretBytes = md.digest();
		} catch (Exception e) {
			PrintException.printException(e);
		}
		// 将加密后的数据转换为16进制数字
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
		// 如果生成数字未满32位，需要前面补0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	/**
	 * 3DES BCB 解密
	 * @param msg
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String desDecrypt(String msg, String key) throws Exception {
		byte[] byes = Base64.getDecoder().decode(msg);
		byte[] data = TripleDESUtil.decrypt(byes, key.getBytes("UTF-8"));
		return new String(data, CHARSET);
	}

	/**
	 * 3DES BCB加密
	 * 
	 * @param msg
	 * @param key
	 *            密钥
	 * @return
	 * @throws Exception
	 * @author hanweizhao
	 */
	public static String desEncrypt(String msg, String key) throws Exception {
		byte[] data = TripleDESUtil.encrypt(msg.getBytes(CHARSET), key.getBytes("UTF-8"));
		String str = Base64.getEncoder().encodeToString(data);
		return str;
	}


	/**
	 * 3DES对称加密
	 * 
	 * @author
	 * 
	 */
	public static class TripleDESUtil {
		/**
		 * 密钥算法
		 */
		public static final String KEY_ALGORITHM = "DESede";

		/**
		 * 加密/解密算法/工作模式/填充方式
		 */
		public static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";

		private TripleDESUtil() {
			throw new Error("Utility classes should not instantiated!");
		}

		private static final int DEFAULT_KEY_SIZE = 168;

		/**
		 * 生成密钥
		 * 
		 * @return byte[] 二进制密钥
		 */
		public static byte[] initkey(int keySize) throws Exception {
			// 实例化密钥生成器
			KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
			// 初始化密钥生成器
			kg.init(keySize);
			// 生成密钥
			SecretKey secretKey = kg.generateKey();
			// 获取二进制密钥编码形式
			return secretKey.getEncoded();
		}

		/**
		 * 生成密钥
		 * 
		 * @return byte[] 二进制密钥
		 */
		public static byte[] initkey() throws Exception {
			return initkey(DEFAULT_KEY_SIZE);
		}

		/**
		 * 转换密钥
		 * 
		 * @param key
		 *            二进制密钥
		 * @return Key 密钥
		 */
		public static Key toKey(byte[] key) throws Exception {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key);
			// 实例化Des密钥
			DESedeKeySpec dks = new DESedeKeySpec(key);
			// 实例化密钥工厂
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
			// 生成密钥
			return keyFactory.generateSecret(dks);
		}

		/**
		 * 加密数据
		 * 
		 * @param data
		 *            待加密数据
		 * @param key
		 *            密钥
		 * @return byte[] 加密后的数据
		 */
		public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
			// 还原密钥
			Key k = toKey(key);
			// 实例化
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			// 初始化，设置为加密模式
			cipher.init(Cipher.ENCRYPT_MODE, k);
			// 执行操作
			return cipher.doFinal(data);
		}

		/**
		 * 解密数据
		 * 
		 * @param data
		 *            待解密数据
		 * @param key
		 *            密钥
		 * @return byte[] 解密后的数据
		 */
		public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
			Key k = toKey(key);
			// 实例化
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
			// 初始化，设置为解密模式
			cipher.init(Cipher.DECRYPT_MODE, k);
			// 执行操作
			return cipher.doFinal(data);
		}
	}

	/**
	 * 用于给用户密码进行加密
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	public static String encryptUserPassword(String userId, String password) {
		String hash = md5(userId);
		return md5(password + hash);
	}
	
	
	public static void main(String[] args) {
	}
}
