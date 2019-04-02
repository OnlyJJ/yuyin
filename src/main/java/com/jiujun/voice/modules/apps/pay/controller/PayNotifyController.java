package com.jiujun.voice.modules.apps.pay.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.RequestUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.pay.domain.PayConfig;
import com.jiujun.voice.modules.apps.pay.service.PayService;
import com.jiujun.voice.modules.apps.pay.util.WxPayUtil;
import com.jiujun.voice.modules.apps.pay.util.XMLUtil;

/**
 * 
 * @author Coody
 *
 */
@Controller
@RequestMapping("/pay/notify")
public class PayNotifyController {

	@Resource
	PayService payService;

	@RequestMapping("/alipay.do")
	public void aliPay(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = getParamentMap(request);
		if (StringUtil.isNullOrEmpty(params)) {
			LogUtil.logger.info("支付回调,未获取到回调报文");
			return;
		}
		LogUtil.logger.info("支付宝支付回调,收到消息>>" + JSON.toJSONString(params));
		try {
			// 验证签名
			PayConfig config = payService.getPayConfig(2);
			if (config == null) {
				LogUtil.logger.info("支付宝支付回调,未获取到支付配置>>" + JSON.toJSONString(params));
				return;
			}
			LogUtil.logger.info("支付宝支付回调,使用配置>>" + JSON.toJSONString(config));
			if (!AlipaySignature.rsaCheckV1(params, config.getPublicKey(), "UTF-8", "RSA2")) {
				LogUtil.logger.info("支付宝支付回调,签名校验失败>>" + params.toString());
				return;
			}
			if (!"TRADE_SUCCESS".equals(params.get("trade_status"))) {
				LogUtil.logger.info("支付宝支付回调,支付状态未成功>>" + JSON.toJSONString(params));
				return;
			}
			// 付款金额
			String amount = params.get("buyer_pay_amount");
			// 商户订单号
			String outTradeNo = params.get("out_trade_no");
			// 支付宝交易号
			String tradeNo = params.get("trade_no");
			Integer gold= Double.valueOf(Double.valueOf(amount) * 100).intValue();
			// 附加数据
			Long code = payService.doTrade(Long.valueOf(outTradeNo), tradeNo, Double.valueOf(amount),gold);
			if (code < 1) {
				LogUtil.logger.info("支付宝支付回调,交易处理失败>>" + JSON.toJSONString(params));
				return;
			}
		} catch (AlipayApiException e) {
			LogUtil.logger.info("支付宝支付回调,抛出异常>>" + PrintException.getErrorStack(e));
			return;
		}
		response.getWriter().print("success");
	}

	@RequestMapping("/wxpay.do")
	public void wxpayDoPay(HttpServletRequest req, HttpServletResponse res) throws IOException {
		try {
			PayConfig config = payService.getPayConfig(1);
			String postData = RequestUtil.getPostContent(req);
			LogUtil.logger.info("微信支付回调,收到消息>>" + postData);
			Map<String, String> paraMap = XMLUtil.doXMLParse(postData);
			String appId = paraMap.get("appid");
			if (!appId.equals(config.getAppId())) {
				LogUtil.logger.error("appid不匹配，订单放弃:" + paraMap);
				res.getWriter().print(returnXML("FAIL"));
				return;
			}
			if (!paraMap.get("result_code").equals("SUCCESS")) {
				LogUtil.logger.error("订单交易失败:" + paraMap);
				res.getWriter().print(returnXML("FAIL"));
				return;
			}
			// 验证签名
			String sign = WxPayUtil.getSign(paraMap, config.getPrivateKey());
			if (!sign.equals(paraMap.get("sign"))) {
				LogUtil.logger.error("签名校验不通过，订单放弃:" + paraMap);
				res.getWriter().print(returnXML("FAIL"));
				return;
			}
			String tradeNo=paraMap.get("transaction_id");
			Double amount=Double.valueOf(paraMap.get("total_fee"))/100;
			Integer gold= Double.valueOf(Double.valueOf(amount) * 100).intValue();
			Long code = payService.doTrade(Long.valueOf(paraMap.get("out_trade_no")), tradeNo, amount,gold);
			if (code < 1) {
				LogUtil.logger.info("支付回调,交易处理失败>>" + JSON.toJSONString(paraMap));
				res.getWriter().print(returnXML("FAIL"));
				return;
			}
		} catch (Exception e) {
			PrintException.printException(e);
			res.getWriter().print(returnXML("FAIL"));
			return;
		} 
		res.getWriter().print(returnXML("SUCCESS"));
	}

	private String returnXML(String returnCode) {

		return "<xml><return_code><![CDATA["

				+ returnCode

				+ "]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
	}

	private Map<String, String> getParamentMap(HttpServletRequest request) {
		Map<String, String[]> requestParams = request.getParameterMap();
		Map<String, String> result = new HashMap<String, String>();
		for (String key : requestParams.keySet()) {
			String[] values = requestParams.get(key);
			if (StringUtil.isNullOrEmpty(values)) {
				result.put(key, "");
				continue;
			}
			if (values.length == 1) {
				result.put(key, values[0]);
				continue;
			}
			result.put(key, StringUtil.collectionMosaic(values, ","));
		}
		return result;
	}
}
