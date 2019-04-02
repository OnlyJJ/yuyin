package com.jiujun.voice.modules.apps.room.caller;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.model.EntityModel;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
import com.jiujun.voice.modules.apps.gift.service.GiftInfoService;
import com.jiujun.voice.modules.apps.room.schema.SendGiftMsgSchema;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.caller.entity.SendGiftEntity;
import com.jiujun.voice.modules.caller.iface.AbstractSendGiftCaller;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

/**
 * 送礼超过990金币添加播报功能
 * 
 * @author Coody
 *
 */
@Component
public class SendGiftBroadcastCaller extends AbstractSendGiftCaller {

	@Resource
	GiftInfoService giftInfoService;
	@Resource
	IMessageHandle imessageHandle;
	@Resource
	UserInfoService userInfoService;

	@LogFlag("送礼播报")
	@Override
	public void execute(SendGiftEntity entity) {
		LogUtil.logger.info("送礼播报>>开始执行");
		GiftInfo giftInfo = giftInfoService.getGiftInfo(entity.getGiftId());
		Integer totalPrice = giftInfo.getPrice() * entity.getNum();
		if (totalPrice < 990) {
			LogUtil.logger.info("送礼数量小于990，不触发播报>>" + entity);
			return;
		}
		SendGiftBroadcastEntity sendGiftBroadcastEntity=new SendGiftBroadcastEntity();
		UserSchema sender=userInfoService.getUserSchema(entity.getFromUserId());
		UserSchema receiver=userInfoService.getUserSchema(entity.getToUserId());
		sendGiftBroadcastEntity.setSender(sender);
		sendGiftBroadcastEntity.setReceiver(receiver);
		
		SendGiftMsgSchema sendGift = new SendGiftMsgSchema();
		sendGift.setFromUserId(entity.getFromUserId());
		sendGift.setFromUserName(sender.getName());
		sendGift.setToUserId(receiver.getUserId());
		sendGift.setToUserName(receiver.getName());
		sendGift.setRoomId(entity.getRoomId());
		sendGift.setGiftId(entity.getGiftId());
		sendGift.setGiftName(giftInfo.getName());
		sendGift.setGiftNum(entity.getNum());
		sendGift.setGiftImg(giftInfo.getIco());
		sendGift.setSpecialId(giftInfo.getSpecialId());
		
		sendGiftBroadcastEntity.setGift(sendGift);
		imessageHandle.sendTotalRoomMsg(JSON.toJSONString(sendGiftBroadcastEntity), MsgEnum.SEND_GIFT_BROADCAST.getType());
	}
	
	
	@SuppressWarnings({"serial","unused"})
	private static class SendGiftBroadcastEntity extends EntityModel{
		
		UserSchema sender;
		
		UserSchema receiver;
		
		SendGiftMsgSchema gift;


		
		public UserSchema getSender() {
			return sender;
		}




		public void setSender(UserSchema sender) {
			this.sender = sender;
		}




		public UserSchema getReceiver() {
			return receiver;
		}


		public SendGiftMsgSchema getGift() {
			return gift;
		}


		public void setReceiver(UserSchema receiver) {
			this.receiver = receiver;
		}


		public void setGift(SendGiftMsgSchema gift) {
			this.gift = gift;
		}
		
		
	}
	

}
