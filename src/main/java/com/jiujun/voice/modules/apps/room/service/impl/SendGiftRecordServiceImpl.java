package com.jiujun.voice.modules.apps.room.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.constants.Constants;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
import com.jiujun.voice.modules.apps.gift.service.GiftInfoService;
import com.jiujun.voice.modules.apps.room.cmd.schema.RoomGiftRecordSchema;
import com.jiujun.voice.modules.apps.room.dao.SendGiftRecordDao;
import com.jiujun.voice.modules.apps.room.domain.SendGiftRecord;
import com.jiujun.voice.modules.apps.room.schema.LevelMsgSchema;
import com.jiujun.voice.modules.apps.room.schema.SendGiftMsgSchema;
import com.jiujun.voice.modules.apps.room.service.SendGiftRecordService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.useraccount.domain.BackpackInfo;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.BackPackService;
import com.jiujun.voice.modules.apps.user.useraccount.service.TradeService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.caller.entity.SendGiftEntity;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

/**
 * @author Coody
 *
 */
@Service
public class SendGiftRecordServiceImpl implements SendGiftRecordService {

	@Resource
	SendGiftRecordDao sendGiftRecordDao;
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserInfoService userInfoService;
	@Resource
	GiftInfoService giftInfoService;
	@Resource
	IMessageHandle messageHandle;
	@Resource
	TradeService tradeService;
	@Resource
	BackPackService backPackService;

	@Resource
	RedisCache redisCache;
	/** 单次送礼最大限制 */
	private static final int MAX_SENDGIFT_GOLD = 10000000;
	/** 单次送礼最小限制 */
	private static final int MIN_SENDGIFT_GOLD = 10;
	/** 个人收礼记录查询限制，10000条 */
	private static final int MAX_RECORD = 10000;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void sendGift(String roomId, String fromUserId, String toUserId, int giftId, int num, boolean isExtends) {
		// 礼物信息
		GiftInfo gift = giftInfoService.getGiftInfo(giftId);
		if (gift == null) {
			throw new CmdException(ErrorCode.ERROR_2011);
		}
		if (gift.getGiftType() == null) {
			throw new CmdException(ErrorCode.ERROR_1034);
		}
		/**
		 * 扣款过程
		 */
		Long backpackChange = backPackService.subBackPack(fromUserId, toUserId, gift.getGiftId().toString(), 1, num,
				"赠送礼物优先扣去背包");
		if (!isExtends) {
			if (backpackChange < num) {
				throw new CmdException(ErrorCode.ERROR_3008);
			}
		}
		num = num - backpackChange.intValue();
		int price = (gift.getPrice() * num);
		if (num > 0) {
			if (gift.getBuyable() != 1) {
				throw new CmdException(ErrorCode.ERROR_3006);
			}
			if (price > MAX_SENDGIFT_GOLD || price < MIN_SENDGIFT_GOLD) {
				throw new CmdException(ErrorCode.ERROR_2012);
			}
			// 校验金币是否足够，此方式只为过滤金币不足时，频繁的无用请求
			UserAccount account = userAccountService.getUserAccountByUserId(fromUserId);
			if (account == null) {
				throw new CmdException(ErrorCode.ERROR_1009);
			}
			if (gift.getGiftType() == 1) {
				// 扣金币
				if (account.getGold() < price) {
					throw new CmdException(ErrorCode.ERROR_2013);
				}
				// 扣金币
				Long code = tradeService.changeGold(fromUserId, -price, "赠送礼物扣除金币");
				if (code < 1) {
					throw new CmdException(ErrorCode.ERROR_1048);
				}
			}
			if (gift.getGiftType() == 2) {
				// 扣元宝
				if (account.getIngot() < price) {
					throw new CmdException(ErrorCode.ERROR_2013);
				}
				// 扣金币
				Long code = tradeService.changeIngot(fromUserId, -price, "赠送礼物扣除元宝");
				if (code < 1) {
					throw new CmdException(ErrorCode.ERROR_1048);
				}
			}
		}
		// 加送礼用户经验
		if (gift.getExpForSender() != null && gift.getExpForSender() > 0) {
			userAccountService.addExp(fromUserId, gift.getExpForSender() * num);
		}
		// 加收礼用户魅力
		if (gift.getExpForReceiver() != null && gift.getExpForReceiver() > 0) {
			userAccountService.addCharm(toUserId, gift.getExpForReceiver() * num);
		}
		// 加收礼用户钻石
		int jewel = gift.getRate() / 100 * price;
		if (jewel > 0) {
			tradeService.changeJewel(toUserId, jewel, "收到礼物增加钻石");
		}
		// 送礼记录
		SendGiftRecord sgr = new SendGiftRecord();
		sgr.setFromUserId(fromUserId);
		sgr.setToUserId(toUserId);
		sgr.setGiftId(giftId);
		sgr.setNum(num);
		sgr.setExp(gift.getExpForSender() * num);
		sgr.setCharm(gift.getExpForReceiver() * num);
		sgr.setJewel(jewel);
		sgr.setRoomId(roomId);
		long recordId = sendGiftRecordDao.insert(sgr);

		UserSchema fromUser = userInfoService.getUserSchema(fromUserId);
		UserSchema toUser = userInfoService.getUserSchema(toUserId);
		if (fromUser == null || toUser == null) {
			throw new CmdException(ErrorCode.ERROR_1009);
		}
		// 升级
		Long upRichCode = userAccountService.upUserLevel(fromUserId);
		if (upRichCode > 0) {
			// 发送礼用户财富升级消息
			LevelMsgSchema levelMsgModel = new LevelMsgSchema();
			UserAccount afterAccount = userAccountService.getUserAccountByUserId(fromUserId);
			if (null != afterAccount && null != fromUser) {
				levelMsgModel.setRoomId(roomId);
				levelMsgModel.setUserId(fromUserId);
				levelMsgModel.setUserName(fromUser.getName());
				levelMsgModel.setLevel(afterAccount.getExpLevel());
				messageHandle.sendRoomMsg(roomId, JSON.toJSONString(levelMsgModel), MsgEnum.UP_RICH_LEVEL.getType());
			}
		}
		Long upCharmCode = userAccountService.upCharmLevel(toUserId);
		if (upCharmCode > 0) {
			// 发收礼用户魅力升级消息
			LevelMsgSchema levelMsgModel = new LevelMsgSchema();
			UserAccount afterAccount = userAccountService.getUserAccountByUserId(toUserId);
			if (null != afterAccount && null != toUser) {
				levelMsgModel.setRoomId(roomId);
				levelMsgModel.setUserId(toUserId);
				levelMsgModel.setUserName(toUser.getName());
				levelMsgModel.setLevel(afterAccount.getCharmLevel());
				messageHandle.sendRoomMsg(roomId, JSON.toJSONString(levelMsgModel), MsgEnum.UP_CHARM_LEVEL.getType());
			}
		}

		// 发送礼消息
		SendGiftMsgSchema msgModel = new SendGiftMsgSchema();
		msgModel.setFromUserId(fromUserId);
		msgModel.setFromUserName(fromUser.getName());
		msgModel.setToUserId(toUserId);
		msgModel.setToUserName(toUser.getName());
		msgModel.setRoomId(roomId);
		msgModel.setGiftId(giftId);
		msgModel.setGiftName(gift.getName());
		msgModel.setGiftNum(num);
		msgModel.setGiftImg(gift.getIco());
		msgModel.setSpecialId(gift.getSpecialId());
		messageHandle.sendRoomMsg(roomId, JSON.toJSONString(msgModel), MsgEnum.SEND_GIFT.getType());

		SendGiftEntity sendEntity = new SendGiftEntity();
		sendEntity.setFromUserId(fromUserId);
		sendEntity.setToUserId(toUserId);
		sendEntity.setRoomId(roomId);
		sendEntity.setGiftId(giftId);
		sendEntity.setNum(num);
		redisCache.lBeanPushTail(CacheConstants.CALLER_SENDGIFT_QUEUE, sendEntity);

		// 记录房间送礼缓存,日，周
		Date now = new Date();
		String giftRecordKey = CacheConstants.ROOM_GIFT_RECORD + recordId;
		String dayRankKey = CacheConstants.ROOM_GIFT_RECORD_DAY + roomId + DateUtils.getDayCode();
		String weekRankKey = CacheConstants.ROOM_GIFT_RECORD_WEEK + roomId + DateUtils.getWeekCode(0);
		String userRecivekey = CacheConstants.USER_RECIVEGIFT_RECORD + toUserId;
		double sorce = 0 - now.getTime();
		String recordIdStr = String.valueOf(recordId);
		redisCache.zadd(dayRankKey, sorce, recordIdStr);
		redisCache.zadd(weekRankKey, sorce, recordIdStr);
		redisCache.zadd(userRecivekey, sorce, recordIdStr);

		RoomGiftRecordSchema rgr = new RoomGiftRecordSchema();
		rgr.setFromUserId(fromUserId);
		rgr.setToUserId(toUserId);
		rgr.setFromName(fromUser.getName());
		rgr.setToUserName(toUser.getName());
		rgr.setGiftId(giftId);
		rgr.setGiftName(gift.getName());
		rgr.setGiftImg(gift.getIco());
		rgr.setGiftNum(num);
		rgr.setSendTime(now.getTime());
		redisCache.setCache(giftRecordKey, rgr);

		// 删除用户信息缓存
		String sendkey = CacheConstants.USER_ACCOUNT + Constants.SEPARATOR_COLON + fromUserId;
		String recivekey = CacheConstants.USER_ACCOUNT + Constants.SEPARATOR_COLON + toUserId;
		redisCache.delCache(sendkey);
		redisCache.delCache(recivekey);

	}

	@Transacted
	@Override
	public void sendGiftFromBackpack(String fromUserId, String toUserId, String roomId, String correId, Integer type,
			Integer num) {

		BackpackInfo backPack = backPackService.getBackPackInfo(fromUserId, correId, type);
		if (backPack == null || backPack.getNum() < num) {
			throw new CmdException(ErrorCode.ERROR_3008);
		}
		sendGift(roomId, fromUserId, toUserId, StringUtil.toInteger(correId), num, false);
	}

	@Override
	public List<SendGiftRecord> getSendGiftRecord(String roomId, Date beginTime, Date endTime) {
		return sendGiftRecordDao.getSendGiftRecord(roomId, beginTime, endTime);
	}

	@Override
	public Pager getRoomGiftRecord(String roomId, int type, int pageSize, int pageNO) {
		// 从缓存里获取
		int start = pageNO > 1 ? pageSize * (pageNO - 1) - 1 : 0;
		int end = pageNO > 1 ? pageSize * pageNO - 1 : pageSize - 1;
		String key = "";
		switch (type) {
		case 1: // 日记录
			key = CacheConstants.ROOM_GIFT_RECORD_DAY + roomId + DateUtils.getDayCode();
			break;
		case 2: // 周记录
			key = CacheConstants.ROOM_GIFT_RECORD_WEEK + roomId + DateUtils.getWeekCode(0);
			break;
		default:
			throw new CmdException(ErrorCode.ERROR_501);
		}
		Pager page = getPage(roomId, key, start, end, type);
		page.setPageNo(pageNO);
		page.setPageSize(pageSize);
		return page;
	}

	private Pager getPage(String roomId, String key, int start, int end, int type) {
		Pager page = new Pager();
		List<RoomGiftRecordSchema> data = new ArrayList<RoomGiftRecordSchema>();
		int total = (int) redisCache.zcard(key);
		page.setCount(total);
		List<String> sorts = redisCache.zlist(key, start, end);
		if (sorts != null && sorts.size() > 0) {
			for (String recordId : sorts) {
				String giftRecordKey = CacheConstants.ROOM_GIFT_RECORD + recordId;
				RoomGiftRecordSchema rgr = redisCache.getCache(giftRecordKey);
				if (rgr != null) {
					data.add(rgr);
				}
				page.setData(data);
			}
		} else { // 从DB加载
			Date now = new Date();
			Date beginTime = null;
			Date endTime = null;
			if (type == 1) {
				beginTime = DateUtils.getDayFirstTime(now);
				endTime = DateUtils.getDayLastTime(now);
			} else if (type == 2) {
				beginTime = DateUtils.getWeekFirstTime();
				endTime = now;
			}
			List<SendGiftRecord> list = getSendGiftRecord(roomId, beginTime, endTime);
			if (list != null && list.size() > 0) {
				for (SendGiftRecord sgr : list) {
					String fromUserId = sgr.getFromUserId();
					String toUserId = sgr.getToUserId();
					UserInfo fromUser = userInfoService.getUserInfo(fromUserId);
					UserInfo toUser = userInfoService.getUserInfo(toUserId);
					if (fromUser == null || toUser == null) {
						continue;
					}
					GiftInfo gift = giftInfoService.getGiftInfo(sgr.getGiftId());
					if (gift == null) {
						continue;
					}
					RoomGiftRecordSchema rgr = new RoomGiftRecordSchema();
					rgr.setFromUserId(fromUserId);
					rgr.setToUserId(toUserId);
					rgr.setFromName(fromUser.getName());
					rgr.setToUserName(toUser.getName());
					rgr.setGiftId(sgr.getGiftId());
					rgr.setGiftName(gift.getName());
					rgr.setGiftImg(gift.getIco());
					rgr.setGiftNum(sgr.getNum());
					rgr.setSendTime(sgr.getCreateTime().getTime());
					long recordId = sgr.getId();
					String giftRecordKey = CacheConstants.ROOM_GIFT_RECORD + recordId;
					redisCache.setCache(giftRecordKey, rgr);
					double sorce = 0 - now.getTime();
					redisCache.zadd(key, sorce, String.valueOf(recordId));
					data.add(rgr);
				}
				// page.setData(data);
			}
		}
		return page;
	}

	@Override
	public Pager getUserGiftRecord(String userId, int pageSize, int pageNO) {
		int start = pageNO > 1 ? pageSize * (pageNO - 1) - 1 : 0;
		int end = pageNO > 1 ? pageSize * pageNO - 1 : pageSize - 1;
		String key = CacheConstants.USER_RECIVEGIFT_RECORD + userId;
		Pager page = new Pager();
		List<RoomGiftRecordSchema> data = new ArrayList<RoomGiftRecordSchema>();
		int total = (int) redisCache.zcard(key);
		page.setCount(total);
		page.setPageSize(pageSize);
		page.setPageNo(pageNO);
		List<String> sorts = redisCache.zlist(key, start, end);
		if (sorts != null && sorts.size() > 0) {
			for (String recordId : sorts) {
				String giftRecordKey = CacheConstants.ROOM_GIFT_RECORD + recordId;
				RoomGiftRecordSchema rgr = redisCache.getCache(giftRecordKey);
				if (rgr != null) {
					data.add(rgr);
				}
				page.setData(data);
			}
		} else { // 从DB加载
			Date now = new Date();
			List<SendGiftRecord> list = sendGiftRecordDao.getUserReciveGift(userId, MAX_RECORD);
			if (list != null && list.size() > 0) {
				for (SendGiftRecord sgr : list) {
					String fromUserId = sgr.getFromUserId();
					String toUserId = sgr.getToUserId();
					UserInfo fromUser = userInfoService.getUserInfo(fromUserId);
					UserInfo toUser = userInfoService.getUserInfo(toUserId);
					if (fromUser == null || toUser == null) {
						continue;
					}
					GiftInfo gift = giftInfoService.getGiftInfo(sgr.getGiftId());
					if (gift == null) {
						continue;
					}
					RoomGiftRecordSchema rgr = new RoomGiftRecordSchema();
					rgr.setFromUserId(fromUserId);
					rgr.setToUserId(toUserId);
					rgr.setFromName(fromUser.getName());
					rgr.setToUserName(toUser.getName());
					rgr.setGiftId(sgr.getGiftId());
					rgr.setGiftName(gift.getName());
					rgr.setGiftImg(gift.getIco());
					rgr.setGiftNum(sgr.getNum());
					rgr.setSendTime(sgr.getCreateTime().getTime());
					long recordId = sgr.getId();
					String giftRecordKey = CacheConstants.ROOM_GIFT_RECORD + recordId;
					redisCache.setCache(giftRecordKey, rgr);
					double sorce = 0 - now.getTime();
					redisCache.zadd(key, sorce, String.valueOf(recordId));
					data.add(rgr);
				}
				// page.setData(data);
			}
		}
		return page;
	}

	@Override
	public Long insert(SendGiftRecord sgr) {
		return sendGiftRecordDao.insert(sgr);
	}

}
