package com.jiujun.voice.modules.apps.user.useraccount.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.DBModel;

@SuppressWarnings("serial")
public class BackPackSchema extends DBModel{

	@DocFlag("物品名称")
	private String name;
	@DocFlag("物品图标")
	private String ico;
	@DocFlag("物品特效ID")
	private Integer specialId;
	@DocFlag("物品价格")
	private Integer price;
	@DocFlag("物品数量")
	private Integer num;
	@DocFlag("实际人民币价格")
	private Double priceForRMB;
	@DocFlag("物品源ID")
	private String correId;
	@DocFlag("类型：1礼物 2道具")
	private Integer type;
	@DocFlag("礼物类型，1普通礼物 2元宝礼物")
	private Integer giftType;
	
	
	
	public Integer getGiftType() {
		return giftType;
	}
	public void setGiftType(Integer giftType) {
		this.giftType = giftType;
	}
	public String getCorreId() {
		return correId;
	}
	public void setCorreId(String correId) {
		this.correId = correId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIco() {
		return ico;
	}
	public void setIco(String ico) {
		this.ico = ico;
	}
	public Integer getSpecialId() {
		return specialId;
	}
	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Double getPriceForRMB() {
		return priceForRMB;
	}
	public void setPriceForRMB(Double priceForRMB) {
		this.priceForRMB = priceForRMB;
	}
	
	
	
}
