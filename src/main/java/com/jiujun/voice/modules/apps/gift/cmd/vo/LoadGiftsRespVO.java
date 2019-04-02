package com.jiujun.voice.modules.apps.gift.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.gift.domain.GiftInfo;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class LoadGiftsRespVO extends BaseRespVO{

	@DocFlag("礼物列表")
	private List<GiftInfo> gifts;

	public List<GiftInfo> getGifts() {
		return gifts;
	}

	public void setGifts(List<GiftInfo> gifts) {
		this.gifts = gifts;
	}
	
	
	
}
