package com.jiujun.voice.modules.apps.topper.cmd.vo;

import com.jiujun.voice.common.cmd.vo.PagerRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.topper.cmd.vo.schema.UserExpSchema;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ExpTopperRespVO extends PagerRespVO<UserExpSchema>{


	@DocFlag("我的排名信息")
	private UserExpSchema current;

	public UserExpSchema getCurrent() {
		return current;
	}

	public void setCurrent(UserExpSchema current) {
		this.current = current;
	}
	
	
	
	
}
