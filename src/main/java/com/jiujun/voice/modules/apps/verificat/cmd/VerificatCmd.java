package com.jiujun.voice.modules.apps.verificat.cmd;

import javax.annotation.Resource;

import com.jiujun.voice.common.cache.instance.RedisCache;
import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.constants.CacheConstants;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.logger.util.LogUtil;
import com.jiujun.voice.common.login.annotation.LoginIgnore;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.modules.apps.verificat.cmd.vo.GetAuthTokenReqVO;
import com.jiujun.voice.modules.apps.verificat.cmd.vo.GetAuthTokenRespVO;
import com.jiujun.voice.modules.apps.verificat.cmd.vo.SendCodeReqVO;
import com.jiujun.voice.modules.apps.verificat.handle.MsgHandle;
import com.jiujun.voice.modules.apps.verificat.service.VerificatService;

/**
 * 
 * @author Coody
 *
 */
@CmdOpen("verificat")
@DocFlag("验证码")
public class VerificatCmd extends RootCmd {

	@Resource
	RedisCache redisCache;
	@Resource
	MsgHandle msgHandle;
	@Resource
	VerificatService verificatService;

	@CmdAction
	@LoginIgnore
	@DocFlag("发送验证码接口")
	public BaseRespVO sendCode(SendCodeReqVO req) {

		// 校验用户发送次数
		Integer sendNumForAccount = redisCache.getCache(CacheConstants.USER_VERCODE_CHECK + ":" + req.getAccount());
		if (sendNumForAccount != null && sendNumForAccount > 5) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1013);
		}
		// 校验IP地址发送次数
		Integer sendNumForIpAddress = redisCache
				.getCache(CacheConstants.USER_VERCODE_CHECK + ":" + req.getHeader().getIpAddress());
		if (sendNumForIpAddress != null && sendNumForIpAddress > 10) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1013);
		}
		// 发送验证码
		Integer code = verificatService.createVerificat(req.getAccount());
		LogUtil.logger.debug("生成验证码>>" + req.getAccount() + ":" + code);
		// 发送手机验证码
		if (StringUtil.isMobile(req.getAccount())) {
			String context = "【WOWO语音】验证码：" + code.toString() + "，感谢您注册帐号，请在30分钟内完成注册。工作人员不会向您索取验证码，请勿泄露。";
			if (!msgHandle.sendMobileCode(req.getAccount(), context)) {
				return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1014);
			}
		}
		// 发送邮箱验证码
		if (StringUtil.isEmail(req.getAccount())) {
			String content = "【WOWO语音】验证码：" + code.toString() + "，感谢您注册帐号，请在10分钟内完成注册。工作人员不会向您索取验证码，请勿泄露。消息来自：ET语音";
			if (!msgHandle.sendEmailCode(req.getAccount(), "【ET语音】", content)) {
				return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1014);
			}
		}
		redisCache.setCache(CacheConstants.USER_VERCODE_CHECK + ":" + req.getAccount(),
				(sendNumForAccount == null ? 1 : sendNumForAccount + 1));
		redisCache.setCache(CacheConstants.USER_VERCODE_CHECK + ":" + req.getHeader().getIpAddress(),
				(sendNumForIpAddress == null ? 1 : sendNumForIpAddress + 1));
		return new BaseRespVO();
	}

	@CmdAction
	@LoginIgnore
	@DocFlag("验证码校验接口")
	public GetAuthTokenRespVO getAuthToken(GetAuthTokenReqVO req) {
		if (!verificatService.doVerification(req.getAccount(), StringUtil.toInteger(req.getCode()))) {
			return new GetAuthTokenRespVO().pushErrorCode(ErrorCode.ERROR_1017);
		}
		String authToken = verificatService.createAuthToken(req.getAccount());
		GetAuthTokenRespVO resp = new GetAuthTokenRespVO();
		resp.setAuthToken(authToken);
		return resp;
	}
}
