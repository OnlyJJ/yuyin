package com.jiujun.voice.modules.apps.home.cmd.vo;

import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;
/**
 * 房间类型下的所有房间请求实体
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class BannerReqVO extends BaseReqVO {
	
	@DocFlag("展示位置，1-首页，2-发现，3-充值中心")
	@ParamCheck
	private Integer showPlace;

	public Integer getShowPlace() {
		return showPlace;
	}

	public void setShowPlace(Integer showPlace) {
		this.showPlace = showPlace;
	}
	
	 
}
