package com.jiujun.voice.modules.apps.user.userinfo.service;

import java.util.List;

import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
/**
 * 用户基础信息服务
 * @author Coody
 *
 */
public interface UserInfoService {

	/**
	 * 插入用户基础信息
	 */
	public UserInfo initUserInfo(String userId,String nickName);
	
	/**
	 * 修改用户信息
	 */
	public Long saveUserInfo(UserInfo userInfo);
	
	/**
	 * 查询用户基础信息
	 */
	public UserInfo getUserInfo(String userId);
	
	/**
	 * 批量查询用户信息
	 */
	public List<UserInfo> getUserInfos(String...userIds);
	
	/**
	 * 查询用户总揽信息
	 */
	public UserSchema getUserSchema(String userId);
	
	/**
	 * 模糊匹配昵称
	 * @author Shao.x
	 * @date 2018年12月14日
	 * @param name 匹配的字符
	 * @return
	 */
	List<UserSchema> searchByKeyWord(String name);
	
	/**
	 * 根据昵称查询用户信息 精准匹配
	 */
	public List<UserInfo> getUserInfoByNickName(String keyWord);
	
	/**
	 * 创建昵称
	 */
	public String createNickName(String name,String userId);
}
