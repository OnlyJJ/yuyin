package com.jiujun.voice.modules.apps.pay.service.impl;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;
import com.jiujun.voice.modules.apps.pay.dao.PayDao;
import com.jiujun.voice.modules.apps.pay.domain.PayAppleProduct;
import com.jiujun.voice.modules.apps.pay.domain.PayConfig;
import com.jiujun.voice.modules.apps.pay.domain.PayRecord;
import com.jiujun.voice.modules.apps.pay.schema.AliPaySchema;
import com.jiujun.voice.modules.apps.pay.schema.WxPaySchema;
import com.jiujun.voice.modules.apps.pay.service.PayService;
import com.jiujun.voice.modules.apps.pay.util.WxPayUtil;
import com.jiujun.voice.modules.apps.pay.util.XMLUtil;
import com.jiujun.voice.modules.apps.user.useraccount.service.TradeService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

/**
 * 
 * @author Coody
 *
 */
@Service
public class PayServiceImpl implements PayService {

	@Resource
	PayDao payDao;
	@Resource
	UserAccountService userAccountService;
	@Resource
	IMessageHandle iMessageHandle;
	@Resource
	TradeService tradeService;
	@Resource
	RedisCache redisCache;

	@Override
	public PayConfig getPayConfig(Integer type) {
		return payDao.getPayConfig(type);
	}

	@Override
	@Transacted
	public AliPaySchema createAlipayOrder(String userId, Double price, String domain) {
		PayConfig config = getPayConfig(2);
		if (config == null) {
			throw new CmdException(ErrorCode.ERROR_1034);
		}
		PayRecord record = new PayRecord();
		record.setPrice(price);
		record.setUserId(userId);
		record.setStatus(0);
		record.setPayType(2);
		Long payId = payDao.addPayRecord(record);

		// 实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", config.getAppId(),
				config.getPrivateKey(), "json", "utf-8", config.getPublicKey(), "RSA2");
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("WOWO语音金币");
		model.setSubject("WOWO语音金币");
		model.setOutTradeNo(payId.toString());
		model.setTimeoutExpress("30m");
		model.setTotalAmount(record.getPrice().toString());
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl(domain + config.getNotifyUrl());
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
			String from = alipayClient.pageExecute(request).getBody();
			AliPaySchema schema = new AliPaySchema();
			schema.setData(response.getBody());
			schema.setPayId(payId);
			schema.setH5Form(from);
			return schema;
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		throw new CmdException(ErrorCode.ERROR_1037);
	}

	@Override
	@Transacted
	public Long doTrade(Long payId, String tradeNo, Double price,Integer gold) {
		PayRecord record = payDao.getPayRecord(payId);
		if (record == null) {
			LogUtil.logger.info("支付订单不存在>>" + payId);
			throw new CmdException(ErrorCode.ERROR_1038);
		}
		// 检查金额
		if (!price.toString().equals(record.getPrice().toString())) {
			LogUtil.logger.info("支付金额不匹配>>" + payId);
			throw new CmdException(ErrorCode.ERROR_1039);
		}
		// 变更订单状态
		Long code = payDao.upPayRecord(payId, tradeNo,gold);
		if (code != 1) {
			LogUtil.logger.info("订单状态变更失败>>" + payId);
			throw new CmdException(ErrorCode.ERROR_1040);
		}
		// 加款
		code = tradeService.changeGold(record.getUserId(), gold, "充值增加金币");
		record.setGold(gold.doubleValue());
		// 写入队列调度
		redisCache.lBeanPushHead(CacheConstants.CALLER_PAY_QUEUE, record);

		// 发送IM消息
		iMessageHandle.sendGeneralMsg(record.getUserId(), "充值" + gold + "金币成功", "恭喜成功充值" + gold + "金币",
				MsgEnum.CHARGE_MSG.getType());
		return code;
	}


	@Override
	public WxPaySchema createWxOrder(String userId, Double price, String tradeType, String domain, String clientIp) {
		PayConfig config = getPayConfig(1);
		if (config == null) {
			throw new CmdException(ErrorCode.ERROR_1034);
		}
		PayRecord record = new PayRecord();
		record.setPrice(price);
		record.setUserId(userId);
		record.setStatus(0);
		record.setPayType(1);
		Long payId = payDao.addPayRecord(record);
		// 生产环境
		String notifyUrl = domain + config.getNotifyUrl();
		String randomStr = JUUIDUtil.createUuid();
		SortedMap<String, String> parameters = new TreeMap<String, String>();
		parameters.put("appid", config.getAppId());
		parameters.put("mch_id", config.getSellerId());
		parameters.put("nonce_str", randomStr);
		parameters.put("body", "WOWO语音金币");
		parameters.put("out_trade_no", payId.toString()); // 订单id
		parameters.put("attach", "WOWO语音金币");
		parameters.put("total_fee", String.valueOf(Double.valueOf(record.getPrice() * 100).intValue()));
		parameters.put("spbill_create_ip", clientIp);
		notifyUrl = "http://www.9mitao.com/pay/notify/wxpay.do";
		parameters.put("notify_url", notifyUrl);
		parameters.put("trade_type", tradeType);
		// 设置签名
		String sign = WxPayUtil.getSign(parameters, config.getPrivateKey());
		parameters.put("sign", sign);
		// 封装请求参数结束
		String requestXML = WxPayUtil.getRequestXml(parameters);
		// 调用统一下单接口
		HttpEntity entity = HttpUtil.post("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);
		LogUtil.logger.info("微信支付响应报文>>" + entity.getHtml());
		Map<String, String> map = XMLUtil.doXMLParse(entity.getHtml());
		SortedMap<String, String> parameterMap2 = new TreeMap<String, String>();
		parameterMap2.put("appid", config.getAppId());
		parameterMap2.put("partnerid", config.getSellerId());
		parameterMap2.put("prepayid", map.get("prepay_id"));
		parameterMap2.put("package", "Sign=WXPay");
		parameterMap2.put("noncestr", randomStr);
		// 本来生成的时间戳是13位，但是ios必须是10位，所以截取了一下
		parameterMap2.put("timestamp", String.valueOf(System.currentTimeMillis()).toString().substring(0, 10));
		String sign2 = WxPayUtil.getSign(parameterMap2, config.getPrivateKey());
		parameterMap2.put("sign", sign2);

		WxPaySchema schema = new WxPaySchema();
		schema.setAppId(config.getAppId());
		schema.setNonceStr(randomStr);
		schema.setPackageValue(parameterMap2.get("package"));
		schema.setPartnerId(config.getSellerId());
		schema.setPayId(payId);
		schema.setPrepayId(map.get("prepay_id"));
		schema.setTimeStamp(parameterMap2.get("timestamp"));
		schema.setSign(sign2);
		schema.setNotifyUrl(notifyUrl);

		return schema;
	}

	@Override
	public PayRecord createAppletOrder(String userId, Double price) {
		PayRecord record = new PayRecord();
		record.setPrice(price);
		record.setUserId(userId);
		record.setStatus(0);
		record.setPayType(3);
		Long payId = payDao.addPayRecord(record);
		if (payId < 1) {
			throw new CmdException(ErrorCode.ERROR_1037);
		}
		record.setId(payId);
		return record;
	}

	@Override
	public PayRecord getPayRecord(Long payId) {
		return payDao.getPayRecord(payId);
	}

	@Override
	public Map<String, PayAppleProduct> getPayAppleProducts() {
		return payDao.getPayAppleProducts();
	}

	@Override
	public PayRecord getPayRecordByTransNo(String transNo, Integer type) {
		return payDao.getPayRecordByTransNo(transNo, type);
	}

	@Override
	public PayRecord getPayRecordByUserId(String userId) {
		return payDao.getPayRecordByUserId(userId);
	}

}
