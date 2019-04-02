package com.jiujun.voice.modules.apps.room.enums;
/**
 * @author Coody
 *
 */
public class MicEnum {

	/**
	 * 房间角色
	 * @author Shao.x
	 * @date 2018年12月3日
	 */
	public enum Status {
		
		/**
		 * 0-正常
		 */
		NORMAL(0),
		/**
		 * 1-禁麦
		 */
		BAN(1),
		/**
		 * 2-锁麦
		 */
		LOCK(2);

		private int value;

		Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	
}
