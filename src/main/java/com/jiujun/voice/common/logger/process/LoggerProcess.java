package com.jiujun.voice.common.logger.process;

import java.lang.reflect.Method;

import org.slf4j.MDC;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.utils.StringUtil;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
public class LoggerProcess {
	
	
	public static String getMethodFlag(Method method) {
		LogFlag flag=method.getAnnotation(LogFlag.class);
		if(flag!=null) {
			return flag.value();
		}
		DocFlag docFlag=method.getAnnotation(DocFlag.class);
		if(docFlag!=null){
			return docFlag.value();
		}
		 return "";
	}
	
	public static String getClassFlag(Class<?> clazz) {
		LogFlag flag=clazz.getAnnotation(LogFlag.class);
		if(flag!=null) {
			return flag.value();
		}
		DocFlag docFlag=clazz.getAnnotation(DocFlag.class);
		if(docFlag!=null){
			return docFlag.value();
		}
		 return "";
	}

	public static void writeLogFlag(String module) {
		MDC.put("logFlag", module);
	}
	public static String getCurrentFlag() {
		return MDC.get("logFlag");
	}

	public static String wipeCurrentFlag() {
		String logFlag = MDC.get("logFlag");
		if (logFlag == null) {
			return "";
		}
		String []tabs = logFlag.split(">");
		if (tabs.length == 1) {
			MDC.put("logFlag", "");
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tabs.length - 1; i++) {
			if (!StringUtil.isNullOrEmpty(sb)) {
				sb.append(">");
			}
			sb.append(tabs[i]);
		}
		MDC.put("logFlag", sb.toString());
		return sb.toString();
	}
}
