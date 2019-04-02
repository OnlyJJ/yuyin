package com.jiujun.voice.modules.im.rongcloud.handle;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;
import com.jiujun.voice.modules.im.rongcloud.util.RongCloudBuilder;
/**
 * 
 * @author Coody
 *
 */
@Component
public class RongCloudHandle {


	
	/**
	 * 创建聊天室
	 * @param roomId 聊天室ID
	 * @param roomName 聊天室名字
	 * @return
	 */
	public boolean createChatRoom(String roomId,String roomName) {
		try {

			Map<String, String> headMap = RongCloudBuilder.buildHeader();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("chatroom["+roomId+"]", roomName);
			HttpEntity entity = HttpUtil.post("http://api.cn.ronghub.com/chatroom/create.json",
					RongCloudBuilder.builderData(params), headMap);
			Map<String, Object> result = JSON.parseObject(entity.getHtml(), new TypeReference<Map<String, Object>>() {
			});
			LogUtil.logger.error("融云>>创建聊天室返回消息>>"+roomId+":"+result);
			if(result.get("code").toString().trim().equals("200")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			LogUtil.logger.error("融云>>创建聊天室失败>>"+roomId);
			return false;
		}
	}
	
	/**
	 * 销毁聊天室
	 * @param args
	 */
	public boolean destroyChatRoom(String roomId) {
		try {

			Map<String, String> headMap = RongCloudBuilder.buildHeader();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("chatroomId", roomId);

			HttpEntity entity = HttpUtil.post("http://api.cn.ronghub.com/chatroom/destroy.json",
					RongCloudBuilder.builderData(params), headMap);
			Map<String, Object> result = JSON.parseObject(entity.getHtml(), new TypeReference<Map<String, Object>>() {
			});
			LogUtil.logger.error("融云>>销毁聊天室返回消息>>"+roomId+":"+result);
			if(result.get("code").toString().trim().equals("200")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			LogUtil.logger.error("融云>>销毁聊天室失败>>"+roomId);
			return false;
		}
	}
	
	/**
	 * 聊天室禁言
	 * @param roomId 房间号
	 * @param userId 用户ID
	 * @param minute 禁言时长(分钟) 最大值为43200分钟
	 * @return 
	 */
	public boolean destroyChatRoom(String roomId,String userId,Integer minute) {
		try {

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("chatroomId", roomId);
			params.put("userId", userId);
			params.put("minute", minute);
			
			Map<String, String> headMap = RongCloudBuilder.buildHeader();
			HttpEntity entity = HttpUtil.post("http://api.cn.ronghub.com/chatroom/destroy.json",
					RongCloudBuilder.builderData(params), headMap);
			Map<String, Object> result = JSON.parseObject(entity.getHtml(), new TypeReference<Map<String, Object>>() {
			});
			LogUtil.logger.error("融云>>聊天室禁言返回消息>>"+roomId+":"+userId+":"+result);
			if(result.get("code").toString().trim().equals("200")) {
				return true;
			}
			return false;
		} catch (Exception e) {
			LogUtil.logger.error("融云>>禁言聊天室用户失败>>"+roomId+":"+userId);
			return false;
		}
	}


	/**
	 * 获取Token https://www.rongcloud.cn/docs/server.html#user_get_token
	 * 
	 * @param userId
	 * @param name
	 * @param head
	 *            头像地址
	 * @return
	 */
	public String getToken(String userId, String name, String head) {
		try {

		
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", userId);
			params.put("name", name);
			params.put("portraitUri", head);
			
			Map<String, String> headMap = RongCloudBuilder.buildHeader();
			HttpEntity entity = HttpUtil.post("http://api.cn.ronghub.com/user/getToken.json",
					RongCloudBuilder.builderData(params), headMap);
			Map<String, Object> result = JSON.parseObject(entity.getHtml(), new TypeReference<Map<String, Object>>() {
			});
			LogUtil.logger.error("融云>>获取Token>>"+userId+":"+result);
			return result.get("token").toString();
		} catch (Exception e) {
			LogUtil.logger.error("融云>>获取Token失败>>" + userId);
			return null;
		}
	}

	
	
	public static void main(String[] args) {
		System.out.println(new RongCloudHandle().getToken("10010", "", ""));
	}
}
