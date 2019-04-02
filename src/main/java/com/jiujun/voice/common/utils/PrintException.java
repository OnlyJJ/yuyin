package com.jiujun.voice.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.jiujun.voice.common.logger.util.LogUtil;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class PrintException {

	public static String getErrorStack(Throwable e) {
		String error = null;
		if (e != null) {
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				PrintStream ps = new PrintStream(baos);
				e.printStackTrace(ps);
				error = baos.toString();
				baos.close();
				ps.close();
			} catch (Exception e1) {
				error = e.toString();
			}
		}
		return error;
	}

	public static void printException(Throwable e) {
		LogUtil.logger.error(getErrorStack(e));
	}

}
