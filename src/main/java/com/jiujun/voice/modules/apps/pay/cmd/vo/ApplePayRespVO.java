package com.jiujun.voice.modules.apps.pay.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

@SuppressWarnings("serial")
public class ApplePayRespVO extends BaseRespVO{

	@DocFlag("支付订单号，可用于主动查询")
	private Long payId;

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}
	
	
}
