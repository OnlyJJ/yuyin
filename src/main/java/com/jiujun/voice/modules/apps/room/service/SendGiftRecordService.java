package com.jiujun.voice.modules.apps.room.service;

import java.util.Date;
import java.util.List;

import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.modules.apps.room.domain.SendGiftRecord;
/**
 * @author Coody
 *
 */
public interface SendGiftRecordService {
	
	/**
	 *  送礼
	 * @param roomId
	 * @param fromUserId
	 * @param toUserId
	 * @param giftId
	 * @param num
	 * @param isExtends 背包不足是否用金币(元宝)补偿交易
	 */
	void sendGift(String roomId, String fromUserId, String toUserId, int giftId, int num,boolean isExtends);
	
	/**
	 * 获取房间送礼记录
	 * @author Shao.x
	 * @date 2018年12月20日
	 * @param roomId
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	List<SendGiftRecord> getSendGiftRecord(String roomId, Date beginTime, Date endTime);
	
	/**
	 * 获取房间送礼记录
	 * @author Shao.x
	 * @date 2018年12月20日
	 * @param roomId
	 * @param type 类型，1-日榜，2-周榜
	 * @param pageSize 单页容量
	 * @param pageNO 页码
	 * @return
	 */
	Pager getRoomGiftRecord(String roomId, int type, int pageSize, int pageNO);
	
	/**
	 * 获取用户个人收礼记录
	 * @author Shao.x
	 * @date 2019年1月14日
	 * @param userId
	 * @param pageSize
	 * @param pageNO
	 * @return
	 */
	Pager getUserGiftRecord(String userId, int pageSize, int pageNO);
	
	/**
	 * 插入送礼记录
	 */
	public Long insert(SendGiftRecord sgr) ;

	/**
	 * 赠送背包礼物
	 * 
	 * @param fromUserId
	 *            赠送者
	 * @param toUserId
	 *            接受者
	 * @param roomId
	 *            房间
	 * @param correId
	 *            礼物或道具ID
	 * @param type
	 *            1礼物 2道具
	 * @param num
	 *            赠送数量
	 * @return
	 */
	public void sendGiftFromBackpack(String fromUserId, String toUserId, String roomId, String correId, Integer type,
			Integer num) ;
}
