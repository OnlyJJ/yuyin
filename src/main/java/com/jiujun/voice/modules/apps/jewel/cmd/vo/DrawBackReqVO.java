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
public class DrawBackReqVO extends BaseReqVO {
	@ParamCheck
	@DocFlag("审核状态，0-待审核，1-成功，2-失败 3待打款")
	private Integer status;

	@ParamCheck
	@DocFlag("提现记录id")
	private Integer recordId;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	 
	
}
