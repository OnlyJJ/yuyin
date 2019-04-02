package com.jiujun.voice.modules.apps.pay.cmd;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.common.utils.RequestUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.HttpsUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;
import com.jiujun.voice.modules.apps.pay.cmd.vo.AliPayRespVO;
import com.jiujun.voice.modules.apps.pay.cmd.vo.ApplePayRespVO;
import com.jiujun.voice.modules.apps.pay.cmd.vo.FirstChargeRespVO;
import com.jiujun.voice.modules.apps.pay.cmd.vo.H5PayRespVO;
import com.jiujun.voice.modules.apps.pay.cmd.vo.PayReqVO;
import com.jiujun.voice.modules.apps.pay.cmd.vo.QueryPayRecordReqVO;
import com.jiujun.voice.modules.apps.pay.cmd.vo.QueryPayRecordRespVO;
import com.jiujun.voice.modules.apps.pay.cmd.vo.WxPayRespVO;
import com.jiujun.voice.modules.apps.pay.domain.PayAppleProduct;
import com.jiujun.voice.modules.apps.pay.domain.PayConfig;
import com.jiujun.voice.modules.apps.pay.domain.PayRecord;
import com.jiujun.voice.modules.apps.pay.schema.AliPaySchema;
import com.jiujun.voice.modules.apps.pay.schema.WxPaySchema;
import com.jiujun.voice.modules.apps.pay.service.PayService;
import com.jiujun.voice.modules.apps.pay.util.WxPayUtil;
import com.jiujun.voice.modules.apps.pay.util.XMLUtil;
import com.jiujun.voice.modules.apps.system.service.ClientAppService;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;

/**
 * 
 * @author Coody
 *
 */
@CmdOpen("pay")
@DocFlag("支付中心")
public class PayCmd extends RootCmd {

	@Resource
	PayService payService;
	@Resource
	HttpServletRequest request;
	@Resource
	ClientAppService clientAppService;
	@Resource
	UserAccountService userAccountService;

	@CmdAction
	@DocFlag("支付宝支付接口")
	public AliPayRespVO aliPay(PayReqVO req) {
		if (req.getPrice() <= 0) {
			throw new CmdException(ErrorCode.ERROR_1006);
		}
		String domain = RequestUtil.getBasePath(request);
		AliPaySchema schema = payService.createAlipayOrder(req.getHeader().getUserId(), req.getPrice(), domain);
		AliPayRespVO resp = new AliPayRespVO();
		resp.setPayId(schema.getPayId());
		resp.setData(schema.getData());
		return resp;
	}

	@CmdAction
	@DocFlag("微信支付接口")
	public WxPayRespVO wxPay(PayReqVO req) {
		if (req.getPrice() <= 0) {
			throw new CmdException(ErrorCode.ERROR_1006);
		}
		String domain = RequestUtil.getBasePath(request);
		WxPaySchema schema = payService.createWxOrder(req.getHeader().getUserId(), req.getPrice(), "APP", domain,
				req.getHeader().getIpAddress());
		WxPayRespVO resp = new WxPayRespVO();
		BeanUtils.copyProperties(schema, resp);
		return resp;
	}

	@CmdAction
	@DocFlag("微信H5支付接口")
	public H5PayRespVO wxH5Pay(PayReqVO req) throws UnsupportedEncodingException {
		if (req.getPrice() <= 0) {
			throw new CmdException(ErrorCode.ERROR_1006);
		}
		String domain = RequestUtil.getBasePath(request);
		WxPaySchema schema = payService.createWxOrder(req.getHeader().getUserId(), req.getPrice(), "MWEB", domain,
				req.getHeader().getIpAddress());
		String payUrl = MessageFormat.format(
				"https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?prepay_id={0}&package={1}&redirect_url={2}",
				schema.getPrepayId(), schema.getPackageValue(), URLEncoder.encode(schema.getNotifyUrl(), "UTF-8"));
		H5PayRespVO resp = new H5PayRespVO();
		resp.setPayId(schema.getPayId());
		String bankUrl = MessageFormat.format(domain + "pay/jump/wxpay.do?bank={0}",
				Base64.getEncoder().encodeToString(payUrl.getBytes("UTF-8")));
		resp.setPayUrl(bankUrl);
		return resp;
	}

	@CmdAction
	@DocFlag("支付宝H5支付接口")
	public H5PayRespVO aliH5Pay(PayReqVO req) throws UnsupportedEncodingException {
		if (req.getPrice() <= 0) {
			throw new CmdException(ErrorCode.ERROR_1006);
		}
		String domain = RequestUtil.getBasePath(request);
		AliPaySchema schema = payService.createAlipayOrder(req.getHeader().getUserId(), req.getPrice(), domain);
		String payUrl = MessageFormat.format("{0}pay/jump/alipay.do?payId={1}", domain, schema.getPayId());
		H5PayRespVO resp = new H5PayRespVO();
		resp.setPayId(schema.getPayId());
		resp.setPayUrl(payUrl);
		return resp;
	}

	@CmdAction
	@DocFlag("Apple支付接口")
	public ApplePayRespVO applePay(PayReqVO req) throws UnsupportedEncodingException {
		if (req.getPrice() <= 0) {
			throw new CmdException(ErrorCode.ERROR_1006);
		}
		PayRecord record = payService.createAppletOrder(req.getHeader().getUserId(), req.getPrice());
		ApplePayRespVO resp = new ApplePayRespVO();
		resp.setPayId(record.getId());
		return resp;
	}

	@CmdAction
	@DocFlag("交易订单主动查询")
	public QueryPayRecordRespVO queryPayRecord(QueryPayRecordReqVO req) throws AlipayApiException {
		PayRecord record = payService.getPayRecord(req.getPayId());
		if (!record.getUserId().equals(req.getHeader().getUserId())) {
			return new QueryPayRecordRespVO().pushErrorCode(ErrorCode.ERROR_503);
		}
		if (record.getStatus() == 1) {
			QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
			BeanUtils.copyProperties(record, resp);
			return resp;
		}
		// 主动查询
		if (record.getPayType() == 2) {
			PayConfig config = payService.getPayConfig(2);
			if (config == null) {
				throw new CmdException(ErrorCode.ERROR_1034);
			}
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					config.getAppId(), config.getPrivateKey(), "json", "UTF-8", config.getPublicKey(), "RSA2");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			request.setBizContent("{\"out_trade_no\":\"" + req.getPayId() + "\"}");
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			String result = response.getBody();
			Map<String, Object> resultMap = JSON.parseObject(response.getBody(),
					new TypeReference<Map<String, Object>>() {
					});
			result = resultMap.get("alipay_trade_query_response").toString();
			resultMap = JSON.parseObject(result, new TypeReference<Map<String, Object>>() {
			});
			if (!"TRADE_SUCCESS".equals(resultMap.get("trade_status"))) {
				LogUtil.logger.info("支付主动查询,支付状态未成功>>" + JSON.toJSONString(resultMap));
				QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
				BeanUtils.copyProperties(record, resp);
				return resp;
			}
			// 付款金额
			Double amount = StringUtil.toDouble(resultMap.get("total_amount"));
			String outTradeNo = resultMap.get("out_trade_no").toString();
			String tradeNo = resultMap.get("trade_no").toString();
			Integer gold = Double.valueOf(Double.valueOf(amount) * 100).intValue();
			Long code = payService.doTrade(Long.valueOf(outTradeNo), tradeNo, Double.valueOf(amount), gold);
			if (code > 0) {
				record = payService.getPayRecord(req.getPayId());
			}
			QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
			BeanUtils.copyProperties(record, resp);
			return resp;
		}
		if (record.getPayType() == 1) {
			// 微信充值
			PayConfig config = payService.getPayConfig(1);
			if (config == null) {
				throw new CmdException(ErrorCode.ERROR_1034);
			}
			Map<String, String> paraMap = new TreeMap<String, String>();
			paraMap.put("appid", config.getAppId());
			paraMap.put("mch_id", config.getSellerId());
			paraMap.put("out_trade_no", record.getId().toString());
			paraMap.put("nonce_str", JUUIDUtil.createUuid());
			String sign = WxPayUtil.getSign(paraMap, config.getPrivateKey());
			paraMap.put("sign", sign);
			String requestXML = WxPayUtil.getRequestXml(paraMap);
			String result = HttpUtil.post("https://api.mch.weixin.qq.com/pay/orderquery", requestXML).getHtml();
			LogUtil.logger.debug("响应报文：" + result);
			try {
				paraMap = XMLUtil.doXMLParse(result);
				String appId = paraMap.get("appid");
				if (!appId.equals(config.getAppId())) {
					LogUtil.logger.error("appid不匹配，订单放弃:" + paraMap);
					QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
					BeanUtils.copyProperties(record, resp);
					return resp;
				}
				if (!paraMap.get("result_code").equals("SUCCESS")) {
					LogUtil.logger.error("订单交易失败:" + paraMap);
					QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
					BeanUtils.copyProperties(record, resp);
					return resp;
				}
				// 付款金额
				Double amount = Double.valueOf(paraMap.get("total_fee")) / 100;
				String outTradeNo = paraMap.get("out_trade_no").toString();
				String tradeNo = paraMap.get("transaction_id").toString();
				Integer gold = Double.valueOf(Double.valueOf(amount) * 100).intValue();
				Long code = payService.doTrade(Long.valueOf(outTradeNo), tradeNo, Double.valueOf(amount), gold);
				if (code > 0) {
					record = payService.getPayRecord(req.getPayId());
				}
				QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
				BeanUtils.copyProperties(record, resp);
				return resp;
			} catch (Exception e) {

				QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
				BeanUtils.copyProperties(record, resp);
				return resp;
			}
		}

		if (record.getPayType() == 3) {
			List<String> packagers = clientAppService.getPackagers(1);
			if (StringUtil.isNullOrEmpty(packagers)) {
				throw new CmdException(ErrorCode.ERROR_1034);
			}
			// 苹果支付
			Map<String, PayAppleProduct> productMap = payService.getPayAppleProducts();
			if (StringUtil.isNullOrEmpty(productMap)) {
				throw new CmdException(ErrorCode.ERROR_1034);
			}
			Map<String, String> data = new HashMap<String, String>();
			data.put("receipt-data", req.getReceipt());
			String url = "https://buy.itunes.apple.com/verifyReceipt";
			UserAccount account = userAccountService.getUserAccountByUserId(record.getUserId());
			if (account.getReferrer() == 4) {
				url = "https://sandbox.itunes.apple.com/verifyReceipt";
			}
			HttpEntity httpEntity = HttpsUtil.post(url, JSON.toJSONString(data));
			LogUtil.logger.info("苹果支付返回报文>>" + httpEntity.getHtml());
			Map<String, Object> resultMap = JSON.parseObject(httpEntity.getHtml(),
					new TypeReference<Map<String, Object>>() {
					});
			Integer status = StringUtil.toInteger(resultMap.get("status"));
			if (status != 0) {
				return new QueryPayRecordRespVO().pushErrorCode(ErrorCode.ERROR_1048);
			}
			resultMap = JSON.parseObject(resultMap.get("receipt").toString(), new TypeReference<Map<String, Object>>() {
			});
			resultMap = JSON.parseObject(resultMap.get("in_app").toString(), new TypeReference<Map<String, Object>>() {
			});

			Integer quantity = StringUtil.toInteger(resultMap.get("quantity"));
			String productId = StringUtil.toString(resultMap.get("product_id"));
			String transactionId = StringUtil.toString(resultMap.get("transaction_id"));

			PayRecord payRecordFromTransactionId = payService.getPayRecordByTransNo(transactionId, 3);
			if (payRecordFromTransactionId != null) {
				throw new CmdException(ErrorCode.ERROR_1055);
			}
			PayAppleProduct product = productMap.get(productId);
			if (product == null) {
				throw new CmdException(ErrorCode.ERROR_1054);
			}
			Double amount = (double) (product.getPrice() * quantity);
			Integer gold = product.getGold() * quantity;
			Long code = payService.doTrade(Long.valueOf(req.getPayId()), transactionId, Double.valueOf(amount), gold);
			if (code > 0) {
				record = payService.getPayRecord(req.getPayId());
			}
			QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
			BeanUtils.copyProperties(record, resp);
			return resp;
		}
		QueryPayRecordRespVO resp = new QueryPayRecordRespVO();
		BeanUtils.copyProperties(record, resp);
		return resp;
	}
	
	@DocFlag("首充礼包接口")
	@CmdAction
	public FirstChargeRespVO FirstCharge(BaseReqVO reqVO) {
		PayRecord payRecord = payService.getPayRecordByUserId(reqVO.getHeader().getUserId());
		if (payRecord != null) {
			return new FirstChargeRespVO().pushErrorCode(ErrorCode.ERROR_3010);

		}
		FirstChargeRespVO respVO = new FirstChargeRespVO();
		respVO.setImageUrl1(
				"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E5%85%83%E5%AE%9D&hs=2&pn=0&spn=0&di=192428834290&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=2332307886%2C117064316&os=425175604%2C1957541589&simid=0%2C0&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=&bdtype=0&oriquery=%E5%85%83%E5%AE%9D&objurl=http%3A%2F%2Fpic.51yuansu.com%2Fpic%2Fcover%2F00%2F00%2F71%2F571038f22dc49_610.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3Bc8y7wgf7_z%26e3Bv54AzdH3FfvAzdH3Fe6esvsz22e_z%26e3Bip4s&gsm=0&islist=&querylist=");
		respVO.setImageUrl2(
				"https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=%E6%A3%92%E6%A3%92%E7%B3%96%E5%9B%BE%E7%89%87&step_word=&hs=2&pn=6&spn=0&di=142813836110&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=3544549841%2C3221529094&os=249511065%2C1159616770&simid=0%2C0&adpicid=0&lpn=0&ln=1784&fr=&fmq=1553589426040_R&fm=detail&ic=&s=undefined&hd=&latest=&copyright=&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F16%2F09%2F17%2F0857dc8f9d2c831.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Flafij3t_z%26e3Bv54AzdH3Ff7vwtAzdH3F8mdblmnl_z%26e3Bip4s&gsm=0&rpstart=0&rpnum=0&islist=&querylist=&force=undefined");
		respVO.setContent("首充即送1500元宝大礼包");
		return respVO;
	}
	

	public static void main(String[] args) {

	}

}
