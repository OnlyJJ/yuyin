package com.jiujun.voice.modules.apps.gift.domain;

import java.util.Date;

import com.jiujun.voice.common.config.YmlConfigBuilder;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_gift_info")
public class GiftInfo extends DBModel{

	@DocFlag("礼物ID")
	private Integer giftId;
	@DocFlag("礼物名称")
	private String name;
	@DocFlag("礼物图标")
	private String ico;
	@DocFlag("礼物特效ID")
	private Integer specialId;
	@DocFlag("礼物价格")
	private Integer price;
	@DocFlag("兑换比例，百分比整数")
	private Integer rate;
	@DocFlag("赠送者获得经验")
	private Integer expForSender;
	@DocFlag("接受者获得经验")
	private Integer expForReceiver;
	@DocFlag("实际人民币价格")
	private Double priceForRMB;
	@DocFlag("是否允许购买 0不允许 1允许")
	private Integer buyable;
	@DocFlag("状态 0不可用 1可用")
	private Integer status;
	@DocFlag("是否在礼物栏显示 0不显示  1显示")
	private Integer isShow;
	@DocFlag("礼物类型，1普通礼物 2元宝礼物")
	private Integer giftType;
	@DocFlag("创建时间")
	private Date createTime;
	
	
	
	
	public Integer getGiftType() {
		return giftType;
	}
	public void setGiftType(Integer giftType) {
		this.giftType = giftType;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getExpForSender() {
		return expForSender;
	}
	public void setExpForSender(Integer expForSender) {
		this.expForSender = expForSender;
	}
	public Integer getExpForReceiver() {
		return expForReceiver;
	}
	public void setExpForReceiver(Integer expForReceiver) {
		this.expForReceiver = expForReceiver;
	}
	public Integer getSpecialId() {
		return specialId;
	}
	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}
	public Double getPriceForRMB() {
		return priceForRMB;
	}
	public void setPriceForRMB(Double priceForRMB) {
		this.priceForRMB = priceForRMB;
	}
	public Integer getGiftId() {
		return giftId;
	}
	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIco() {
		return YmlConfigBuilder.getProperty(Constants.FILE_DOMAIN) + ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Integer getBuyable() {
		return buyable;
	}
	public void setBuyable(Integer buyable) {
		this.buyable = buyable;
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
