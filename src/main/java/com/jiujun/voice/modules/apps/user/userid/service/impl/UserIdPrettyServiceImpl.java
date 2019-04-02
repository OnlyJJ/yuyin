package com.jiujun.voice.modules.apps.user.userid.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.modules.apps.user.userid.dao.UserIdPrettyDao;
import com.jiujun.voice.modules.apps.user.userid.domain.UserIdPretty;
import com.jiujun.voice.modules.apps.user.userid.service.UserIdPrettyService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class UserIdPrettyServiceImpl implements UserIdPrettyService{

	@Resource
	UserIdPrettyDao userIdPrettyDao;

	@Override
	public Integer insertUserIdPretty(List<UserIdPretty> prettys) {
		return userIdPrettyDao.insertUserIdPretty(prettys);
	}
}
