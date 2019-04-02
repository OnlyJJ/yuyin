package com.jiujun.voice.modules.apps.user.useraccount.handle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.cmd.vo.Header;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.model.EntityModel;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.room.service.RoomMemberBehaviorService;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserLoginRecord;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserLoginRecordService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@Component
@LogFlag("用户授权业务处理")
public class UserAuthHandle {

	@Resource
	IMessageHandle imessageHandle;
	@Resource
	RoomMemberBehaviorService roomMemberBehaviorService;
	/**
	 * 五分钟无动作代表已断线
	 */
	public static final Integer USER_TIME_OUT = 1000 * 60 * 5;

	@Resource
	RedisCache redisCache;
	@Resource
	UserLoginRecordService userLoginRecordService;
	@Resource
	UserAccountService userAccountService;

	@LogFlag("销毁Token")
	public void removeToken(String userId) {
		try {
			String key = CacheConstants.USER_TOKEN + ":" + userId;
			redisCache.delCache(key);
			roomMemberBehaviorService.cleanUser(userId);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@LogFlag("创建Token")
	public TokenEntity createToken(String userId,Header header) {
		if(StringUtil.isNullOrEmpty(userId)){
			throw new CmdException(ErrorCode.ERROR_1004);
		}
		LogUtil.logger.info("创建用户Token:" + userId);
		/**
		 * 写入登录记录
		 */
		UserLoginRecord record=new UserLoginRecord();
		BeanUtils.copyProperties(header, record);
		record.setUserId(userId);
		userLoginRecordService.insertLoginRecord(record);
		/**
		 * 更新登录时间
		 */
		userAccountService.writeLoginTime(userId, header.getIpAddress());
		
		TokenEntity tokenEntity = getUserToken(userId);
		String key = CacheConstants.USER_TOKEN + ":" + userId;
		if (tokenEntity == null) {
			tokenEntity = new TokenEntity();
			String token = JUUIDUtil.createUuid();
			tokenEntity.setToken(token);
			tokenEntity.setClientId(header.getClientId());
			redisCache.setCache(key, tokenEntity, 12 * 60 * 60);
			return tokenEntity;
		}
		if(!tokenEntity.getClientId().equals(header.getClientId())){
			String token = JUUIDUtil.createUuid();
			tokenEntity.setToken(token);
			removeToken(userId);
			Map<String, String> context=new HashMap<String, String>();
			context.put("data", "您的账号已在另一台设备登录，您已被迫下线，如非本人操作，请及时更改密码!");
			context.put("clientId", tokenEntity.getClientId());
			imessageHandle.sendGeneralMsg(userId, "下线提醒", JSON.toJSONString(context), MsgEnum.LOGIN_CONFLICT.getType(),false);
		}
		tokenEntity.setClientId(header.getClientId());
		redisCache.setCache(key, tokenEntity, 12 * 60 * 60);
		return tokenEntity;
	}


	@LogFlag("获取Token")
	public TokenEntity getUserToken(String userId) {
		String key = CacheConstants.USER_TOKEN + ":" + userId;
		TokenEntity tokenEntity = redisCache.getCache(key);
		if (tokenEntity != null) {
			// 当token激活时间超过1分钟，自动续期
			if (System.currentTimeMillis() - tokenEntity.getAcviteTime().getTime() > 1000 * 60) {
				tokenEntity.setAcviteTime(new Date());
				redisCache.setCache(key, tokenEntity, 12 * 60 * 60);
			}
		}
		return tokenEntity;
	}

	/**
	 * 判断用户是否在线
	 * @param userId
	 * @return
	 */
	public Boolean isOnLine(String userId) {
		TokenEntity tokenEntity = getUserToken(userId);
		if (tokenEntity == null) {
			return false;
		}
		if (System.currentTimeMillis() - tokenEntity.getAcviteTime().getTime() > USER_TIME_OUT) {
			return false;
		}
		return true;
	}
	
	/**
	 * 批量获取用户在线时间
	 *
	 */
	public Map<String, TokenEntity> getUserTokens(String...userIds) {
		List<String> keys=new ArrayList<String>();
		for(String userId:userIds){
			String key=CacheConstants.USER_TOKEN + ":" + userId;
			keys.add(key);
		}
		Map<String, Object> valueMap=redisCache.getCacheBatch(keys.toArray(new String[]{}));
		if(StringUtil.isNullOrEmpty(valueMap)){
			return new HashMap<String, UserAuthHandle.TokenEntity>();
		}
		Map<String, TokenEntity> results=new HashMap<String, TokenEntity>();
		for(String userId:userIds){
			String valueKey=CacheConstants.USER_TOKEN + ":" + userId;
			TokenEntity token=(TokenEntity) valueMap.get(valueKey);
			results.put(userId, token);
		}
		return results;
	}
	@SuppressWarnings("serial")
	public static class TokenEntity extends EntityModel{

		private String token;

		private Date acviteTime = new Date();
		
		private String clientId;

		
		
		public String getClientId() {
			return clientId;
		}

		public void setClientId(String clientId) {
			this.clientId = clientId;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public Date getAcviteTime() {
			return acviteTime;
		}

		public void setAcviteTime(Date acviteTime) {
			this.acviteTime = acviteTime;
		}

	}
}
