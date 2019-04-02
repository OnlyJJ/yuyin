package com.jiujun.voice.modules.apps.room.cmd.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;

/**
 * 用户收礼记录
 * @author Shao.x
 * @date 2018年12月20日
 */
@SuppressWarnings("serial")
public class UserGiftRecordSchema extends SchemaModel {
	 
	@DocFlag("礼物id")
	private String giftId;
	
	@DocFlag("礼物名称")
	private String giftName;
	
	@DocFlag("礼物图片")
	private String giftImg;
	
	@DocFlag("礼物数量")
	private Integer giftNum;

	public String getGiftId() {
		return giftId;
	}

	public void setGiftId(String giftId) {
		this.giftId = giftId;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getGiftImg() {
		return giftImg;
	}

	public void setGiftImg(String giftImg) {
		this.giftImg = giftImg;
	}

	public Integer getGiftNum() {
		return giftNum;
	}

	public void setGiftNum(Integer giftNum) {
		this.giftNum = giftNum;
	}
	
	
}
