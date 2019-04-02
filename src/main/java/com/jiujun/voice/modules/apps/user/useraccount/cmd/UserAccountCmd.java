package com.jiujun.voice.modules.apps.user.useraccount.cmd;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
import com.jiujun.voice.modules.apps.gift.service.GiftInfoService;
import com.jiujun.voice.modules.apps.topper.domain.UserSendTopper;
import com.jiujun.voice.modules.apps.topper.service.UserSendTopperService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.GiftTopperReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.GiftTopperRespVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.MyBackPackRespVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.GiftTopperSchema;
import com.jiujun.voice.modules.apps.user.useraccount.schema.BackPackSchema;
import com.jiujun.voice.modules.apps.user.useraccount.service.BackPackService;

/**
 * @author Coody
 *
 */
@CmdOpen("userAccount")
@DocFlag("用户账务中心")
public class UserAccountCmd extends RootCmd {

	@Resource
	UserSendTopperService userSendTopperService;
	@Resource
	GiftInfoService giftInfoService;
	@Resource
	BackPackService backPackService;

	@CmdAction
	@DocFlag("礼物累计接口")
	public GiftTopperRespVO giftTopper(GiftTopperReqVO req) {
		List<UserSendTopper> toppers = userSendTopperService.getUserReceiveGiftTopper(req.getTargetUserId());
		if (toppers == null) {
			return new GiftTopperRespVO();
		}
		List<Integer> giftIds = PropertUtil.getFieldValues(toppers, "giftId");
		Map<Integer, GiftInfo> giftMap = giftInfoService.getGiftInfos(giftIds.toArray(new Integer[] {}));
		List<GiftTopperSchema> giftToppers = new ArrayList<GiftTopperSchema>();
		for (UserSendTopper topper : toppers) {
			GiftInfo giftInfo = giftMap.get(topper.getGiftId());
			if (StringUtil.isNullOrEmpty(giftInfo)) {
				continue;
			}
			GiftTopperSchema schema = new GiftTopperSchema();
			schema.setGiftIcon(giftInfo.getIco());
			schema.setGiftId(topper.getGiftId());
			schema.setGiftName(giftInfo.getName());
			schema.setNum(topper.getNum());
			giftToppers.add(schema);
		}
		GiftTopperRespVO resp = new GiftTopperRespVO();
		resp.setGiftToppers(giftToppers);
		return resp;
	}

	@CmdAction
	@DocFlag("我的背包记录表")
	public MyBackPackRespVO getBackPackInfo(BaseReqVO req) {

		List<BackPackSchema> backpacks = backPackService.getBackpackInfo(req.getHeader().getUserId());
		MyBackPackRespVO resp = new MyBackPackRespVO();
		resp.setBackpacks(backpacks);
		return resp;

	}


}
