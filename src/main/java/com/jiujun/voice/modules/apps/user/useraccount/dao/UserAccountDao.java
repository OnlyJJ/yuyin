package com.jiujun.voice.modules.apps.user.useraccount.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.jiujun.voice.common.cache.annotation.CacheWipe;
import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.handle.JdbcHandle;
import com.jiujun.voice.common.utils.EncryptUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
/**
 * @author Coody
 *
 */
@Repository
public class UserAccountDao {

	@Resource
	JdbcHandle jdbcHandle;

	/**
	 * 根据用户ID、手机号、邮箱查询用户账务信息
	 * 
	 * @param userIdOrMobileOrEmail
	 * @return
	 */
	@CacheWrite(key = CacheConstants.USER_ACCOUNT, time = 5, fields = "userIdOrMobileOrEmail")
	public UserAccount getUserAccount(String userIdOrMobileOrEmail) {
		String sql = "select * from t_user_account where userId=? or mobile=? or email=?";
		return jdbcHandle.queryFirst(UserAccount.class, sql, userIdOrMobileOrEmail, userIdOrMobileOrEmail,
				userIdOrMobileOrEmail);
	}

	/**
	 * 根据用户ID查询用户账务信息（此处不要使用缓存，如果需要，请在service层使用）
	 * 
	 * @param userId
	 * @return
	 */
	public UserAccount getUserAccountByUserId(String userId) {
		String sql = "select * from t_user_account where userId=?";
		return jdbcHandle.queryFirst(UserAccount.class, sql, userId);
	}

	/**
	 * 插入用户信息
	 * 
	 * @param account
	 * @return
	 */
	public Long insertUserAccount(UserAccount account) {
		Long code = jdbcHandle.insert(account);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1020);
		}
		return code;
	}


	/**
	 * 更新字段
	 */
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "account.userId")
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "account.email")
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "account.mobile")
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = { "account.unionId", "account.referrer" })
	public Long updateUserAccount(UserAccount account, String upField) {
		String sql = "update t_user_account set " + upField + "=? where userId=? limit 1";
		Object value = PropertUtil.getFieldValue(account, upField);
		Long code = jdbcHandle.update(sql, value, account.getUserId());
		if (code < 0) {
			throw new CmdException(ErrorCode.ERROR_1021);
		}
		return code;
	}



	/**
	 * 加财富经验
	 * 
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @return
	 */
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	public Long addExp(String userId, int exp) {
		String sql = "update t_user_account set exp = exp + ? where userId=? ";
		Long code = jdbcHandle.update(sql, exp, userId);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1021);
		}
		return code;
	}

	/**
	 * 加魅力
	 * 
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @return
	 */
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	public Long addCharm(String userId, int charm) {
		String sql = "update t_user_account set charm = charm + ? where userId=? ";
		Long code = jdbcHandle.update(sql, charm, userId);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1021);
		}
		return code;
	}



	public Long upRichLevel(String userId, int level) {
		String sql = "update t_user_account set expLevel=? where userId=?";
		Long code = jdbcHandle.update(sql, level, userId);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1021);
		}
		return code;
	}

	public Long upCharmLevel(String userId, int level) {
		String sql = "update t_user_account set charmLevel=? where userId=?";
		Long code = jdbcHandle.update(sql, level, userId);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1021);
		}
		return code;
	}

	/**
	 * 通过第三方唯一ID查询用户信息
	 * 
	 * @param unionId
	 *            第三方唯一ID
	 * @param referrer
	 *            2QQ 3微信 4微博
	 * @return
	 */
	@CacheWrite(key = CacheConstants.USER_ACCOUNT, time = 300, fields = { "unionId", "referrer" })
	public UserAccount getUserAccount(String unionId, Integer referrer) {
		String sql = "select * from t_user_account where unionId=? and referrer=? limit 1";
		return jdbcHandle.queryFirst(UserAccount.class, sql, unionId, referrer);
	}

	/**
	 * 查询根据注册设备查询账号
	 * 
	 * @param clientId
	 * @param type
	 *            来源0手机 1邮箱 2QQ 3微信 4微博 5游客
	 * @return
	 */
	@CacheWrite(key = CacheConstants.USER_ACCOUNT, fields = { "clientId", "type" })
	public UserAccount getUserAccountByClientId(String clientId, Integer referrer) {
		String sql = "select * from t_user_account where clientId=? and referrer=?";
		return jdbcHandle.queryFirst(UserAccount.class, sql, clientId, referrer);
	}


	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	public Long writeAccessToken(UserAccount userAccount) {
		String password = EncryptUtil.md5(userAccount.getUnionId() + userAccount.getAccessToken());
		password = EncryptUtil.encryptUserPassword(userAccount.getUserId(), password);
		String sql = "update t_user_account set accessToken=?,password=? where userId=? limit 1";
		return jdbcHandle.update(sql, userAccount.getAccessToken(), password, userAccount.getUserId());
	}
	
	/**
	 * 更新登录记录
	 */
	@CacheWipe(key = CacheConstants.USER_ACCOUNT, fields = "userId")
	public Long writeLoginTime(String userId,String loginIp){
		String sql = "update t_user_account set loginIp=?,loginTime=? where userId=? limit 1";
		return jdbcHandle.update(sql, loginIp, new Date(), userId);
	}
	
	/**
	 * 根据设备ID查询用户列表
	 * @param args
	 */
	public List<UserAccount> getUserAccounts(String clientId){
		String sql="select * from t_user_account where clientId=? order by createTime limit 100";
		return jdbcHandle.query(UserAccount.class,sql,clientId);
	}

	
	
	public static void main(String[] args) {
		String mds=EncryptUtil.encryptUserPassword("108290", "6e0eec27db68ceda572bb617a38080ca");
		System.out.println(mds);
	}
}
