package com.jiujun.voice.modules.apps.pay.domain;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

@SuppressWarnings("serial")
@DBTable("t_pay_apple_product")
public class PayAppleProduct extends DBModel{

	
	private String productId;
	private Integer price;
	private Integer gold;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getGold() {
		return gold;
	}
	public void setGold(Integer gold) {
		this.gold = gold;
	}
	
	
	

}
