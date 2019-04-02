package com.jiujun.voice.modules.apps.gift.cmd;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.gift.cmd.vo.LoadGiftsRespVO;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
import com.jiujun.voice.modules.apps.gift.service.GiftInfoService;
/**
 * 
 * @author Coody
 *
 */
@CmdOpen("gift")
@DocFlag("礼物中心")
public class GiftCmd extends RootCmd{

	@Resource
	GiftInfoService giftInfoService;
	
	
	@CmdAction
	@LoginIgnore
	@DocFlag("加载礼物列表")
	public LoadGiftsRespVO loadGifts(BaseReqVO req) {
		List<GiftInfo> gifts=giftInfoService.loadGiftInfos();
		if(StringUtil.isNullOrEmpty(gifts)) {
			return new LoadGiftsRespVO().pushErrorCode(ErrorCode.ERROR_1012);
		}
		LoadGiftsRespVO resp=new LoadGiftsRespVO();
		resp.setGifts(new ArrayList<GiftInfo>());
		for(GiftInfo gift:gifts){
			if(gift.getIsShow()==0){
				continue;
			}
			resp.getGifts().add(gift);
		}
		return resp;
	}
}
