package com.jiujun.voice.modules.apps.pay.schema;

import com.jiujun.voice.common.model.SchemaModel;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class AliPaySchema extends SchemaModel{

	private Long payId;
	
	private String data;
	
	private String h5Form;
	
	
	
	

	
	
	public String getH5Form() {
		return h5Form;
	}

	public void setH5Form(String h5Form) {
		this.h5Form = h5Form;
	}

	public Long getPayId() {
		return payId;
	}

	public void setPayId(Long payId) {
		this.payId = payId;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
}
