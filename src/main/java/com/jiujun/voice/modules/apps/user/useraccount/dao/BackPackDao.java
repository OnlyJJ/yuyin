package com.jiujun.voice.modules.apps.user.useraccount.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.user.useraccount.domain.BackpackInfo;
import com.jiujun.voice.modules.apps.user.useraccount.domain.BackpackRecord;

@Repository
public class BackPackDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 加载物品详情
	 * @param userId
	 * @param correId  关联值，如：礼物ID、道具ID
	 * @param type  1礼物 2道具
	 * @return
	 */
	public BackpackInfo getBackPackInfo(String userId, String correId, Integer type){
		String sql="select * from t_backpack where userId=? and correId=? and type=? limit 1";
		return jdbcHandle.queryFirst(BackpackInfo.class, sql, userId,correId,type);
	}
	/**
	 * 加载我的背包列表
	 * 
	 * @param userId
	 * @return
	 */
	@CacheWrite(key = CacheConstants.BACKPACK_INFO, fields = "userId", time = 600)
	public List<BackpackInfo> getBackPacks(String userId) {
		String sql = "select * from t_backpack where userId=? and num>0 order by createTime desc ";
		return jdbcHandle.query(BackpackInfo.class, sql, userId);
	}

	/**
	 * 减少背包商品
	 */
	@CacheWipe(key = CacheConstants.BACKPACK_INFO, fields = "userId")
	public Long subBackpack(String userId, String correId, Integer type, Integer changeNum) {
		String sql = "update t_backpack set num=num-? where userId=? and correId=? and type=? and num>=?";
		Long code= jdbcHandle.update(sql, changeNum, userId, correId, type, changeNum);
		return code;
	}

	/**
	 * 增加背包商品
	 */
	@CacheWipe(key = CacheConstants.BACKPACK_INFO, fields = "userId")
	public Long addBackpack(String userId, String correId, Integer type, Integer changeNum) {
		if (changeNum < 0) {
			throw new CmdException(ErrorCode.ERROR_3007);
		}
		BackpackInfo backpack = new BackpackInfo();
		backpack.setNum(changeNum);
		backpack.setCorreId(correId);
		backpack.setType(type);
		backpack.setUserId(userId);
		return jdbcHandle.saveOrUpdate(backpack, "num");
	}
	
	/**
	 * 插入变更记录
	 */
	public Long addChangeRecord(BackpackRecord record){
		return jdbcHandle.insert(record);
	}
}
