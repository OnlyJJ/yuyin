package com.jiujun.voice.modules.apps.user.useraccount.cmd;

import javax.annotation.Resource;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseReqVO;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.common.utils.EncryptUtil;
import com.jiujun.voice.common.utils.JUUIDUtil;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.room.service.RoomMemberBehaviorService;
import com.jiujun.voice.modules.apps.user.invite.service.InviteService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.HeartBeatReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.LoginReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.QQLoginReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.RegisterReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.ResetPwdReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.ThirdTokenRespVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.TokenRespVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.WXLoginReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.WebQQLoginReqVO;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.handle.ThirdLoginHandle;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle.TokenEntity;
import com.jiujun.voice.modules.apps.user.useraccount.schema.UserAccountSchema;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.verificat.service.VerificatService;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@CmdOpen("userAuth")
@DocFlag("用户授权中心")
public class UserAuthCmd extends RootCmd {
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserAuthHandle userAuthHandle;
	@Resource
	VerificatService verificatService;
	@Resource
	ThirdLoginHandle thridLoginHandle;
	@Resource
	RoomMemberBehaviorService roomMemberBehaviorService;
	@Resource
	InviteService inviteService;

	/**
	 * 登录接口
	 * 
	 * @param req
	 * @return
	 */
	@CmdAction
	@LoginIgnore
	@DocFlag("用户登录接口")
	public TokenRespVO login(LoginReqVO req) {

		UserAccount account = userAccountService.getUserAccount(req.getAccount());
		if (account == null) {
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1009);
		}
		if (!EncryptUtil.encryptUserPassword(account.getUserId(), req.getPwd()).equals(account.getPassword())) {
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1010);
		}
		if (account.getStatus()!=null&&account.getStatus() == -1) {
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1026);
		}
		TokenEntity token = userAuthHandle.createToken(account.getUserId().toString(),req.getHeader());
		TokenRespVO resp = new TokenRespVO();
		resp.setUserId(account.getUserId());
		resp.setToken(token.getToken());
		return resp;
	}
	@CmdAction
	@LoginIgnore
	@DocFlag("游客登录接口")
	public TokenRespVO tourerLogin(BaseReqVO req) {
		UserAccount account = userAccountService.getUserAccountByClientId(req.getHeader().getClientId(),5);
		if (account == null) {
			//游客注册
			account = userAccountService.initUserAccount("游客",null, null, null, req.getHeader().getClientId(),
					JUUIDUtil.createUuid(), 5);
		}
		if (account.getStatus()!=null&&account.getStatus() == -1) {
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1026);
		}
		TokenEntity token = userAuthHandle.createToken(account.getUserId().toString(),req.getHeader());
		TokenRespVO resp = new TokenRespVO();
		resp.setUserId(account.getUserId());
		resp.setToken(token.getToken());
		return resp;
	}
	/**
	 * 注册接口
	 * 
	 * @param req
	 * @return
	 */
	@CmdAction
	@LoginIgnore
	@DocFlag("用户注册接口")
	public TokenRespVO register(RegisterReqVO req) {

		UserAccount account = userAccountService.getUserAccount(req.getAccount());
		if (account != null) {
			// 用户已存在
			if (StringUtil.isMobile(req.getAccount())) {
				return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1015);
			}
			if (StringUtil.isEmail(req.getAccount())) {
				return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1016);
			}
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1004);
		}
		// 验证码校验
		if (!verificatService.doVerification(req.getAccount(), StringUtil.toInteger(req.getCode()))) {
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1017);
		}
		// 手机注册
		if (StringUtil.isMobile(req.getAccount())) {
			account = userAccountService.initUserAccount(null,req.getAccount(), null, null, req.getHeader().getClientId(),
					req.getPassword(), 0);
			TokenEntity token = userAuthHandle.createToken(account.getUserId().toString(),req.getHeader());
			//处理用户邀请逻辑
			if(!StringUtil.isNullOrEmpty(req.getInviteCode())){
				inviteService.inputInvite(account.getUserId(), req.getInviteCode());
			}
			TokenRespVO resp = new TokenRespVO();
			resp.setToken(token.getToken());
			resp.setUserId(account.getUserId());
			return resp;

		}
		// 邮箱注册
		if (StringUtil.isEmail(req.getAccount())) {
			account = userAccountService.initUserAccount(null,null, req.getAccount(), null, req.getHeader().getClientId(),
					req.getPassword(), 1);
			TokenEntity token = userAuthHandle.createToken(account.getUserId().toString(),req.getHeader());
			//处理用户邀请逻辑
			if(!StringUtil.isNullOrEmpty(req.getInviteCode())){
				inviteService.inputInvite(account.getUserId(), req.getInviteCode());
			}
			TokenRespVO resp = new TokenRespVO();
			resp.setUserId(account.getUserId());
			resp.setToken(token.getToken());
			return resp;
		}
		return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1004);
	}

	@CmdAction
	@DocFlag("重置密码接口")
	@LoginIgnore
	public TokenRespVO resetPwd(ResetPwdReqVO req) {
		if(!verificatService.doVerification(req.getAccount(), Integer.valueOf(req.getCode()))){
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1017);
		}
		UserAccount userAccount = userAccountService.getUserAccount(req.getAccount());
		if (userAccount == null) {
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1009);
		}
		userAccount.setPassword(EncryptUtil.encryptUserPassword(userAccount.getUserId(), req.getNewPwd()));
		Long code = userAccountService.updateUserAccount(userAccount, "password");
		if (code < 0) {
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1004);
		}
		TokenEntity token = userAuthHandle.createToken(userAccount.getUserId().toString(),req.getHeader());
		TokenRespVO resp = new TokenRespVO();
		resp.setUserId(userAccount.getUserId());
		resp.setToken(token.getToken());
		return resp;
	}
	
	@CmdAction("QQLogin")
	@DocFlag("QQ登录")
	@LoginIgnore
	public ThirdTokenRespVO qqLogin(QQLoginReqVO req){
		UserAccountSchema account=thridLoginHandle.getUserGeneralForQQByToken(req.getAccessToken(),req.getHeader());
		if(StringUtil.isNullOrEmpty(account)){
			return  new ThirdTokenRespVO().pushErrorCode(ErrorCode.ERROR_1033);
		}
		if (account.getStatus()!=null&&account.getStatus() == -1) {
			return new ThirdTokenRespVO().pushErrorCode(ErrorCode.ERROR_1026);
		}
		TokenEntity token = userAuthHandle.createToken(account.getUserId().toString(),req.getHeader());
		ThirdTokenRespVO resp = new ThirdTokenRespVO();
		resp.setUserId(account.getUserId());
		resp.setToken(token.getToken());
		String authToken = EncryptUtil.md5(account.getUnionId() + account.getAccessToken());
		resp.setAuthToken(authToken);
		resp.setIsFirstLogin(account.getIsFirstLogin());
		return resp;
	}
	
	@CmdAction("WebQQLogin")
	@DocFlag("WEBQQ登录")
	@LoginIgnore
	public ThirdTokenRespVO webQQLogin(WebQQLoginReqVO req){
		UserAccountSchema account=thridLoginHandle.getUserGeneralForQQByCode(req.getCode(),req.getHeader());
		if(StringUtil.isNullOrEmpty(account)){
			return  new ThirdTokenRespVO	().pushErrorCode(ErrorCode.ERROR_1033);
		}
		if (account.getStatus()!=null&&account.getStatus() == -1) {
			return new ThirdTokenRespVO().pushErrorCode(ErrorCode.ERROR_1026);
		}
		TokenEntity token = userAuthHandle.createToken(account.getUserId().toString(),req.getHeader());
		ThirdTokenRespVO resp = new ThirdTokenRespVO();
		resp.setUserId(account.getUserId());
		resp.setToken(token.getToken());
		String authToken = EncryptUtil.md5(account.getUnionId() + account.getAccessToken());
		resp.setAuthToken(authToken);
		resp.setIsFirstLogin(account.getIsFirstLogin());
		return resp;
	}
	@CmdAction("WXLogin")
	@DocFlag("微信登录")
	@LoginIgnore
	public ThirdTokenRespVO wxLogin(WXLoginReqVO req){
		UserAccount account=thridLoginHandle.getUserGeneralForWX(req.getCode(),req.getHeader());
		if(StringUtil.isNullOrEmpty(account)){
			return  new ThirdTokenRespVO	().pushErrorCode(ErrorCode.ERROR_1033);
		}
		if (account.getStatus()!=null&&account.getStatus() == -1) {
			return new TokenRespVO().pushErrorCode(ErrorCode.ERROR_1026);
		}
		TokenEntity token = userAuthHandle.createToken(account.getUserId().toString(),req.getHeader());
		ThirdTokenRespVO resp = new ThirdTokenRespVO();
		resp.setUserId(account.getUserId());
		resp.setToken(token.getToken());
		String authToken = EncryptUtil.md5(account.getUnionId() + account.getAccessToken());
		resp.setAuthToken(authToken);
		return resp;
	}
	
	@CmdAction
	@DocFlag("心跳接口")
	public BaseRespVO heartBeat(HeartBeatReqVO req){
		return new BaseRespVO();
	}
	
	@CmdAction
	@DocFlag("注销登录接口")
	public BaseRespVO loginOut(BaseReqVO req){
		userAuthHandle.removeToken(req.getHeader().getUserId());
		roomMemberBehaviorService.cleanUser(req.getHeader().getUserId());
		return new BaseRespVO();
	}
}
