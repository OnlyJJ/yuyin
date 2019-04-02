package com.jiujun.voice.modules.apps.pay.controller;

import java.io.IOException;
import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.jiujun.voice.common.utils.RequestUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.pay.domain.PayConfig;
import com.jiujun.voice.modules.apps.pay.domain.PayRecord;
import com.jiujun.voice.modules.apps.pay.service.PayService;

/**
 * 
 * @author Coody
 *
 */
@Controller
@RequestMapping("/pay/jump")
public class PayJumpController {

	@Resource
	PayService payService;

	@RequestMapping("/alipay.do")
	public void aliPay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String payId = request.getParameter("payId");
		if (StringUtil.isNullOrEmpty(payId)) {
			return;
		}
		PayRecord record = payService.getPayRecord(Long.valueOf(payId));
		PayConfig config = payService.getPayConfig(2);
		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", config.getAppId(),
				config.getPrivateKey(), "json", "utf-8", config.getPublicKey(), "RSA2");
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest req = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("金币购买");
		model.setSubject("金币");
		model.setOutTradeNo(payId.toString());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(record.getPrice().toString());
		model.setProductCode("QUICK_MSECURITY_PAY");
		req.setBizModel(model);

		String domain = RequestUtil.getBasePath(request);
		req.setNotifyUrl(domain + config.getNotifyUrl());
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			String form = alipayClient.pageExecute(req).getBody();
			response.setContentType("text/html;charset=" + "UTF-8");
			response.getWriter().write(form);// 直接将完整的表单html输出到页面
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping("/wxpay.do")
	public void wxpay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String bank = request.getParameter("bank");
		if (StringUtil.isNullOrEmpty(bank)) {
			return;
		}
		String url = new String(Base64.getDecoder().decode(bank), "UTF-8");
		response.setContentType("text/html;charset=" + "UTF-8");
		response.getWriter()
				.print("<html><head><script>window.location.href='" + url + "';</script></head><body></body></html>");
	}
}
