package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.GiftTopperSchema;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class GiftTopperRespVO extends BaseRespVO{

	
	private List<GiftTopperSchema> giftToppers;

	public List<GiftTopperSchema> getGiftToppers() {
		return giftToppers;
	}

	public void setGiftToppers(List<GiftTopperSchema> giftToppers) {
		this.giftToppers = giftToppers;
	}
	
	
	
}
