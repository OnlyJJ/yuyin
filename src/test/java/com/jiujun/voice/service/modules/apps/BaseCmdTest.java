package com.jiujun.voice.service.modules.apps;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;

public class BaseCmdTest {
	
	private static String serverUrl="http://123.103.15.174:8180/service/call.do";
	private static String clientId="fe2766fe406a499a9eb6457aa4a013d8";
	private static String userId="100861";
	

	@SuppressWarnings("unchecked")
	protected <T> T execute(String action,BaseReqVO req,Class<?> respType) {
		Header header=builderHeader();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("header", header);
		map.put("body", req);
		String postData=JSON.toJSONString(map);
		System.out.println("请求参数:"+postData);
		HttpEntity entity=HttpUtil.post(serverUrl+"?action="+action, postData);
		System.out.println("响应参数:"+entity.getHtml());
		return (T) JSON.parseObject(entity.getHtml(), respType);
	}
	
	
	private Header builderHeader() {
		return builderHeader(userId, "4adcc2a8edef4cd98cae8bcad104e140");
	}
	
	private Header builderHeader(String userId,String token) {
		Header header=new Header();
		header.setChannel("CHINA-MOBILE");
		header.setClientId(clientId);
		header.setPackager("com.jiujun.voice.android");
		header.setVersion("V1.0.1");
		header.setUserId(userId);
		header.setToken(token);
		return header;
	}
}
