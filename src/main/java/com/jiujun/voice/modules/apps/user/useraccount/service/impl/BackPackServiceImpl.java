package com.jiujun.voice.modules.apps.user.useraccount.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
import com.jiujun.voice.modules.apps.gift.service.GiftInfoService;
import com.jiujun.voice.modules.apps.room.service.SendGiftRecordService;
import com.jiujun.voice.modules.apps.user.useraccount.dao.BackPackDao;
import com.jiujun.voice.modules.apps.user.useraccount.domain.BackpackInfo;
import com.jiujun.voice.modules.apps.user.useraccount.domain.BackpackRecord;
import com.jiujun.voice.modules.apps.user.useraccount.schema.BackPackSchema;
import com.jiujun.voice.modules.apps.user.useraccount.service.BackPackService;
import com.jiujun.voice.modules.apps.user.useraccount.service.TradeService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class BackPackServiceImpl implements BackPackService {

	@Resource
	BackPackDao backPackDao;
	@Resource
	GiftInfoService giftInfoService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	TradeService tradeService;
	@Resource
	SendGiftRecordService sendGiftRecordService;

	@Override
	public List<BackPackSchema> getBackpackInfo(String userId) {
		List<BackpackInfo> packs = backPackDao.getBackPacks(userId);
		if (StringUtil.isNullOrEmpty(packs)) {
			return null;
		}
		List<BackPackSchema> schemas = new ArrayList<BackPackSchema>();
		// 加载礼物类物品
		List<BackpackInfo> giftPacks = PropertUtil.getGroup(packs, "type", 1);
		if (!StringUtil.isNullOrEmpty(giftPacks)) {
			List<String> giftIds = PropertUtil.getFieldValues(giftPacks, "correId");
			Map<Integer, GiftInfo> giftMap = giftInfoService
					.getGiftInfos(StringUtil.getIntegerParas(giftIds.toArray(new String[] {})));
			for (BackpackInfo pack : giftPacks) {
				if (!giftMap.containsKey(Integer.valueOf(pack.getCorreId()))) {
					continue;
				}
				BackPackSchema schema = new BackPackSchema();
				BeanUtils.copyProperties(giftMap.get(Integer.valueOf(pack.getCorreId())), schema);
				schema.setType(1);
				schema.setCorreId(pack.getCorreId());
				schema.setNum(pack.getNum());
				schemas.add(schema);
			}
		}
		// 加载其他物品
		return schemas;
	}

	@Transacted
	@Override
	public Long addBackPack(String userId,String fromUserId, String correId, Integer type, Integer num, String remark) {

		BackpackRecord record = new BackpackRecord();
		record.setNum(num);
		record.setReceiver(userId);
		record.setSender(fromUserId);
		record.setNum(num);
		record.setRemark("赠送道具");
		Long code = backPackDao.addChangeRecord(record);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1004);
		}
		code = backPackDao.addBackpack(userId, correId, type, num);
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1004);
		}
		return code;
	}

	@Override
	public Long subBackPack(String userId,String toUserId, String correId, Integer type, Integer num, String remark) {
		while (true) {
			/**
			 * 查询背包商品数量
			 */
			BackpackInfo backPack = backPackDao.getBackPackInfo(userId, correId, type);
			if (backPack == null || backPack.getNum() == 0) {
				return 0L;
			}
			Long realChange = backPackDao.subBackpack(userId, correId, type, backPack.getNum());
			if (realChange > 0) {
				BackpackRecord record = new BackpackRecord();
				record.setNum(backPack.getNum());
				record.setReceiver(toUserId);
				record.setSender(userId);
				record.setRemark(remark);
				backPackDao.addChangeRecord(record);
				return backPack.getNum().longValue();
			}
		}
	}

	@Override
	public BackpackInfo getBackPackInfo(String userId, String correId, Integer type) {
		return backPackDao.getBackPackInfo(userId, correId, type);
	}


}
