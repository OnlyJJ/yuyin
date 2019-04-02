package com.jiujun.voice.modules.apps.user.invite.caller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.modules.apps.pay.domain.PayRecord;
import com.jiujun.voice.modules.apps.user.invite.domain.InviteRecord;
import com.jiujun.voice.modules.apps.user.invite.service.InviteService;
import com.jiujun.voice.modules.apps.user.useraccount.service.TradeService;
import com.jiujun.voice.modules.caller.iface.AbstractPayCaller;

@Component
public class InvitePayPrizeCaller extends AbstractPayCaller {

	@Resource
	InviteService inviteService;
	@Resource
	TradeService tradeService;

	@LogFlag("邀请好友充值任务")
	@Override
	@Transacted
	public void execute(PayRecord entity) {
		LogUtil.logger.info("处理好友邀请任务>>" + entity.toString());
		// 检查邀请关系
		InviteRecord record = inviteService.getInviteRecordByUserId(entity.getUserId());
		if (record == null) {
			LogUtil.logger.info("暂无邀请者，充值数据放弃>>" + entity.toString());
			return;
		}
		if (record.getStatus() != 1) {
			LogUtil.logger.info("邀请无效，充值数据放弃>>" + entity.toString());
			return;
		}
		// 检查邀请关系是否小于30天
		Date targeTime = DateUtils.addDate(record.getCreateTime(), 30);
		if (targeTime.getTime() < System.currentTimeMillis()) {
			LogUtil.logger.info("邀请记录超过30天，充值数据放弃>>" + entity.toString());
			return;
		}
		// 赠送5%金币
		Double sendGold = entity.getGold() * 5 / 100;
		if (sendGold < 1) {
			LogUtil.logger.info("充值金额过小，充值数据放弃>>" + entity.toString());
			return;
		}
		tradeService.changeGold(record.getInviter(), sendGold.intValue(), "邀请好友充值奖励");
		LogUtil.logger.info("写入邀请奖励记录");
		// 添加奖励记录
		inviteService.pushUserInvitePrize(record.getInviter(), 0, sendGold.intValue());
	}
}
