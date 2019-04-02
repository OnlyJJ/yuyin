package com.jiujun.voice.modules.apps.system.cmd;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.filting.FiltingProcess;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.system.cmd.vo.ClientUpdateRespVO;
import com.jiujun.voice.modules.apps.system.cmd.vo.LastVersionRespVO;
import com.jiujun.voice.modules.apps.system.cmd.vo.LoadKeyWordsRespVO;
import com.jiujun.voice.modules.apps.system.cmd.vo.LoadResourcesRespVO;
import com.jiujun.voice.modules.apps.system.cmd.vo.schema.ClientAppSchema;
import com.jiujun.voice.modules.apps.system.domain.ClientApp;
import com.jiujun.voice.modules.apps.system.service.AppResourceService;
import com.jiujun.voice.modules.apps.system.service.ClientAppService;

/**
 * @author Coody
 *
 */
@CmdOpen("system")
@DocFlag("系统配置")
public class SystemCmd extends RootCmd {

	@Resource
	ClientAppService clientAppService;
	@Resource
	AppResourceService appResourceService;

	@DocFlag("版本更新接口")
	@CmdAction
	@LoginIgnore
	public ClientUpdateRespVO clientUpdate(BaseReqVO req) {
		List<ClientApp> clientApps = clientAppService.getNextClientApps(req.getHeader().getClientType(),
				req.getHeader().getPackager(), req.getHeader().getVersion());
		if (StringUtil.isNullOrEmpty(clientApps)) {
			return new ClientUpdateRespVO();
		}
		ClientApp app = clientApps.get(0);
		ClientUpdateRespVO resp = new ClientUpdateRespVO();
		BeanUtils.copyProperties(app, resp);
		if (resp.getMusted() != null && resp.getMusted() == 1) {
			return resp;
		}
		// 判断是否需要强制更新
		for (ClientApp appTemp : clientApps) {
			if (appTemp.getMusted() == 1) {
				resp.setMusted(1);
			}
		}
		return resp;
	}

	@DocFlag("资源包列表接口")
	@CmdAction
	@LoginIgnore
	public LoadResourcesRespVO loadResources(BaseReqVO req) {
		LoadResourcesRespVO resp = new LoadResourcesRespVO();
		resp.setResources(appResourceService.getAppResources());
		return resp;
	}
	
	@DocFlag("获取最新版本接口")
	@CmdAction
	@LoginIgnore
	public LastVersionRespVO lastVersion(BaseReqVO req) {
		
		LastVersionRespVO resp=new LastVersionRespVO();
		ClientApp androidApp=clientAppService.getLastVersion(0, "com.jj.wowovoice");
		if(!StringUtil.isNullOrEmpty(androidApp)){
			ClientAppSchema android=new ClientAppSchema();
			BeanUtils.copyProperties(androidApp, android);
			resp.setAndroid(android);
		}
		ClientApp iosApp=clientAppService.getLastVersion(1, "com.et.vioce");
		if(!StringUtil.isNullOrEmpty(iosApp)){
			ClientAppSchema ios=new ClientAppSchema();
			BeanUtils.copyProperties(iosApp, ios);
			resp.setIos(ios);
		}
		return resp;
	}
	
	@DocFlag("获取过滤词汇列表")
	@CmdAction
	public LoadKeyWordsRespVO loadKeyWords(BaseReqVO req){
		
		LoadKeyWordsRespVO resp=new LoadKeyWordsRespVO();
		resp.setKeywords(FiltingProcess.filtings);
		return resp;
		
	}
}

