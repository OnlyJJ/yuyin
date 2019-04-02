package com.jiujun.voice.modules.apps.room.cmd.vo;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.verification.annotation.ParamCheck;

/**
 * 编辑房间话题请求实体
 * @author Shao.x
 * @date 2018年11月30日
 */
@SuppressWarnings("serial")
public class EditThemeReqVO extends RoomBaseReqVO {
	
	@DocFlag("话题，长度限制：12字")
	@ParamCheck
	private String theme;

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}
	
}
