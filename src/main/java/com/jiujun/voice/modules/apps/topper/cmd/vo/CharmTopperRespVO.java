package com.jiujun.voice.modules.apps.topper.cmd.vo;

import com.jiujun.voice.common.cmd.vo.PagerRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.topper.cmd.vo.schema.UserCharmSchema;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class CharmTopperRespVO extends PagerRespVO<UserCharmSchema>{

	
	@DocFlag("我的排名信息")
	private UserCharmSchema current;

	public UserCharmSchema getCurrent() {
		return current;
	}

	public void setCurrent(UserCharmSchema current) {
		this.current = current;
	}

}
