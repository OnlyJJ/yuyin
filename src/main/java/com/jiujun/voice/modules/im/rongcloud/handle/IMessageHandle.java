package com.jiujun.voice.modules.im.rongcloud.handle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.http.HttpUtil;
import com.jiujun.voice.common.utils.http.entity.HttpEntity;
import com.jiujun.voice.modules.apps.room.service.RoomActiveService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.im.msg.ImMessage;
import com.jiujun.voice.modules.im.rongcloud.config.RongCloudConfig;
import com.jiujun.voice.modules.im.rongcloud.entity.RongCloudMessageEntity;
import com.jiujun.voice.modules.im.rongcloud.task.RongCloudQueueTask;
import com.jiujun.voice.modules.im.rongcloud.util.RongCloudBuilder;

/**
 * 消息发送服务
 * 
 * @author Coody
 *
 */
@Component
public class IMessageHandle {

	@Resource
	RedisCache redisCache;
	@Resource
	UserInfoService userInfoService;
	@Resource
	RongCloudQueueTask rongCloudQueueTask;
	@Resource
	RoomActiveService roomActiveService;

	/**
	 * 发送系统消息
	 * 
	 * @param toUserId
	 *            目标用户
	 * @param title
	 *            消息标题，显示在通知栏
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送系统消息")
	public void sendGeneralMsg(String toUserId, String title, String content, Integer type) {
		try {
			// 接受者
			UserSchema receiver = userInfoService.getUserSchema(toUserId);
			sendGeneralMsg(receiver, title, content, type, true);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送系统消息
	 * 
	 * @param toUserId
	 *            目标用户
	 * @param title
	 *            消息标题，显示在通知栏
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送系统消息")
	public void sendGeneralMsg(String toUserId, String title, String content, Integer type, Boolean isAsyn) {
		try {
			// 接受者
			UserSchema receiver = userInfoService.getUserSchema(toUserId);
			sendGeneralMsg(receiver, title, content, type, isAsyn);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送系统消息
	 * 
	 * @param receiver
	 *            接受者
	 * @param title
	 *            消息标题，显示在通知栏
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 * @param isAsyn
	 *            是否异步发送
	 */
	@LogFlag("发送系统消息")
	public void sendGeneralMsg(UserSchema receiver, String title, String content, Integer type, Boolean isAsyn) {
		sendGeneralMsg(RongCloudConfig.SYSTEM_USER_ID, receiver, title, content, type, isAsyn);
	}

	/**
	 * 发送系统消息
	 * 
	 * @param receiver
	 *            接受者
	 * @param title
	 *            消息标题，显示在通知栏
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 * @param isAsyn
	 *            是否异步发送
	 */
	@LogFlag("发送系统消息")
	public void sendGeneralMsg(String userId, UserSchema receiver, String title, String content, Integer type,
			Boolean isAsyn) {
		try {
			ImMessage message = new ImMessage();
			// 发送者

			UserSchema sender = new UserSchema();
			sender.setUserId(RongCloudConfig.SYSTEM_USER_ID);
			message.setSender(sender);
			if (!userId.equals(RongCloudConfig.SYSTEM_USER_ID)) {
				sender = userInfoService.getUserSchema(userId);
				message.setSender(sender);
			}
			// 接受者
			message.setReceiver(receiver);

			message.setType(type);

			message.setContent(content);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromUserId", RongCloudConfig.SYSTEM_USER_ID);
			params.put("toUserId", receiver.getUserId());
			params.put("objectName", "CX:IMessage");
			params.put("pushContent", title);
			params.put("content", JSON.toJSONString(message));
			// 构建队列容器
			RongCloudMessageEntity queue = new RongCloudMessageEntity();
			queue.setUrl("http://api.cn.ronghub.com/message/system/publish.json");
			queue.setData(params);
			queue.setMessage(message);
			if (isAsyn) {
				redisCache.lBeanPushHead(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE, JSON.toJSONString(queue));
				return;
			}
			rongCloudQueueTask.sendMessage(queue);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送房间系统消息
	 * 
	 * @param userId
	 *            触发者
	 * 
	 * @param roomId
	 *            房间号
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送房间消息")
	public void sendRoomMsg(String userId, String roomId, String content, Integer type) {
		try {
			// 发送者
			UserSchema sender = new UserSchema();
			sender.setUserId(userId);
			if (!userId.equals(RongCloudConfig.SYSTEM_USER_ID)) {
				sender = userInfoService.getUserSchema(userId);
			}
			sendRoomMsg(sender, roomId, content, type);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送房间系统消息
	 * 
	 * @param sender
	 *            触发者
	 * 
	 * @param roomId
	 *            房间号
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送房间消息")
	public void sendRoomMsg(UserSchema sender, String roomId, String content, Integer type) {
		try {
			sendRoomMsg(sender, new String[] { roomId }, content, type);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送房间系统消息
	 * 
	 * @param sender
	 *            触发者
	 * 
	 * @param roomId
	 *            房间号
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送房间消息")
	public void sendRoomMsg(UserSchema sender, String[] roomId, String content, Integer type) {
		try {
			ImMessage message = new ImMessage();
			// 发送者
			message.setSender(sender);
			message.setContent(content);
			message.setType(type);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromUserId", RongCloudConfig.SYSTEM_USER_ID);
			params.put("toChatroomId", roomId);
			params.put("objectName", "CX:IMessage");
			params.put("content", JSON.toJSONString(message));
			// 构建队列容器
			RongCloudMessageEntity queue = new RongCloudMessageEntity();
			queue.setUrl("http://api.cn.ronghub.com/message/chatroom/publish.json");
			queue.setData(params);
			queue.setMessage(message);
			redisCache.lBeanPushHead(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE, JSON.toJSONString(queue));
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送房间系统消息
	 * 
	 * @param roomId
	 *            房间号
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送房间消息")
	public void sendRoomMsg(String roomId, String content, Integer type) {
		try {
			sendRoomMsg(RongCloudConfig.SYSTEM_USER_ID, roomId, content, type);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送全站用户消息 单个应用每小时只能发送 2 次，每天最多发送 3 次。
	 * 
	 * @param title
	 *            通知栏标题
	 * @param content
	 *            消息内容 &json
	 * @param clientType
	 *            客户端类型 0所有 1iOS 2 Android
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送全站用户消息")
	public void sendBroadMsg(String title, String content, Integer clientType, Integer type) {
		try {
			ImMessage message = new ImMessage();
			// 发送者
			UserSchema sender = new UserSchema();
			sender.setUserId(RongCloudConfig.SYSTEM_USER_ID);
			message.setSender(sender);
			message.setContent(content);
			message.setType(type);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromUserId", RongCloudConfig.SYSTEM_USER_ID);
			params.put("objectName", "CX:IMessage");
			params.put("pushContent", "title");
			if (clientType == 1) {
				params.put("os", "iOS");
			}
			if (clientType == 2) {
				params.put("os", "Android");
			}
			params.put("content", JSON.toJSONString(message));
			// 构建队列容器
			RongCloudMessageEntity queue = new RongCloudMessageEntity();
			queue.setUrl("http://api.cn.ronghub.com/message/chatroom/broadcast.json");
			queue.setData(params);
			queue.setMessage(message);
			redisCache.lBeanPushHead(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE, JSON.toJSONString(queue));
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送全站房间消息
	 * 
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送全站房间消息")
	public void sendTotalRoomMsg(String content, Integer type) {

			List<String> roomIds = roomActiveService.getAllActiveRoomId();
			sendTotalRoomMsg(roomIds, content, type);
	}
	/**
	 * 发送全站房间消息
	 * 
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送全站房间消息")
	public void sendTotalRoomMsg(List<String> roomIds,String content, Integer type) {
		try {

			ImMessage message = new ImMessage();
			// 发送者
			UserSchema sender = new UserSchema();
			sender.setUserId(RongCloudConfig.SYSTEM_USER_ID);
			message.setSender(sender);
			message.setContent(content);
			message.setType(type);

			List<String> tempRoomIds = new ArrayList<String>();
			for (int i = 0; i < roomIds.size(); i++) {
				tempRoomIds.add(roomIds.get(i));
				if (tempRoomIds.size() >= 100) {
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("fromUserId", RongCloudConfig.SYSTEM_USER_ID);
					params.put("toChatroomId", tempRoomIds);
					params.put("objectName", "CX:IMessage");
					params.put("content", JSON.toJSONString(message));
					// 构建队列容器
					RongCloudMessageEntity queue = new RongCloudMessageEntity();
					queue.setUrl("http://api.cn.ronghub.com/message/chatroom/publish.json");
					queue.setData(params);
					queue.setMessage(message);
					redisCache.lBeanPushHead(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE, JSON.toJSONString(queue));
					tempRoomIds.clear();
				}
			}
			if (StringUtil.isNullOrEmpty(tempRoomIds)) {
				return;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromUserId", RongCloudConfig.SYSTEM_USER_ID);
			params.put("toChatroomId", roomIds);
			params.put("objectName", "CX:IMessage");
			params.put("content", JSON.toJSONString(message));
			// 构建队列容器
			RongCloudMessageEntity queue = new RongCloudMessageEntity();
			queue.setUrl("http://api.cn.ronghub.com/message/chatroom/publish.json");
			queue.setData(params);
			queue.setMessage(message);
			redisCache.lBeanPushHead(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE, JSON.toJSONString(queue));
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}
	/**
	 * 发送全站房间广播 每秒最多发一次 (赞不可用)
	 * 
	 * @param content
	 *            消息内容 & json
	 * @param type
	 *            类型，参考MsgEnum消息枚举
	 */
	@LogFlag("发送全站房间消息")
	public void sendRoomBroadMsg(String content, Integer type) {
		try {
			ImMessage message = new ImMessage();
			// 发送者
			UserSchema sender = new UserSchema();
			sender.setUserId(RongCloudConfig.SYSTEM_USER_ID);
			message.setSender(sender);

			message.setType(type);

			message.setContent(content);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromUserId", RongCloudConfig.SYSTEM_USER_ID);
			params.put("objectName", "CX:IMessage");
			params.put("content", JSON.toJSONString(message));
			// 构建队列容器
			RongCloudMessageEntity queue = new RongCloudMessageEntity();
			queue.setUrl("http://api.cn.ronghub.com/message/chatroom/broadcast.json");
			queue.setData(params);
			queue.setMessage(message);
			redisCache.lBeanPushHead(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE, JSON.toJSONString(queue));
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	@LogFlag("下载日志")
	public static void getHastory() {
		try {
			String day = "2018121016";
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("date", day);
			Map<String, String> headMap = RongCloudBuilder.buildHeader();

			HttpEntity entity = HttpUtil.post("http://api.cn.ronghub.com/message/history.json",
					RongCloudBuilder.builderData(params), headMap);

			System.out.println(entity.getHtml());
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	/**
	 * 发送单聊消息
	 * 
	 * @param userId
	 * @param receiver
	 * @param title
	 * @param content
	 * @param type
	 * @param isAsyn
	 */
	@LogFlag("发送系统消息")
	public void sendSingleMsg(UserSchema sender, UserSchema receiver, String content, Integer type, Boolean isAsyn) {
		try {
			ImMessage message = new ImMessage();
			// 发送者
			message.setSender(sender);
			// 接受者
			message.setReceiver(receiver);
			message.setType(type);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromUserId", RongCloudConfig.SYSTEM_USER_ID);
			params.put("toUserId", receiver.getUserId());
			params.put("objectName", "CX:IMessage");
			params.put("pushContent", sender.getName() + "发来一条私信");
			params.put("content", JSON.toJSONString(message));
			// 构建队列容器
			RongCloudMessageEntity queue = new RongCloudMessageEntity();
			queue.setUrl("http://api-cn.ronghub.com/message/private/publish.json");
			queue.setData(params);
			queue.setMessage(message);
			if (isAsyn) {
				redisCache.lBeanPushHead(CacheConstants.RONG_CLOUD_MESSAGE_QUEUE, JSON.toJSONString(queue));
				return;
			}
			rongCloudQueueTask.sendMessage(queue);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

	public static void main(String[] args) throws Exception {
		try {
			ImMessage message = new ImMessage();
			// 发送者

			UserSchema sender = new UserSchema();
			sender.setUserId(RongCloudConfig.SYSTEM_USER_ID);
			message.setSender(sender);
			// 接受者
			UserSchema receiver = new UserSchema();
			receiver.setUserId("105539");
			message.setReceiver(receiver);

			message.setType(999);

			message.setContent("测试测试");
			message.setContent(
					"<img src='https://tva2.sinaimg.cn/crop.0.0.180.180.180/8c6d19f7jw1e8qgp5bmzyj2050050aa8.jpg' /><font color='red'>红色字体</font> 普通字体 <a href='https://baidu.com'><font color='green'>超链接</font></a>");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("fromUserId", RongCloudConfig.SYSTEM_USER_ID);
			params.put("toUserId", receiver.getUserId());
			params.put("objectName", "CX:IMessage");
			params.put("pushContent", "测试富文本消息");
			params.put("content", JSON.toJSONString(message));
			// 构建队列容器
			RongCloudMessageEntity queue = new RongCloudMessageEntity();
			queue.setUrl("http://api.cn.ronghub.com/message/system/publish.json");
			queue.setData(params);
			queue.setMessage(message);
			new RongCloudQueueTask().sendMessage(queue);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}

}
