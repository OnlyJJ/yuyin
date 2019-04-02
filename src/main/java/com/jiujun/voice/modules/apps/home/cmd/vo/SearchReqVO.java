package com.jiujun.voice.modules.apps.home.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 搜索请求实体
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class SearchReqVO extends BaseReqVO {
	
	@DocFlag("搜索条件：房间号/用户id/昵称")
	@ParamCheck
	private String condition;

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	
}
