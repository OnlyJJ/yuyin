package com.jiujun.voice.modules.apps.dic.dao;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.modules.apps.dic.domain.DicInfo;

/**
 * 
 * @author Coody
 *
 */
@Repository
public class DicInfoDao {

	@Resource
	JdbcHandle jdbcHandle;
	
	/**
	 * 根据key查询字典信息
	 * @param key
	 * @return
	 */
	@CacheWrite(key=CacheConstants.DIC_INFO,fields="fieldName",time=72000)
	public DicInfo getDicInfo(String fieldName) {
		String sql="select * from t_dic_info where fieldName=?";
		return jdbcHandle.queryFirst(DicInfo.class, sql, fieldName);
	}
	
	
	/**
	 * 更新字典值
	 */
	@CacheWipe(key=CacheConstants.DIC_INFO,fields="dicInfo.fieldName")
	public Long saveDicInfo(DicInfo dicInfo) {
		return jdbcHandle.saveOrUpdate(dicInfo);
	}
}
