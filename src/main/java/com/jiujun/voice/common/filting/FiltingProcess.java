package com.jiujun.voice.common.filting;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.StringUtil;

/**
 * 敏感词过滤
 * 
 * @author Administrator
 *
 */
@Component
public class FiltingProcess implements InitializingBean {

	@Resource
	JdbcHandle jdbcHandle;

	public static List<String> filtings = new ArrayList<String>();

	/**
	 * 敏感词过滤
	 * 
	 * @param context
	 *            需要过滤的内容
	 * @param seize
	 *            用于替换的文本
	 * @return
	 */
	public static String doFilting(String context, String seize) {
		if (StringUtil.isNullOrEmpty(context)) {
			return context;
		}
		for (String filting : filtings) {
			context = context.replace(filting, seize);
		}
		return context;
	}

	/**
	 * 敏感词查询
	 * @param context 
	 * @return
	 */
	public static String getFilting(String context) {
		if (StringUtil.isNullOrEmpty(context)) {
			return null;
		}
		for (String filting : filtings) {
			if(context.contains(filting)){
				return filting;
			}
		}
		return null;
	}
	@Override
	public void afterPropertiesSet() throws Exception {
		filtings = jdbcHandle.query(String.class, "select keyword from t_black_keyword");
	}

}
