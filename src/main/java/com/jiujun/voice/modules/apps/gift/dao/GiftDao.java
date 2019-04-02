package com.jiujun.voice.modules.apps.gift.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class GiftDao {

	@Resource
	JdbcHandle jdbcHandle;

	@CacheWrite(key = CacheConstants.GIFT_LIST)
	public List<GiftInfo> loadGiftInfos() {
		String sql = "select * from t_gift_info where status=1 order by seq desc, price asc, createTime desc ";
		return jdbcHandle.query(GiftInfo.class, sql);
	}

	@CacheWrite(key = CacheConstants.GIFT_INFO,  fields = "giftId")
	public GiftInfo getGiftInfo(Integer giftId) {
		String sql = "select * from t_gift_info where giftId=?";
		return jdbcHandle.queryFirst(GiftInfo.class, sql, giftId);
	}

	@CacheWrite
	public List<GiftInfo> getGiftInfos(Integer... giftId) {
		String sql = "select * from t_gift_info where giftId in(" + StringUtil.getByMosaicChr("?", ",", giftId.length)
				+ ")";
		return jdbcHandle.query(GiftInfo.class, sql, giftId);
	}

}
