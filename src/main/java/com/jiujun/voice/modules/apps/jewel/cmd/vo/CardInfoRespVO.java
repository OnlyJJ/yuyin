package com.jiujun.voice.modules.apps.jewel.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

/**
 * 我的银行卡
 * @author Shao.x
 * @date 2019年1月9日
 */
@SuppressWarnings("serial")
public class CardInfoRespVO extends BaseRespVO {
	@DocFlag("银行卡号")
	private String cardNO;
	
	@DocFlag("开户人")
	private String owner;
	
	@DocFlag("绑定的手机号")
	private String mobile;
	
	@DocFlag("开户银行")
	private String bank;
	
	@DocFlag("支行")
	private String subbranch;
	
	@DocFlag("地址")
	private String address;

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
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
