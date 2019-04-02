package com.jiujun.voice.modules.apps.game.dice.schema;

import com.jiujun.voice.common.model.SchemaModel;

/**
 * 摇骰子消息体
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class DiceSchema extends SchemaModel{

	private String roomId;
	private String userId;
	private Integer diceValue;
	private Integer frozeTime;
	private String remark;

	
	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDiceValue() {
		return diceValue;
	}

	public void setDiceValue(Integer diceValue) {
		this.diceValue = diceValue;
	}

	public Integer getFrozeTime() {
		return frozeTime;
	}

	public void setFrozeTime(Integer frozeTime) {
		this.frozeTime = frozeTime;
	}
	
	
}
