package com.jiujun.voice.modules.apps.room.enums;
/**
 * 
 * @author Coody
 *
 */
public class RoomEnum {

	/**
	 * 房间角色
	 * @author Shao.x
	 * @date 2018年12月3日
	 */
	public enum Role {
		
		/**
		 * 1-房主
		 */
		OWNER(1),
		/**
		 * 2-管理员
		 */
		MANAGER(2);

		private int identity;

		Role(int identity) {
			this.identity = identity;
		}

		public int getIdentity() {
			return identity;
		}
	}
	
	/**
	 * 房间行为
	 * @author Shao.x
	 * @date 2018年12月10日
	 */
	public enum Behavior {
		
		/**
		 * 麦克风
		 */
		MIC(1),
		/**
		 * 2-发言
		 */
		TALK(2),
		/**
		 * 踢出房间
		 */
		OUTROOM(3);

		private int value;

		Behavior(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 麦位类型
	 * @author Shao.x
	 * @date 2018年12月13日
	 */
	public enum MicType {
		
		/**
		 * 1-主麦
		 */
		MASTER(1),
		/**
		 * 0-普通
		 */
		GENERAL(0);

		private int value;

		MicType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	/**
	 * 房间属性-公开or私密
	 * @author Shao.x
	 * @date 2019年1月14日
	 */
	public enum Feature {
		
		/**
		 * 0-公开
		 */
		OPEN(0),
		/**
		 * 0-私密
		 */
		HIDE(1);

		private int value;

		Feature(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	
	public enum EnjoyType {
		/**
		 * 偏好类型，其他
		 */
		OTHER("OTHER");

		private String value;

		EnjoyType(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum Grade {
		/**
		 * 系统房间
		 */
		SYS(1),
		/**
		 * 普通房间
		 */
		GENERAL(0);

		private int value;

		Grade(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
}
