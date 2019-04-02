package com.jiujun.voice.modules.apps.game.dice.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.game.dice.domain.GameDiceQualer;
import com.jiujun.voice.modules.apps.game.dice.domain.GameDiceRecord;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class DiceDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 查询资质名单
	 * 
	 * @param roomId
	 * @return
	 */
	@CacheWrite(key = CacheConstants.DICE_QUALER_LIST, time = 600, fields = "roomId")
	public List<GameDiceQualer> getQualer(String roomId) {
		String sql = "select * from t_game_dice_qualer where roomId=? ";
		return jdbcHandle.query(GameDiceQualer.class, sql, roomId);
	}

	/**
	 * 取消资质
	 */
	@CacheWipe(key = CacheConstants.DICE_QUALER_LIST, fields = "roomId")
	public Long cancelQualer(String roomId, String userId) {
		String sql = "delete from t_game_dice_qualer where userId=?";
		return jdbcHandle.update(sql, userId);
	}

	/**
	 * 写入资质名单
	 */
	@CacheWipe(key = CacheConstants.DICE_QUALER_LIST, fields = "roomId")
	public void pushQualer(String roomId, List<String> userIds) {
		// 取消所有资质用户
		String sql = "delete from t_game_dice_qualer where roomId=? ";
		jdbcHandle.update(sql, roomId);
		if (StringUtil.isNullOrEmpty(userIds)) {
			return;
		}
		// 插入资质用户
		for (String userId : userIds) {
			GameDiceQualer qualer = new GameDiceQualer();
			qualer.setRoomId(roomId);
			qualer.setUserId(userId);
			jdbcHandle.insert(qualer);
		}
		return;
	}

	/**
	 * 记录摇骰子
	 */
	public Long pushDiceRecord(GameDiceRecord record) {
		return jdbcHandle.insert(record);
	}
}
