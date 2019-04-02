package com.jiujun.voice.modules.apps.topper.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.jiujun.voice.common.cache.annotation.CacheWrite;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.monitor.annotation.RunTimeFlag;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.topper.cmd.vo.schema.UserCharmSchema;
import com.jiujun.voice.modules.apps.topper.cmd.vo.schema.UserExpSchema;
import com.jiujun.voice.modules.apps.topper.dao.UserSendTopperDao;
import com.jiujun.voice.modules.apps.topper.domain.UserSendTopper;
import com.jiujun.voice.modules.apps.topper.service.UserSendTopperService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("unchecked")
@Service
public class UserSendTopperServiceImpl implements UserSendTopperService{

	
	@Resource
	UserSendTopperDao userSendTopperDao;
	@Resource
	UserInfoService userInfoService;
	
	@RunTimeFlag("保存用户赠送榜单")
	@Override
	public Integer saveUserSendToppers(List<UserSendTopper> toppers) {
		return userSendTopperDao.saveUserSendToppers(toppers);
	}


	@Override
	public List<UserSendTopper> getUserReceiveGiftTopper(String toUserId) {
		return userSendTopperDao.getUserReceiveGiftTopper(toUserId);
	}


	
	@Override
	@CacheWrite
	public Pager getExpTopperPager(Integer type, Pager pager) {
		pager=userSendTopperDao.getExpTopperPager(type, pager);
		if(pager.getData()==null){
			return pager;
		}
		List<UserSendTopper> data=pager.getData();
		List<String> userIds=PropertUtil.getFieldValues(data, "fromUserId");
		List<UserInfo> users = userInfoService.getUserInfos(userIds.toArray(new String[] {}));
		if(StringUtil.isNullOrEmpty(userIds)){
			return pager;
		}
		Map<String, UserInfo> userMap=(Map<String, UserInfo>) PropertUtil.listToMap(users, "userId");
		List<UserExpSchema> userExpSchemas=new ArrayList<UserExpSchema>();
		
		for(UserSendTopper topper:data){
			UserInfo userInfo=userMap.get(topper.getFromUserId());
			if(userInfo==null){
				continue;
			}
			UserExpSchema schema=new UserExpSchema();
			BeanUtils.copyProperties(userInfo, schema);
			schema.setExp(topper.getExp());
			userExpSchemas.add(schema);
		}
		pager.setData(userExpSchemas);
		return pager;
	}


	@Override
	public Pager getCharmTopperPager(Integer type, Pager pager) {
		pager=userSendTopperDao.getCharmTopperPager(type, pager);
		if(pager.getData()==null){
			return pager;
		}
		List<UserSendTopper> data=pager.getData();
		List<String> userIds=PropertUtil.getFieldValues(data, "toUserId");
		List<UserInfo> users = userInfoService.getUserInfos(userIds.toArray(new String[] {}));
		if(StringUtil.isNullOrEmpty(userIds)){
			return pager;
		}
		Map<String, UserInfo> userMap=(Map<String, UserInfo>) PropertUtil.listToMap(users, "userId");
		List<UserCharmSchema> userExpSchemas=new ArrayList<UserCharmSchema>();
		
		for(UserSendTopper topper:data){
			UserInfo userInfo=userMap.get(topper.getToUserId());
			if(userInfo==null){
				continue;
			}
			UserCharmSchema schema=new UserCharmSchema();
			BeanUtils.copyProperties(userInfo, schema);
			schema.setCharm(topper.getExp());
			userExpSchemas.add(schema);
		}
		pager.setData(userExpSchemas);
		return pager;
	}


	@Override
	public UserExpSchema getExpRank(String userId, Integer type) {
		Integer rank=userSendTopperDao.getExpRank(userId, type);
		UserInfo userInfo=userInfoService.getUserInfo(userId);
		UserExpSchema schema=new UserExpSchema();
		BeanUtils.copyProperties(userInfo, schema);
		schema.setRank(rank);
		return schema;
	}
	
	@Override
	public UserCharmSchema getCharmRank(String userId, Integer type) {
		Integer rank=userSendTopperDao.getCharmRank(userId, type);
		UserInfo userInfo=userInfoService.getUserInfo(userId);
		UserCharmSchema schema=new UserCharmSchema();
		BeanUtils.copyProperties(userInfo, schema);
		schema.setRank(rank);
		return schema;
	}

}
