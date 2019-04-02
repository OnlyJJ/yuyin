package com.jiujun.voice.modules.apps.room.schema;

import com.jiujun.voice.common.model.SchemaModel;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SendGiftMsgSchema extends SchemaModel {
	/**
	 * 送礼房间
	 */
	private String roomId;
	/**
	 * 送礼用户id
	 */
	private String fromUserId;
	/**
	 * 送礼用户昵称
	 */
	private String fromUserName;
	/**
	 * 收礼用户id
	 */
	private String toUserId;
	/**
	 * 送礼用户昵称
	 */
	private String toUserName;
	/**
	 * 礼物id
	 */
	private Integer giftId;
	/**
	 * 礼物名称
	 */
	private String giftName;
	/**
	 * 送礼数量
	 */
	private Integer giftNum;
	/**
	 * 礼物图片
	 */
	private String giftImg;
	
	/**
	 * 礼物特效ID
	 * @return
	 */
	private Integer specialId;
	
	
	
	public Integer getSpecialId() {
		return specialId;
	}

	public void setSpecialId(Integer specialId) {
		this.specialId = specialId;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
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

	public Integer getGiftNum() {
		return giftNum;
	}

	public void setGiftNum(Integer giftNum) {
		this.giftNum = giftNum;
	}

	public String getGiftImg() {
		return giftImg;
	}

	public void setGiftImg(String giftImg) {
		this.giftImg = giftImg;
	}
	
}
