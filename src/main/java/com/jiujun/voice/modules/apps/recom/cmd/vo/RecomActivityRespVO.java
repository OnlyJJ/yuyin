package com.jiujun.voice.modules.apps.recom.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.recom.domain.RecomActivity;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class RecomActivityRespVO extends BaseRespVO{

	@DocFlag("活动列表")
	private List<RecomActivity> activitys;

	public List<RecomActivity> getActivitys() {
		return activitys;
	}

	public void setActivitys(List<RecomActivity> activitys) {
		this.activitys = activitys;
	}
	
	
	
}
