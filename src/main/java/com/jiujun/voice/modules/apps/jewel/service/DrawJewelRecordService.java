package com.jiujun.voice.modules.apps.jewel.service;

import com.jiujun.voice.common.jdbc.entity.Pager;

/**
 * 
 * @author Coody
 *
 */
public interface DrawJewelRecordService {
	
	/**
	 * 用户提款申请
	 * @author Shao.x
	 * @date 2019年1月14日
	 * @param userId
	 * @param drawJewel
	 * @return
	 */
	Long draw(String userId, Integer drawJewel);
	
	/**
	 * 获取用户正在提现的总钻石
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param userId
	 * @return
	 */
	Long getFrozenJewel(String userId);
	
	/**
	 * 获取用户提现记录
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @return
	 */
	Pager getDrawRecord(String userId, int pageSize, int pageNo);
	
	/**
	 * 提现审核结果回调
	 * @author Shao.x
	 * @date 2019年1月16日
	 * @param userId 提现用户
	 * @param recordId 提现记录id
	 * @param status 状态，1-成功，2-失败
	 * @return
	 */
	Long drawResultBack(String userId, int recordId, int status);
}
