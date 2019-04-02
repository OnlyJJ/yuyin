package com.jiujun.voice.modules.apps.pay.cmd.vo;

import java.util.Date;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class QueryPayRecordRespVO extends BaseRespVO{

	
	
	@DocFlag("用户ID")
	private String userId;
	@DocFlag("充值金额")
	private Double price;
	@DocFlag("充值状态，0待处理 1已完成 ")
	private Integer status;
	@DocFlag("第三方流水号")
	private String tradeNo;
	@DocFlag("充值类型,1微信APP 2支付宝APP")
	private Integer payType;
	@DocFlag("创建时间")
	private Date createTime;
	@DocFlag("更新时间")
	private Date updateTime;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
