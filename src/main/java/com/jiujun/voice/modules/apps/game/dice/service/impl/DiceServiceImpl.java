package com.jiujun.voice.modules.apps.game.dice.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.game.dice.dao.DiceDao;
import com.jiujun.voice.modules.apps.game.dice.domain.GameDiceQualer;
import com.jiujun.voice.modules.apps.game.dice.domain.GameDiceRecord;
import com.jiujun.voice.modules.apps.game.dice.schema.DiceSchema;
import com.jiujun.voice.modules.apps.game.dice.service.DiceService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;
import com.jiujun.voice.modules.im.enm.MsgEnum;
import com.jiujun.voice.modules.im.rongcloud.handle.IMessageHandle;

/**
 * 
 * @author Coody
 *
 */
@Service
public class DiceServiceImpl implements DiceService {

	@Resource
	DiceDao diceDao;
	@Resource
	IMessageHandle iMessageHandle;
	@Resource
	UserInfoService userInfoService;

	@Override
	public List<GameDiceQualer> getQualer(String roomId) {
		return diceDao.getQualer(roomId);
	}

	@Override
	public Long cancelQualer(String roomId, String userId) {
		return diceDao.cancelQualer(roomId, userId);
	}

	@Override
	public void pushQualer(String roomId, List<String> userIds) {
		diceDao.pushQualer(roomId, userIds);
		if (StringUtil.isNullOrEmpty(userIds)) {
			userIds = new ArrayList<String>();
		}
		iMessageHandle.sendRoomMsg(roomId, JSON.toJSONString(userIds), MsgEnum.DICE_CHANGE.getType());
	}

	@Override
	public Long doDice(String roomId, String userId) {

		Integer diceValue = StringUtil.getRanDom(1, 6);
		GameDiceRecord record = new GameDiceRecord();
		record.setRoomId(roomId);
		record.setUserId(userId);
		record.setDiceValue(diceValue);
		Long code = diceDao.pushDiceRecord(record);
		if (code < 1) {
			return code;
		}
		DiceSchema schema = new DiceSchema();
		BeanUtils.copyProperties(record, schema);
		schema.setFrozeTime(3000);
		UserInfo userInfo = userInfoService.getUserInfo(userId);
		schema.setRemark(userInfo.getName() + "摇到" + diceValue + "点");
		// 发送消息
		iMessageHandle.sendRoomMsg(roomId, JSON.toJSONString(schema), MsgEnum.DICE_VALUE_SHOW.getType());
		return code;
	}

}
