package com.jiujun.voice.common.cmd.builder;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.entity.CmdFlushEntity;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.container.BeanContainer;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.utils.StringUtil;

/**
 * 
 * @author Coody
 *
 */
@Component
public class CmdBuilder {

	public static CmdFlushEntity builder(String action,String ipAddress,String context) {
		if (StringUtil.isNullOrEmpty(action)) {
			throw new CmdException(ErrorCode.ERROR_1007);
		}
		String[] attrs = action.trim().split("\\.");
		if (attrs.length != 2) {
			throw new CmdException(ErrorCode.ERROR_1007);
		}
		String cmd = attrs[0];
		action = attrs[1];
		if (StringUtil.hasNull(cmd, action)) {
			throw new CmdException(ErrorCode.ERROR_1007);
		}
		RootCmd rootCmd = BeanContainer.getBean(cmd);
		if (rootCmd == null) {
			throw new CmdException(ErrorCode.ERROR_1001);
		}
		Map<String, Object> data = null;
		if (!StringUtil.isNullOrEmpty(context)) {
			data = JSON.parseObject(context, new TypeReference<Map<String, Object>>() {
			});
		}
		Header header = builderHeader(data, cmd, action);
		header.setIpAddress(ipAddress);
		String body = getBody(data);
		CmdFlushEntity flush=new CmdFlushEntity();
		flush.setBody(body);
		flush.setHeader(header);
		flush.setInstance(rootCmd);
		return flush;
	}
	
	private static String getBody(Map<String, Object> data) {
		if (StringUtil.isNullOrEmpty(data) || StringUtil.isNullOrEmpty(data.get("body"))) {
			return null;
		}
		return data.get("body").toString();
	}
	private static Header builderHeader(Map<String, Object> data, String cmd, String action) {
		if (StringUtil.isNullOrEmpty(data) || StringUtil.isNullOrEmpty(data.get("header"))) {
			Header header = new Header();
			header.setCmd(cmd);
			header.setAction(action);
			return header;
		}
		String headerJson = data.get("header").toString();
		Header header = JSON.parseObject(headerJson, Header.class);
		header.setCmd(cmd);
		header.setAction(action);
		return header;
	}
}
