package com.jiujun.voice.modules.apps.user.relation.cmd.vo;

import java.util.List;

import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.user.relation.schema.RelationApplySchema;
/**
 * 
 * @author Coody
 *
 */
@SuppressWarnings("serial")
public class ApplyRecordsRespVO extends BaseRespVO{

	
	@DocFlag("通知列表")
	private List<RelationApplySchema> applys;

	public List<RelationApplySchema> getApplys() {
		return applys;
	}

	public void setApplys(List<RelationApplySchema> applys) {
		this.applys = applys;
	}
	
	
	
}
