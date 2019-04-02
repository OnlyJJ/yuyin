package com.jiujun.voice.modules.apps.topper.cmd.vo.schema;

import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.schema.UserGeneralSchema;
/**
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class UserExpSchema extends UserGeneralSchema{

	@DocFlag("经验值")
	private Long exp;
	@DocFlag("排名,用于我的排名时存在")
	private Integer rank;
	
	
	
	

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Long getExp() {
		return exp;
	}

	public void setExp(Long exp) {
		this.exp = exp;
	}
	
	
	
}
