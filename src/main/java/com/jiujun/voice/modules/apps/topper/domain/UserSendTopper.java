package com.jiujun.voice.modules.apps.topper.domain;

import java.util.Date;

import com.jiujun.voice.common.jdbc.annotation.DBTable;
import com.jiujun.voice.common.model.DBModel;

/**
 * 用户送礼统计表
 * @author Coody
 *
 */
@SuppressWarnings("serial")
@DBTable("t_user_send_topper")
public class UserSendTopper extends DBModel{

	/**
	 * 赠送者ID
	 */
	private String fromUserId;
	/**
	 * 收礼者
	 */
	private String toUserId;
	/**
	 * 日期
	 */
	private String dayCode;
	/**
	 * 房间ID
	 */
	private String roomId;
	
	/**
	 * 礼物ID
	 */
	private Integer giftId;
	/**
	 * 经验、魅力值、财富值
	 */
	private Long exp;
	/**
	 * 数量
	 */
	private Integer num;
	/**
	 * 类型 0总榜，1日榜 2周榜 3月榜
	 */
	private Integer type;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	
	public Integer getGiftId() {
		return giftId;
	}
	public void setGiftId(Integer giftId) {
		this.giftId = giftId;
	}
	public Long getExp() {
		return exp;
	}
	public void setExp(Long exp) {
		this.exp = exp;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
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
	public String getDayCode() {
		return dayCode;
	}
	public void setDayCode(String dayCode) {
		this.dayCode = dayCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	 

}
