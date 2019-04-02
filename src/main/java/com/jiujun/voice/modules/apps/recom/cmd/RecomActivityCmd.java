package com.jiujun.voice.modules.apps.recom.cmd;


import java.util.List;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.modules.apps.recom.cmd.vo.RecomActivityRespVO;
import com.jiujun.voice.modules.apps.recom.domain.RecomActivity;
import com.jiujun.voice.modules.apps.recom.service.RecomActivityService;

/**
 * 
 * @author Coody
 *
 */
@CmdOpen("recom")
@DocFlag("推荐活动")
public class RecomActivityCmd extends RootCmd{

	@Resource
	RecomActivityService  recomActivityService;
	
	
	
	@CmdAction
	@DocFlag("加载推荐活动接口")
	public RecomActivityRespVO recomActivity(BaseReqVO req){
		
		RecomActivityRespVO resp=new RecomActivityRespVO();
		List<RecomActivity>  activitys=recomActivityService.getRecomers();
		resp.setActivitys(activitys);
		return resp;
	}
	
}
