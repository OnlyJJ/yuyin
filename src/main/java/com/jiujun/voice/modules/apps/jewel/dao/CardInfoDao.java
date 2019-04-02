package com.jiujun.voice.modules.apps.jewel.dao;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.modules.apps.jewel.domain.CardInfo;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class CardInfoDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public Long insert(CardInfo info) {
		Long code = jdbcHandle.insert(info);
		if(code < 1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	public CardInfo getCardInfo(String userId) {
		String sql = "select * from t_card_info where userId=? limit 1";
		return jdbcHandle.queryFirst(CardInfo.class, sql, userId);
	}
}