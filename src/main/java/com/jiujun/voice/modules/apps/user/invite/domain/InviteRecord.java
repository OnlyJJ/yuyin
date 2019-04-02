package com.jiujun.voice.modules.apps.user.invite.domain;

import java.util.Date;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 邀请记录表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_invite_record")
public class InviteRecord extends DBModel{

	
	/**
	 * 邀请者userId
	 */
	@DocFlag("邀请者")
	private String inviter;
	/**
	 * 注册者userId
	 */
	@DocFlag("注册者、被邀请者")
	private String registranter;
	/**
	 * 状态  0无效 1有效
	 */
	@DocFlag("邀请是否有效,0无效 1有效")
	private Integer status;
	/**
	 * 描述
	 */
	@DocFlag("描述")
	private String remark;
	
	/**
	 * 邀请日期
	 */
	@DocFlag("邀请日期")
	private String dayCode;
	/**
	 * 创建时间
	 */
	@DocFlag("创建/邀请时间")
	private Date createTime;
	
	
	
	
	public String getDayCode() {
		return dayCode;
	}
	public void setDayCode(String dayCode) {
		this.dayCode = dayCode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getInviter() {
		return inviter;
	}
	public void setInviter(String inviter) {
		this.inviter = inviter;
	}
	public String getRegistranter() {
		return registranter;
	}
	public void setRegistranter(String registranter) {
		this.registranter = registranter;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
}
