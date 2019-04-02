package com.jiujun.voice.modules.apps.topper.cmd;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.room.cmd.vo.RoomGIftRecordReqVO;
import com.jiujun.voice.modules.apps.room.cmd.vo.RoomGiftRecordRespVO;
import com.jiujun.voice.modules.apps.room.service.SendGiftRecordService;
import com.jiujun.voice.modules.apps.topper.cmd.vo.CharmTopperRespVO;
import com.jiujun.voice.modules.apps.topper.cmd.vo.ExpTopperRespVO;
import com.jiujun.voice.modules.apps.topper.cmd.vo.TopperReqVO;
import com.jiujun.voice.modules.apps.topper.cmd.vo.schema.UserCharmSchema;
import com.jiujun.voice.modules.apps.topper.cmd.vo.schema.UserExpSchema;
import com.jiujun.voice.modules.apps.topper.service.UserSendTopperService;

/**
 * @author Coody
 *
 */
@DocFlag("榜单中心")
@CmdOpen("topper")
public class TopperCmd extends RootCmd{

	
	@Resource
	UserSendTopperService userSendTopperService;
	@Resource
	SendGiftRecordService sendGiftRecordService;
	
	
	@CmdAction
	@DocFlag("财富榜接口")
	public ExpTopperRespVO expTopper(TopperReqVO req){
		Pager pager=req.toPager();
		pager =userSendTopperService.getExpTopperPager(req.getType(), pager);
		ExpTopperRespVO resp= new ExpTopperRespVO().fromPager(pager);
		if(StringUtil.isNullOrEmpty(pager.getData())){
			return resp;
		}
		UserExpSchema current=userSendTopperService.getExpRank(req.getHeader().getUserId(), req.getType());
		resp.setCurrent(current);
		return resp;
	}
	
	
	@CmdAction
	@DocFlag("魅力榜接口")
	public CharmTopperRespVO charmTopper(TopperReqVO req){
		Pager pager=req.toPager();
		pager =userSendTopperService.getCharmTopperPager(req.getType(), pager);
		CharmTopperRespVO resp= new CharmTopperRespVO().fromPager(pager);
		if(StringUtil.isNullOrEmpty(pager.getData())){
			return resp;
		}
		UserCharmSchema current=userSendTopperService.getCharmRank(req.getHeader().getUserId(), req.getType());
		resp.setCurrent(current);
		return resp;
	}
	
	@CmdAction()
	@DocFlag("房间送礼榜")
	public RoomGiftRecordRespVO getRoomGiftRecord(RoomGIftRecordReqVO req) {
		Pager pager = sendGiftRecordService.getRoomGiftRecord(req.getRoomId(), req.getType(), req.getPageSize(), req.getPageNo());
		return new RoomGiftRecordRespVO().fromPager(pager);
	} 
	
	
}
