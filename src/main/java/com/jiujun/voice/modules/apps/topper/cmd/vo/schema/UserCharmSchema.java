package com.jiujun.voice.modules.apps.topper.cmd.vo.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.schema.UserGeneralSchema;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class UserCharmSchema extends UserGeneralSchema{

	@DocFlag("魅力值")
	private Long charm;
	
	@DocFlag("排名,用于我的排名时存在")
	private Integer rank;
	
	
	
	

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}


	public Long getCharm() {
		return charm;
	}

	public void setCharm(Long charm) {
		this.charm = charm;
	}

	
}
