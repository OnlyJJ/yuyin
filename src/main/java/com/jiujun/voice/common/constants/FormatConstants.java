package com.jiujun.voice.common.constants;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class FormatConstants {
	//验证用户名
	public final static String USER_NAME="^[a-zA-Z0-9_-]{4,16}$";
	//验证密码
	public final static String USER_PWD="^[a-z0-9_-]{6,16}$";
	//验证手机
	public final static String MOBILE="^((13[0-9])|(14[5,7,9])|(15[^4])|(16[^4])|(18[0-9])|(17[0-9]))\\d{8}$";;
	//验证邮箱
	public final static String EMAIL="^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
	//验证MD5
	public final static String MD5="[A-Za-z0-9_]{16,40}";
	//验证日期，yyyy-MM-dd
	public final static String DATE="[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}";
	//验证时间，yyyy-MM-dd HH:MM:SS
	public final static String DATETIME="^\\d{4}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D+\\d{1,2}\\D*";
	//验证整数
	public final static String NUMBER="^-?[0-9]\\d*$";
	//正整数
	public final static String POSITIVE_NUMBER="^[0-9]*[1-9][0-9]*$";
	//验证中文
	public final static String CHINESE="^[\u4e00-\u9fa5]+$";
	//验证字母
	public final static String LETTER="^[a-zA-Z]+$";
	
	
}
