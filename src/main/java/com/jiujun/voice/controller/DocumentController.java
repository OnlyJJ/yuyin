package com.jiujun.voice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.builder.CmdBuilder;
import com.jiujun.voice.common.cmd.entity.CmdFlushEntity;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.DocHandle;
import com.jiujun.voice.common.doc.DocMsgHandle;
import com.jiujun.voice.common.doc.entity.ActionDocument;
import com.jiujun.voice.common.doc.entity.ActionMsgDocument;
import com.jiujun.voice.common.doc.entity.CmdDocument;
import com.jiujun.voice.common.entity.MsgEntity;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.RequestUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.TokenRespVO;
import com.jiujun.voice.modules.im.enm.MsgEnum;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@RequestMapping("/doc")
@Controller
public class DocumentController {

	private static final String DOCUMENT_LOGINED = "DOCUMENT_LOGINED";

	private static final String AUTH_KEY = "Coody888";

	@Resource
	DocHandle docHandle;
	@Resource
	DocMsgHandle docMsgHandle;

	@RequestMapping("/result.do")
	public String result(HttpServletRequest req, HttpServletResponse resp) {
		
		if (LogUtil.isProd()) {
			return "";
		}
		resp.setContentType("text/html;charset=UTF-8");
		String pass = getParamentWithCookie(req, resp, DOCUMENT_LOGINED);
		if (!AUTH_KEY.equals(pass)) {
			return "doc/auth";
		}
		List<ErrorCode> resultCodes = PropertUtil.loadEnumInstances(ErrorCode.class);
		req.setAttribute("resultCodes", resultCodes);

		List<MsgEnum> msgCodes = PropertUtil.loadEnumInstances(MsgEnum.class);
		req.setAttribute("msgCodes", msgCodes);

		return "doc/result";
	}

	@RequestMapping("/api.do")
	public String api(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		if (LogUtil.isProd()) {
			return "";
		}
		resp.setContentType("text/html;charset=UTF-8");
		String pass = getParamentWithCookie(req, resp, DOCUMENT_LOGINED);
		if (!AUTH_KEY.equals(pass)) {
			return "doc/auth";
		}
		List<RootCmd> instances = docHandle.getCmdInstance();
		if (StringUtil.isNullOrEmpty(instances)) {
			return "doc/api";
		}
		List<CmdDocument> cmdDocuments = new ArrayList<CmdDocument>();
		for (RootCmd cmd : instances) {
			CmdDocument cmdDocument = docHandle.builderCmdDocument(cmd);
			if (cmdDocument == null) {
				continue;
			}
			cmdDocuments.add(cmdDocument);
		}
		cmdDocuments = PropertUtil.doSeq(cmdDocuments, "name");
		req.setAttribute("cmdDocuments", cmdDocuments);
		// 处理当前接口渲染
		if (!StringUtil.isNullOrEmpty(cmdDocuments)) {
			CmdDocument cmdDocument = cmdDocuments.get(0);
			ActionDocument actionDocument = cmdDocument.getActions().get(0);

			String cmdParam = req.getParameter("cmd");
			String actionParam = req.getParameter("action");
			if (!StringUtil.hasNull(cmdParam, actionParam)) {
				cmdDocument = PropertUtil.getByList(cmdDocuments, "cmd", cmdParam);
				actionDocument = PropertUtil.getByList(cmdDocument.getActions(), "action", actionParam);
			}
			req.setAttribute("cmdDocument", cmdDocument);
			req.setAttribute("actionDocument", actionDocument);
			req.setAttribute("title","["+cmdDocument.getName()+">" +actionDocument.getName()+"]");
			ActionMsgDocument actionInputMsgDocument = docMsgHandle.buildMsg(actionDocument);
			try {
				String userId = getParamentWithCookie(req, resp, "userId");
				String token = getParamentWithCookie(req, resp, "token");
				if(!StringUtil.hasNull(userId,token)){
					Map<String, Object> inputMsgMap = JSON.parseObject(actionInputMsgDocument.getMsg(),
							new TypeReference<Map<String, Object>>() {
							});
					Map<String, Object> headerMap = JSON.parseObject(inputMsgMap.get("header").toString(),
							new TypeReference<Map<String, Object>>() {
							});
					headerMap.put("userId", userId);
					headerMap.put("token", token);
					inputMsgMap.put("header", headerMap);
					actionInputMsgDocument.setMsg(JSON.toJSONString(inputMsgMap,SerializerFeature.PrettyFormat));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			req.setAttribute("actionInputMsgDocument", actionInputMsgDocument);
			ActionMsgDocument actionOutputMsgDocument = docMsgHandle.getOutputMsg(actionDocument);
			req.setAttribute("actionOutputMsgDocument", actionOutputMsgDocument);
		}
		return "doc/api";
	}

	@RequestMapping("/invoke.do")
	@ResponseBody
	public Object invoke(HttpServletRequest req, HttpServletResponse resp) {
		if (LogUtil.isProd()) {
			return "";
		}
		resp.setContentType("application/json;charset=UTF-8");
		String pass = getParamentWithCookie(req, resp, DOCUMENT_LOGINED);
		if (!AUTH_KEY.equals(pass)) {
			return "doc/auth";
		}
		try {
			String action = req.getParameter("action");
			String context = req.getParameter("context");
			CmdFlushEntity flushEntity = CmdBuilder.builder(action, RequestUtil.getIpAddr(req), context);
			BaseRespVO result = flushEntity.getInstance().execute(flushEntity.getHeader(), flushEntity.getBody());
			if (result.getCode() == ErrorCode.SUCCESS_0.getCode()) {
				CmdDocument cmdDocument = docHandle.builderCmdDocument(flushEntity.getInstance());
				ActionDocument actionDocument = PropertUtil.getByList(cmdDocument.getActions(), "action", action);
				if (actionDocument != null) {
					ActionMsgDocument outputMsg = new ActionMsgDocument();
					outputMsg.setFingerprint(actionDocument.getFingerprint());
					outputMsg.setMsg(JSON.toJSONString(result, SerializerFeature.PrettyFormat));
					docMsgHandle.setOutputMsg(outputMsg);
					ActionMsgDocument inputMsg = new ActionMsgDocument();
					inputMsg.setFingerprint(actionDocument.getFingerprint());
					if (!StringUtil.isNullOrEmpty(context)) {
						Map<String, Object> contextMap = JSON.parseObject(context,
								new TypeReference<Map<String, Object>>() {
								});
						context = JSON.toJSONString(contextMap, SerializerFeature.PrettyFormat);
					}
					inputMsg.setMsg(context);
					docMsgHandle.setInputMsg(inputMsg);
				}
			}
			if (result instanceof TokenRespVO) {
				TokenRespVO token = (TokenRespVO) result;
				Cookie userCookie = new Cookie("userId", token.getUserId());
				userCookie.setHttpOnly(true);
				resp.addCookie(userCookie);
				Cookie tokenCookie = new Cookie("token", token.getToken());
				tokenCookie.setHttpOnly(true);
				resp.addCookie(tokenCookie);
			}
			return new MsgEntity(0, "操作成功", JSON.toJSONString(result, SerializerFeature.PrettyFormat));
		} catch (CmdException cmdException) {
			return new MsgEntity(-1, "执行出错", cmdException.builderResponse().toJson());
		} catch (Exception e) {
			return new MsgEntity(-1, "系统繁忙", PrintException.getErrorStack(e));
		}
	}

	private String getParamentWithCookie(HttpServletRequest req, HttpServletResponse resp, String paramentName) {
		String pass = req.getParameter(paramentName);
		if (StringUtil.isNullOrEmpty(pass)) {
			Cookie[] cookies = req.getCookies();
			if (StringUtil.isNullOrEmpty(cookies)) {
				return "";
			}
			for (Cookie cookie : cookies) {
				if (!paramentName.equals(cookie.getName())) {
					continue;
				}
				pass = cookie.getValue();
			}
		}
		if (!StringUtil.isNullOrEmpty(pass)) {
			Cookie cookie = new Cookie(paramentName, pass);
			cookie.setHttpOnly(true);
			resp.addCookie(cookie);
		}
		return pass;
	}
	
}
