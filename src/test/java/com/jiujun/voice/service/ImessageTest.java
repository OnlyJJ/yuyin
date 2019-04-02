package com.jiujun.voice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.VoiceApplication;
import com.jiujun.voice.common.utils.PrintException;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.im.msg.ImMessage;
import com.jiujun.voice.modules.im.rongcloud.config.RongCloudConfig;
import com.jiujun.voice.modules.im.rongcloud.entity.RongCloudMessageEntity;
import com.jiujun.voice.modules.im.rongcloud.task.RongCloudQueueTask;

@RunWith(SpringRunner.class)
@SpringBootTest( classes = VoiceApplication.class)
public class ImessageTest {

	@Resource
	RongCloudQueueTask rongCloudQueueTask;
	
	@Test
	public void sendTotalRoomMsg() {
		try {
			String content="测试内容";
			Integer type=202;
			ImMessage message = new ImMessage();
			// 发送者
			UserSchema sender = new UserSchema();
			sender.setUserId(RongCloudConfig.SYSTEM_USER_ID);
			message.setSender(sender);
			message.setContent(content);
			message.setType(type);
			
			List<String> roomIds = new ArrayList<String>();
			for(int i=0;i<100;i++){
				roomIds.add(StringUtil.getRanDom(100000, 999999).toString());
				
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
			rongCloudQueueTask.sendMessage(queue);
		} catch (Exception e) {
			PrintException.printException(e);
		}
	}
	
}
