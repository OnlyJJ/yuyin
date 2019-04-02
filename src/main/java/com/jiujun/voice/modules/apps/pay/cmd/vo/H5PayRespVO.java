package com.jiujun.voice.modules.apps.pay.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class H5PayRespVO extends BaseRespVO{

	@DocFlag("支付订单号，可用于主动查询")
	private Long payId;
	@DocFlag("跳转地址")
	private String payUrl;
	public Long getPayId() {
		return payId;
	}
	public void setPayId(Long payId) {
		this.payId = payId;
	}
	public String getPayUrl() {
		return payUrl;
	}
	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}
	
	
}

