package com.jiujun.voice.modules.apps.user.userid.service;

import java.util.List;

import com.jiujun.voice.modules.apps.user.userid.domain.UserIdPretty;

/**
 * 用户靓号服务
 * @author Coody
 *
 */
public interface UserIdPrettyService {

	/**
	 * 插入靓号
	 * @param pretty
	 * @return
	 */
	public Integer insertUserIdPretty(List<UserIdPretty> prettys);
	
}
