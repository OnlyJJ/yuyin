package com.jiujun.voice.modules.apps.pay.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class QueryPayRecordReqVO extends BaseReqVO{

	@ParamCheck
	@DocFlag("订单号")
	private Long payId;
	
	@DocFlag("适用于IOS的订单凭证")
	private String receipt;
	
	
	
	
	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}
	
	
	
}
