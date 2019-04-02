package com.jiujun.voice.modules.apps.jewel.cmd.schema;


import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.model.SchemaModel;

/**
 * 收礼记录
 * @author Shao.x
 * @date 2019年1月9日
 */
@SuppressWarnings("serial")
public class ReceiveGiftSchema extends SchemaModel {

	@DocFlag("送礼用户id")
	private String fromUserId;
	
	@DocFlag("送礼用户昵称")
	private String fromName;
	
	@DocFlag("礼物id")
	private Integer giftId;
	
	@DocFlag("礼物名称")
	private String giftName;
	
	@DocFlag("礼物图片")
	private String giftImg;
	
	@DocFlag("礼物数量")
	private Integer giftNum;
	
	@DocFlag("送礼时间，时间戳")
	private Long sendTime;

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Integer getGiftId() {
		return giftId;
	}

	public void setGiftId(Integer giftId) {
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

	public Long getSendTime() {
		return sendTime;
	}

	public void setSendTime(Long sendTime) {
		this.sendTime = sendTime;
	}
	
}
