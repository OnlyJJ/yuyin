package com.jiujun.voice.modules.apps.user.useraccount.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.modules.apps.user.useraccount.schema.BackPackSchema;

@SuppressWarnings("serial")
public class MyBackPackRespVO extends BaseRespVO{


	private List<BackPackSchema> backpacks;

	public List<BackPackSchema> getBackpacks() {
		return backpacks;
	}

	public void setBackpacks(List<BackPackSchema> backpacks) {
		this.backpacks = backpacks;
	}
	
	
}
