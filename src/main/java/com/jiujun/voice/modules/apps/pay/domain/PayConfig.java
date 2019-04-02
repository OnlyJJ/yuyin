package com.jiujun.voice.modules.apps.pay.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_pay_config")
public class PayConfig extends DBModel{

	private String appId;
	private String sellerId;
	private String publicKey;
	private String privateKey;
	private String notifyUrl;
	private String returnUrl;
	private Integer type;
	private Date createTime;
	
	
	
	
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "PayConfig [appId=" + appId + ", publicKey=" + publicKey + ", privateKey=" + privateKey + ", notifyUrl="
				+ notifyUrl + ", returnUrl=" + returnUrl + ", type=" + type + ", createTime=" + createTime + "]";
	}
	
	

}
