package com.jiujun.voice.modules.apps.jewel.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.modules.apps.jewel.cmd.schema.DrawJewelSchema;
import com.jiujun.voice.modules.apps.jewel.dao.DrawJewelRecordDao;
import com.jiujun.voice.modules.apps.jewel.domain.CardInfo;
import com.jiujun.voice.modules.apps.jewel.domain.DrawJewelRecord;
import com.jiujun.voice.modules.apps.jewel.enums.Jewel;
import com.jiujun.voice.modules.apps.jewel.service.CardInfoService;
import com.jiujun.voice.modules.apps.jewel.service.DrawJewelRecordService;
import com.jiujun.voice.modules.apps.jewel.service.DrawJewelService;
import com.jiujun.voice.modules.apps.user.useraccount.service.TradeService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
/**
 * 
 * @author Coody
 *
 */
@Service
public class DrawJewelRecordServiceImpl implements DrawJewelRecordService{

	@Resource
	DrawJewelRecordDao drawJewelRecordDao;
	@Resource
	UserAccountService userAccountService;
	@Resource
	DrawJewelService drawJewelService;
	@Resource
	CardInfoService cardInfoService;
	@Resource
	TradeService tradeService;
	
	/** 最低提现限制 */
	private static final int MIN_DRAW_JEWEL = 5000;

	
	@Override
	public Long getFrozenJewel(String userId) {
		return drawJewelRecordDao.getFrozenJewel(userId);
	}
	
	@Override
	@Transacted
	public Long draw(String userId, Integer drawJewel) {
		// 是否绑定银行卡
		CardInfo card = cardInfoService.getCardInfo(userId);
		if(card == null) {
			throw new CmdException(ErrorCode.ERROR_3002);
		}
		// 最低提现限制
		if(drawJewel < MIN_DRAW_JEWEL) {
			throw new CmdException(ErrorCode.ERROR_3001);
		}
		// 扣除账户钻石
		tradeService.changeJewel(userId, -drawJewel,"提现扣除钻石");
		
		// 加提现记录
		DrawJewelRecord record = new DrawJewelRecord();
		record.setUserId(userId);
		record.setDrawJewel(drawJewel);
		record.setStatus(Jewel.Status.VERIFY.getValue());
		return drawJewelRecordDao.insert(record);
	}
	
	@Override
	public Pager getDrawRecord(String userId, int pageSize, int pageNo) {
		Pager page = new Pager();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		List<DrawJewelRecord> list = drawJewelRecordDao.listRecord(userId);
		if(list != null && list.size() >0) {
			List<DrawJewelSchema> ret = new ArrayList<DrawJewelSchema>();
			int index = pageNo > 1 ? (pageNo - 1) * pageSize : 0;
			int size = list.size();
			page.setCount(size);
			for(int i=0; i<pageSize; i++) {
				if(size <= index) {
					break;
				}
				DrawJewelSchema schema = new DrawJewelSchema();
				DrawJewelRecord djr = list.get(index);
				schema.setDrawJewel(djr.getDrawJewel());
				schema.setStatus(djr.getStatus());
				schema.setTime(DateUtils.toString(djr.getCreateTime(), DateUtils.DATA_PATTERN));
				ret.add(schema);
				index++;
			}
			page.setData(ret);
		}
		return page;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public Long drawResultBack(String userId, int recordId, int status) {
		Long code = 0L;
		DrawJewelRecord record = drawJewelRecordDao.getById(recordId);
		if(record != null) {
			String recUser = record.getUserId();
			if(recUser.equals(userId)) {
				switch(status) {
				case 0:
					break;
				case 1: // 成功才处理，其他状态暂时不处理
					// 更新成功状态
					drawJewelRecordDao.update(record.getId(), 1);
					// 更新总收益
					drawJewelService.addTotalJewel(record.getUserId(), record.getDrawJewel());
					break;
				case 2:
					break;
				default:
					break;
				}
			}
		} 
		return code;
	}

}
