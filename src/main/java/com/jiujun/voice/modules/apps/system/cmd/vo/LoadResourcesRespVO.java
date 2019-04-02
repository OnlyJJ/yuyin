package com.jiujun.voice.modules.apps.system.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.system.domain.AppResource;

@SuppressWarnings("serial")
public class LoadResourcesRespVO extends BaseRespVO{

	@DocFlag("资源列表")
	private List<AppResource> resources;

	public List<AppResource> getResources() {
		return resources;
	}

	public void setResources(List<AppResource> resources) {
		this.resources = resources;
	}
	
	
}
