package com.jiujun.voice.modules.caller.entity;

import com.jiujun.voice.common.model.EntityModel;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SendGiftEntity extends EntityModel{

	/**
	 * 送礼的用户
	 */
	private String fromUserId;
	/**
	 * 收礼的用户
	 */
	private String toUserId;
	/**
	 * 礼物ID
	 */
	private Integer giftId;
	/**
	 * 房间号
	 */
	private String roomId;
	/**
	 * 礼物数量
	 */
	private Integer num;
	
	
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
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}
