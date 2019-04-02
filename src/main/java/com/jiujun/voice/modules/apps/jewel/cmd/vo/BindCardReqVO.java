package com.jiujun.voice.modules.apps.jewel.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 绑定银行卡
 * @author Shao.x
 * @date 2019年1月14日
 */
@SuppressWarnings("serial")
public class BindCardReqVO extends BaseReqVO {
	@ParamCheck
	@DocFlag("银行卡号")
	private String cardNo;
	
	@ParamCheck
	@DocFlag("开户人")
	private String owner;
	
	@ParamCheck
	@DocFlag("开户银行")
	private String bank;
	
	@DocFlag("支行")
	private String subbranch;
	
	@ParamCheck
	@DocFlag("地址")
	private String address;
	

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getSubbranch() {
		return subbranch;
	}

	public void setSubbranch(String subbranch) {
		this.subbranch = subbranch;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	} 


	
}
