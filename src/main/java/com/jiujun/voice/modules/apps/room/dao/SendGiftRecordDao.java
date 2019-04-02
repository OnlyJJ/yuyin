package com.jiujun.voice.modules.apps.room.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.room.domain.SendGiftRecord;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class SendGiftRecordDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public Long insert(SendGiftRecord sgr) {
		return jdbcHandle.insert(sgr);
	}
	
	/**
	 * 获取房间送礼记录
	 * @author Shao.x
	 * @date 2018年12月21日
	 * @param roomId 
	 * @param beginTime 开始时间，格式：yyyy-MM-dd HH:mm:ss
	 * @param endTime 结束时间
	 * @return
	 */
	public List<SendGiftRecord> getSendGiftRecord(String roomId, Date beginTime, Date endTime) {
		String sql = "select * from t_send_gift_record where roomId=? "
				+ " and createTime >=? and createTime<=? order by createTime desc";
		return jdbcHandle.query(SendGiftRecord.class, sql, roomId, beginTime, endTime);
	}
	
	/**
	 * 个人收礼记录
	 * @author Shao.x
	 * @date 2019年1月14日
	 * @param userId
	 * @param limit 个人记录最大限制10000
	 * @return
	 */
	public List<SendGiftRecord> getUserReciveGift(String userId, int limit) {
		String sql = "select * from t_send_gift_record where toUserId=? order by createTime desc limit ?";
		return jdbcHandle.query(SendGiftRecord.class, sql, userId, limit);
	}
 }