package com.jiujun.voice.modules.apps.home.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.home.domain.BannerInfo;

/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class BannerRespVO extends BaseRespVO {
	
	@DocFlag("banner宣传位列表")
	private List<BannerInfo> data;

	public List<BannerInfo> getData() {
		return data;
	}

	public void setData(List<BannerInfo> data) {
		this.data = data;
	}

	
}
