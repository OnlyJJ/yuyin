package com.jiujun.voice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cmd.builder.CmdBuilder;
import com.jiujun.voice.common.cmd.entity.CmdFlushEntity;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.RequestUtil;

/**
 * 
 * @author Coody
 * @date 2018年10月31日
 */
@RequestMapping("/service")
@Controller
public class CmdController {

	@RequestMapping("/call.do")
	public void call(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json;charset=UTF-8");
		String action = req.getParameter("action");
		String context = RequestUtil.getPostContent(req);
		LogUtil.logger.info("CmdEnter>>" + action + ">>" + ((context!=null&&context.length()>512)?"报文过长，不予展示":context));
		long enterTime = System.currentTimeMillis();
		String result = doCall(req, resp, action, context);
		long outTime = System.currentTimeMillis();
		LogUtil.logger.info("CmdOut>>" + action + ">>" + "speedTime>>" + (outTime - enterTime) + ">>" + ((result!=null&&result.length()>1024)?"报文过长，不予展示":result));
	}

	private String doCall(HttpServletRequest req, HttpServletResponse resp, String action, String context) {
		try {
			CmdFlushEntity flushEntity = CmdBuilder.builder(action, RequestUtil.getIpAddr(req), context);
			BaseRespVO result = flushEntity.getInstance().execute(flushEntity.getHeader(), flushEntity.getBody());
			String resultJson = JSON.toJSONString(result);
			printException(resp, resultJson);
			return resultJson;
		} catch (CmdException cmdException) {
			String resultJson = cmdException.builderResponse().toJson();
			printException(resp, resultJson);
			return resultJson;
		}
	}

	private void printException(HttpServletResponse resp, String result) {
		try {
			resp.getWriter().println(result);
			resp.getWriter().flush();
			resp.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
