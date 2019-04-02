package com.jiujun.voice.modules.apps.jewel.dao;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.jewel.domain.DrawJewelRecord;
/**
 * 
 * @author Coody
 *
 */
@Repository
public class DrawJewelRecordDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	public Long insert(DrawJewelRecord record) {
		Long code = jdbcHandle.insert(record);
		if(code < 1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	public Long update(Integer id, Integer status) {
		String sql = "update t_draw_jewel_record set status=? where id=?";
		Long code = jdbcHandle.update(sql, status, id);
		if(code < 1) {
			throw new CmdException(ErrorCode.ERROR_502);
		}
		return code;
	}
	
	/**
	 * 获取用户当前正在提现的所有钻石
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param userId
	 * @return
	 */
	public Long getFrozenJewel(String userId) {
		String sql = "select SUM(drawJewel) from t_draw_jewel_record where userId=? and status=0";
		return jdbcHandle.queryFirst(Long.class, sql, userId);
	}
	
	/**
	 * 用户所有提现记录
	 * @author Shao.x
	 * @date 2019年1月9日
	 * @param userId
	 * @return
	 */
	public List<DrawJewelRecord> listRecord(String userId) {
		String sql = "select * from t_draw_jewel_record where userId=? order by createTime desc";
		return jdbcHandle.query(DrawJewelRecord.class, sql, userId);
	}
	
	public DrawJewelRecord getById(int id) {
		String sql = "select * from t_draw_jewel_record where id=? ";
		return jdbcHandle.queryFirst(DrawJewelRecord.class, sql, id);
	}
	
}