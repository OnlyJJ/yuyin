package com.jiujun.voice.modules.apps.jewel.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 提现申请
 * 
 * @author Shao.x
 * @date 2019年1月14日
 */
@SuppressWarnings("serial")
public class DrawReqVO extends BaseReqVO {
	@ParamCheck
	@DocFlag("提现金额")
	private Integer jewel;

	public Integer getJewel() {
		return jewel;
	}

	public void setJewel(Integer jewel) {
		this.jewel = jewel;
	}

	
}
