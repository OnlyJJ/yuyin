package com.jiujun.voice.modules.apps.pay.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class WxPayRespVO extends BaseRespVO{

	@DocFlag("支付订单号，可用于主动查询")
	private Long payId;
	@DocFlag("appId")
	private String appId;
	@DocFlag("商户ID")
	private String partnerId ;
	@DocFlag("微信订单ID")
	private String prepayId ;
	
	@DocFlag("签名包")
	private String packageValue ;
	
	@DocFlag("随机字符串")
	private String nonceStr ;
	
	@DocFlag("时间戳")
	private String timeStamp ;
	
	@DocFlag("签名")
	private String sign;

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getPackageValue() {
		return packageValue;
	}

	public void setPackageValue(String packageValue) {
		this.packageValue = packageValue;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	
}

