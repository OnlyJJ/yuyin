package com.jiujun.voice.modules.apps.jewel.enums;

/**
 * 
 * @author Coody
 *
 */
public class Jewel {

	/**
	 * 提取状态，0-审核中，1-成功，2-失败  
	 * @author Shao.x
	 * @date 2018年12月3日
	 */
	public enum Status {
		
		/**
		 * -待审核
		 */
		VERIFY(0),
		/**
		 * -待打款
		 */
		PAYING(3),
		/**
		 * 1-成功
		 */
		SUCCESS(1),
		/**
		 * 2-失败
		 */
		FAIL(2);

		private int value;

		Status(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
	
	
}
