package com.jiujun.voice.modules.apps.user.useraccount.service;

import java.util.List;

import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;

/**
 * 用户账务相关service
 * 
 * @author Coody
 *
 */
public interface UserAccountService {

	/**
	 * 根据用户ID、邮箱、手机查询用户信息
	 */
	public UserAccount getUserAccount(String userIdOrMobileOrEmail);
	
	/**
	 * 查询根据注册设备查询账号
	 * @param clientId
	 * @param referrer 来源0手机 1邮箱 2QQ 3微信 4微博 5游客
	 * @return
	 */
	public UserAccount getUserAccountByClientId(String clientId,Integer referrer);

	/**
	 * 插入用户账务信息
	 * 
	 * @param mobile  手机号，没有请留空
	 * @param email   邮箱，没有请留空
	 * @param unionId 第三方登录唯一ID，没有请留空
	 * @param clientId 客户端ID，必传
	 * @param password 密码，必传
	 * @param referrer 0手机 1邮箱 2QQ 3微信 4微博
	 * @return
	 */
	public UserAccount initUserAccount(String nickName,String mobile, String email, String unionId, String clientId, String password,Integer referrer);
	
	/**
	 * 根据用户ID、邮箱、手机查询用户信息
	 */
	public UserAccount getUserAccountByUserId(String userId);
	
	/**
	 * 更新某个字段 以userId依据
	 * @param account useraccount对象
	 * @param upField 要更新的字段
	 * @return
	 */
	public Long updateUserAccount(UserAccount account, String upField);
	

	/**
	 * 加财富经验
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @param exp 财富经验值
	 * @return
	 */
	public Long addExp(String userId, int exp);
	
	/**
	 * 加魅力值
	 * @author Shao.x
	 * @date 2018年12月11日
	 * @param charm 魅力值
	 * @return
	 */
	public Long addCharm(String userId, int charm);
	
	
	/**
	 * 更新用户财富等级
	 * @author Shao.x
	 * @date 2018年12月17日
	 * @param userId
	 * @param level 当前用户财富等级
	 * @param exp 当前用户财富经验
	 * @return 返回1，说明升级，其他则不升级
	 */
	public Long upUserLevel(String userId);
	
	/**
	 * 更新魅力等级
	 * @author Shao.x
	 * @date 2018年12月17日
	 * @param userId
	 * @param level 当前用户魅力等级
	 * @param exp 当前用户魅力经验
	 * @return 返回1，说明升级，其他则不升级
	 */
	public Long upCharmLevel(String userId);
	
	
	/**
	 * 通过第三方唯一ID查询用户信息
	 * @param unionId 第三方唯一ID
	 * @param referrer 2QQ 3微信 4微博
	 * @return
	 */
	public UserAccount getUserAccount(String unionId,Integer referrer);
	
	/**
	 * 保存第三方AccessToken
	 */
	public Long changeAuthToken(UserAccount account);

	/**
	 * 更新登录记录
	 */
	public Long writeLoginTime(String userId,String loginIp);
	/**
	 * 根据设备ID查询用户列表
	 */
	public List<UserAccount> getUserAccounts(String clientId);
	
}
