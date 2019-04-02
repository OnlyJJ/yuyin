package com.jiujun.voice.modules.apps.user.userinfo.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ModifyUserInfoReqVO extends BaseReqVO{

	@ParamCheck
	@DocFlag("昵称")
	private String name;
	@DocFlag("头像地址")
	private String head;
	@ParamCheck
	@DocFlag("性别，1男 0女")
	private Integer sex;
	@DocFlag("生日，yyyy-MM-dd")
	private String birth;
	@DocFlag("邀请码")
	private String inviteCode;
	@DocFlag("个性签名")
	private String sign;
	
	
	
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	
	
}
