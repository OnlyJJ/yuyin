package com.jiujun.voice.modules.apps.user.invite.cmd;

import java.util.List;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.cmd.vo.PagerReqVO;
import com.jiujun.voice.common.cmd.vo.PagerRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.modules.apps.user.invite.cmd.vo.InputInviteCodeReqVO;
import com.jiujun.voice.modules.apps.user.invite.cmd.vo.InviteInfoRespVO;
import com.jiujun.voice.modules.apps.user.invite.cmd.vo.schema.InviteRecordSchema;
import com.jiujun.voice.modules.apps.user.invite.domain.InvitePrizedRecord;
import com.jiujun.voice.modules.apps.user.invite.handle.InviteHandle;
import com.jiujun.voice.modules.apps.user.invite.service.InviteService;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;

@CmdOpen("invite")
@DocFlag("邀请中心")
public class InviteCmd extends RootCmd {

	@Resource
	InviteHandle inviteHandle;
	@Resource
	InviteService inviteService;
	@Resource
	UserAccountService userAccountService;

	
	@CmdAction
	@DocFlag("邀请页详情")
	public InviteInfoRespVO inviteInfo(BaseReqVO req){
		InviteInfoRespVO resp=new InviteInfoRespVO();
		resp.setInviteCode(inviteHandle.getInvite(req.getHeader().getUserId()));
		InvitePrizedRecord prizeRecord=inviteService.getUserInvitePrize(req.getHeader().getUserId());
		/**
		 * 检查本账号是否具备被邀请的权益
		 */
		resp.setIsNewer(1);
		UserAccount userAccount = userAccountService.getUserAccountByUserId(req.getHeader().getUserId());
		if (System.currentTimeMillis() - userAccount.getCreateTime().getTime() > 1000 * 60 * 60 * 24 * 7) {
			resp.setIsNewer(0);
		}
		if(resp.getIsNewer()==1){
			List<UserAccount> accounts = userAccountService.getUserAccounts(userAccount.getClientId());
			if (accounts.size() > 1) {
				if (!accounts.get(0).getUserId().equals(req.getHeader().getUserId())) {
					resp.setIsNewer(0);
				}
			}
		}
		if(prizeRecord==null){
			return resp;
		}
		resp.setIngot(prizeRecord.getIngot());
		resp.setGold(prizeRecord.getGold());
		resp.setInviteUrl("https://www.baidu.com/");
		return resp;
	}
	
	@CmdAction
	@DocFlag("邀请列表")
	public PagerRespVO<InviteRecordSchema> inviteRecord(PagerReqVO req){
		Pager pager=inviteService.getInvitePager(req.getHeader().getUserId(), req.toPager());
		PagerRespVO<InviteRecordSchema> resp=new PagerRespVO<InviteRecordSchema>().fromPager(pager);
		return resp;
	}
	
	
	@CmdAction
	@DocFlag("录入邀请码")
	public BaseRespVO inputInviteCode(InputInviteCodeReqVO req){
		
		String userId=inviteHandle.getUserIdByInvite(req.getInviteCode());
		if(userId.equals(req.getHeader().getUserId())){
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1052);
		}
		Long code=inviteService.inputInvite(req.getHeader().getUserId(), req.getInviteCode());
		if(code<1){
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1051);
		}
		return new BaseRespVO().pushErrorCode(ErrorCode.SUCCESS_0.getCode(),"被邀请成功");
	}
}
