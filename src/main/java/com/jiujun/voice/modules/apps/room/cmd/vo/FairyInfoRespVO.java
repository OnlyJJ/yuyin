package com.jiujun.voice.modules.apps.room.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;

@SuppressWarnings("serial")
public class FairyInfoRespVO extends BaseRespVO{
	
	
	/**
	 * 活动名称
	 */
	@DocFlag("活动名称")
	private String activityName;
	/**
	 * 显示图片
	 */
	@DocFlag("显示图片")
	private List<String> showImgs;
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public List<String> getShowImgs() {
		return showImgs;
	}
	public void setShowImgs(List<String> showImgs) {
		this.showImgs = showImgs;
	}
	
	
}
