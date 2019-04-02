package com.jiujun.voice.modules.apps.pay.caller;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.logger.annotation.LogFlag;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.modules.apps.pay.domain.PayRecord;
import com.jiujun.voice.modules.apps.pay.service.PayService;
import com.jiujun.voice.modules.apps.user.useraccount.service.BackPackService;
import com.jiujun.voice.modules.apps.user.useraccount.service.TradeService;
import com.jiujun.voice.modules.caller.iface.AbstractPayCaller;

@Component
public class FirstChargeCaller extends AbstractPayCaller{
	@Resource
	private BackPackService backPackService;
	@Resource
	private TradeService tradeService;
	@Resource
	private PayService payService;
	
	private final String correId="6";//代表礼物关联ID是棒棒糖
	
	@LogFlag("首充赠送礼物任务")
	@Override
	@Transacted
	public void execute(PayRecord entity) {
		LogUtil.logger.info("处理首充赠送礼物任务>>" + entity.toString());
		//检查是否是首充
		PayRecord payRecord	= payService.getPayRecordByUserId(entity.getUserId());
		if(payRecord.getId()!=entity.getId()){
			LogUtil.logger.info("不是首充，充值数据放弃>>" + entity.toString());
			return;
		}
		LogUtil.logger.info("写入背包礼物记录");
		//添加背包礼物记录
		backPackService.addBackPack(entity.getUserId(),"0", correId, 1, 50, "首充赠送50棒棒糖");
		LogUtil.logger.info("赠送1000元宝");
		//赠送1000元宝
		tradeService.changeIngot(entity.getUserId(), 1000, "首充赠送1000元宝");
	}

}
