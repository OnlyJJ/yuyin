package com.jiujun.voice.modules.apps.room.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;


/**
 * 送礼记录表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_send_gift_record")
public class SendGiftRecord extends DBModel {
	
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 送礼的房间
	 */
	private String roomId;
	/**
	 * 送礼用户id
	 */
	private String fromUserId;
	/**
	 * 收礼用户id
	 */
	private String toUserId;
	/**
	 * 礼物id
	 */
	private Integer giftId;
	/**
	 * 送礼数量
	 */
	private Integer num;
	/**
	 * 本次送礼的财富值
	 */
	private Integer exp;
	/**
	 * 本次送礼的魅力值
	 */
	private Integer charm;
	/**
	 * 本次送礼的礼物兑换钻石值
	 */
	private Integer jewel;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
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

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public Integer getGiftId() {
		return this.giftId;
	}
	
	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}
	public Integer getNum() {
		return this.num;
	}
	
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getExp() {
		return this.exp;
	}
	
	public void setExp(Integer exp) {
		this.exp = exp;
	}
	public Integer getCharm() {
		return this.charm;
	}
	
	public void setCharm(Integer charm) {
		this.charm = charm;
	}
	public Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getJewel() {
		return jewel;
	}

	public void setJewel(Integer jewel) {
		this.jewel = jewel;
	}
	
}
