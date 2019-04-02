package com.jiujun.voice.modules.apps.topper.cmd.vo;

import com.jiujun.voice.common.cmd.vo.PagerReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class TopperReqVO extends PagerReqVO{

	@ParamCheck
	@DocFlag("类型，0总榜，1日榜 2周榜 3月榜")
	private Integer type;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
