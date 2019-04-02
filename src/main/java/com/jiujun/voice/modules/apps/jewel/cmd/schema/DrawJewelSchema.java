package com.jiujun.voice.modules.apps.jewel.cmd.schema;


import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;

/**
 * 我的收益
 * @author Shao.x
 * @date 2019年1月9日
 */
@SuppressWarnings("serial")
public class DrawJewelSchema extends SchemaModel {

	@DocFlag("提现钻石")
	private int drawJewel;
	
	@DocFlag("提取状态，0-待审核，1-成功，2-失败 3待打款")
	private int status;
	
	@DocFlag("提取时间")
	private String time;

	public long getDrawJewel() {
		return drawJewel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setDrawJewel(int drawJewel) {
		this.drawJewel = drawJewel;
	}

	 
	
	
}
