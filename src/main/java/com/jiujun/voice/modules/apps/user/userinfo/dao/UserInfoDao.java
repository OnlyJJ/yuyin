package com.jiujun.voice.modules.apps.user.userinfo.dao;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;

/**
 * 
 * @author Coody
 *
 *         2018年12月14日
 * 
 * @blog 54sb.org
 */
@Repository
public class UserInfoDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 根据userId查询用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@CacheWrite(key = CacheConstants.USER_INFO, time = 60, fields = "userId")
	public UserInfo getUserInfo(String userId) {
		String sql = "select * from t_user_info where userId=?";
		return jdbcHandle.queryFirst(UserInfo.class, sql, userId);
	}

	/**
	 * 插入用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	public Long insertUserInfo(UserInfo userInfo) {
		Long code = jdbcHandle.insert(userInfo);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1018);
		}
		return code;
	}

	/**
	 * 更新用户信息
	 * 
	 * @param userInfo
	 * @return
	 */
	@CacheWipe(key = CacheConstants.USER_INFO, fields = "userInfo.userId")
	public Long saveUserInfo(UserInfo userInfo) {
		Long code = jdbcHandle.saveOrUpdate(userInfo);
		if (code < 0) {
			throw new CmdException(ErrorCode.ERROR_1019);
		}
		return code;
	}

	/**
	 * 批量查询用户信息
	 * 
	 * @param userIds
	 * @return
	 */
	public List<UserInfo> getUserInfos(String... userIds) {
		String sql = "select * from t_user_info where userId in (" + StringUtil.getByMosaicChr("?", ",", userIds.length)
				+ ")";
		return jdbcHandle.query(UserInfo.class, sql, Arrays.<String>asList(userIds));
	}

	/**
	 * 模糊搜索昵称
	 * 
	 * @author Shao.x
	 * @date 2018年12月14日
	 * @param nameLike
	 * @return
	 */
	public List<UserInfo> searchByKeyWord(String keyWord) {
		String keyWordLike = MessageFormat.format("%{0}%", keyWord);
		String sql = "select * from t_user_info where userId=? or name like ? limit 100";
		return jdbcHandle.query(UserInfo.class, sql, keyWord, keyWordLike);
	}

	public List<UserInfo> getUserInfoByNickName(String name) {
		String sql = "select * from t_user_info where name =? ";
		return jdbcHandle.query(UserInfo.class, sql, name);
	}
}
