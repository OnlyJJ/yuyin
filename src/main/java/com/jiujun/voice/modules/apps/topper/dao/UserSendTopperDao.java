package com.jiujun.voice.modules.apps.topper.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.jdbc.process.PagerAble;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.modules.apps.topper.domain.UserSendTopper;
/**
 * @author Coody
 *
 */
@Repository
public class UserSendTopperDao {

	@Resource
	JdbcHandle jdbcHandle;

	public Integer saveUserSendToppers(List<UserSendTopper> toppers) {
		return jdbcHandle.batchSaveOrUpdate(toppers, "exp", "num");
	}

	public List<UserSendTopper> getUserReceiveGiftTopper(String toUserId) {
		String sql = "select * from t_user_send_topper where fromUserId=0 and toUserId=? and roomId=0 and giftId>0 and type=0";
		return jdbcHandle.query(UserSendTopper.class, sql, toUserId);
	}

	/**
	 * 分页查询财富榜
	 * 
	 * @param type
	 *            0总榜，1日榜 2周榜 3月榜
	 * @param pager
	 * @return
	 */
	@CacheWrite
	public Pager getExpTopperPager(Integer type, Pager pager) {
		String dayCode = "0";
		switch (type) {
		case 1:
			dayCode = DateUtils.getDayCode();
			break;
		case 2:
			dayCode = DateUtils.getWeekCode(0);
			break;
		case 3:
			dayCode = DateUtils.getMonthCode(0);
			break;
		default:
			break;
		}
		String sql = "select * from t_user_send_topper where type=? and toUserId=? and roomId=? and giftId=? and dayCode=? and fromUserId<>? order by exp desc ";
		pager = new PagerAble(UserSendTopper.class, sql, pager, type, "0", "0", 0, dayCode, "0").invoke();
		return pager;
	}

	@CacheWrite
	public Integer getExpRank(String userId, Integer type) {
		String dayCode = "0";
		switch (type) {
		case 1:
			dayCode = DateUtils.getDayCode();
			break;
		case 2:
			dayCode = DateUtils.getWeekCode(0);
			break;
		case 3:
			dayCode = DateUtils.getMonthCode(0);
			break;
		default:
			break;
		}
		String sql = "select * from t_user_send_topper where type=? and toUserId=? and roomId=? and giftId=? and dayCode=? and fromUserId<>? and fromUserId= ?";
		UserSendTopper topper = jdbcHandle.queryFirst(UserSendTopper.class, sql,type, "0", "0", 0, dayCode, "0", userId);
		if(topper==null){
			return 0;
		}
		Long currentExp =  topper.getExp();
		sql = "select count(*) from t_user_send_topper where type=? and toUserId=? and roomId=? and giftId=? and dayCode=? and fromUserId<>? and exp > ?";
		Integer rank = jdbcHandle.queryFirst(Integer.class, sql,type, "0", "0", 0, dayCode, "0", currentExp);
		rank++;
		return rank;
	}

	/**
	 * 分页查询魅力榜
	 * 
	 * @param type
	 *            0总榜，1日榜 2周榜 3月榜
	 * @param pager
	 * @return
	 */
	@CacheWrite
	public Pager getCharmTopperPager(Integer type, Pager pager) {
		String dayCode = "0";
		switch (type) {
		case 1:
			dayCode = DateUtils.getDayCode();
			break;
		case 2:
			dayCode = DateUtils.getWeekCode(0);
			break;
		case 3:
			dayCode = DateUtils.getMonthCode(0);
			break;
		default:
			break;
		}
		String sql = "select * from t_user_send_topper where type=? and fromUserId=? and roomId=? and giftId=? and dayCode=? and toUserId<>? order by exp desc ";
		pager = new PagerAble(UserSendTopper.class, sql, pager, type, "0", "0", 0, dayCode, "0").invoke();
		return pager;
	}
	
	
	@CacheWrite
	public Integer getCharmRank(String userId, Integer type) {
		String dayCode = "0";
		switch (type) {
		case 1:
			dayCode = DateUtils.getDayCode();
			break;
		case 2:
			dayCode = DateUtils.getWeekCode(0);
			break;
		case 3:
			dayCode = DateUtils.getMonthCode(0);
			break;
		default:
			break;
		}
		String sql = "select * from t_user_send_topper where type=? and fromUserId=? and roomId=? and giftId=? and dayCode=? and toUserId<>? and toUserId= ?";
		UserSendTopper topper = jdbcHandle.queryFirst(UserSendTopper.class, sql,type, "0", "0", 0, dayCode, "0", userId);
		if(topper==null){
			return 0;
		}
		Long currentExp = topper.getExp();
		sql = "select count(*) from t_user_send_topper where type=? and fromUserId=? and roomId=? and giftId=? and dayCode=? and toUserId<>? and exp > ?";
		Integer rank = jdbcHandle.queryFirst(Integer.class, sql, type,"0", "0", 0, dayCode, "0", currentExp);
		rank++;
		return rank;
	}
}
