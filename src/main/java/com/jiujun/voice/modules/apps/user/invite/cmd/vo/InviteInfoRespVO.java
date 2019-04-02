package com.jiujun.voice.modules.apps.user.invite.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

@SuppressWarnings("serial")
public class InviteInfoRespVO extends BaseRespVO{

	@DocFlag("是否允许被邀请,1是，0否")
	private Integer isNewer;
	
	@DocFlag("邀请码")
	private String inviteCode;
	
	@DocFlag("邀请链接")
	private String inviteUrl;
	
	@DocFlag(value="累计元宝",docFieldValue="0")
	private Integer ingot;
	
	@DocFlag(value="累计金币",docFieldValue="0")
	private Integer gold;
	
	
	

	public Integer getIsNewer() {
		return isNewer;
	}

	public void setIsNewer(Integer isNewer) {
		this.isNewer = isNewer;
	}

	public String getInviteUrl() {
		return inviteUrl;
	}

	public void setInviteUrl(String inviteUrl) {
		this.inviteUrl = inviteUrl;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Integer getIngot() {
		return ingot;
	}

	public void setIngot(Integer ingot) {
		this.ingot = ingot;
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
	}
	
	
}


