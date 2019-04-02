package com.jiujun.voice.modules.im.enm;
/**
 * 
 * @author Coody
 *
 */
public enum MsgEnum {
	
	
	/**
	 * 封号通知
	 */
	USER_FROZING(206,"封号通知"),
	/**
	 * 暂停歌曲
	 */
	STOP_MUSIC(205,"暂停歌曲"),
	/**
	 * 音乐列表表更
	 */
	MUSIC_CHANGE(204,"音乐列表变更"),
	/**
	 * 切换歌曲
	 */
	SWITCH_MUSIC(203,"切换歌曲"),
	/**
	 * 送礼播报
	 */
	SEND_GIFT_BROADCAST(202,"送礼播报"),
	/**
	 * 掉线提示
	 */
	LOGIN_CONFLICT(201,"掉线提示"),
	
	/**
	 * 摇骰子结果
	 */
	DICE_VALUE_SHOW(116,"摇骰子结果"),
	/**
	 * 摇骰子用户资质变更
	 */
	DICE_CHANGE(115,"摇骰子用户资质变更"),
	/**
	 * 抱用户上麦/下麦
	 */
	PICK_MIC(114,"抱用户上麦/下麦"),
	/**
	 * 刷新房间信息
	 */
	RESH_ROONINFO(113,"刷新房间信息"),
	/**
	 * 刷新话题
	 */
	RESH_THEME_MSG(112,"刷新话题"),
	/**
	 * 充值成功提示
	 */
	CHARGE_MSG(111,"充值成功提示"),
	/**
	 * 房间用户公屏消息
	 */
	USER_ROOM_MSG(110,"房间用户公屏消息"),
	/**
	 * 私聊消息
	 */
	PRIVATE_MSG(109,"私聊消息"),
	/**
	 * 房间行为通知，1:上下管理、2:禁言解禁、3:踢出房间
	 */
	ROOM_BEHAVIOR(108,"房间行为通知"),
	/** 刷新房间麦位 */
	REFRESH_MIC(107,"刷新房间麦位"),

	/** 刷新房间成员列表 */
	REFRESH_MEMBER(106,"刷新房间成员列表"),

	/** 魅力升级 */
	UP_CHARM_LEVEL(105,"魅力升级"),

	/** 财富升级 */
	UP_RICH_LEVEL(104,"财富升级"),

	/** 送礼 */
	SEND_GIFT(103,"送礼"),

	/** 进入房间 */
	IN_ROOM(102,"进入房间"),

	/** 好友关系通知 */
	RELATION_NOTICE(101,"好友关系通知"),

	/** 常规系统通知 */
	SYSTEM_NOTICE(100,"常规系统通知"),;

	private int type;

	private String remark;

	private MsgEnum(int type, String remark) {
		this.type = type;
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setType(int type) {
		this.type = type;
	}

}
