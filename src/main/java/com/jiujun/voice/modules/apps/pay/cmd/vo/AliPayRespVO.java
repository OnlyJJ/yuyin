package com.jiujun.voice.modules.apps.pay.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class AliPayRespVO extends BaseRespVO{

	@DocFlag("支付订单号，可用于主动查询")
	private Long payId;
	
	@DocFlag("支付相关数据")
	private String data;

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
	
}

