package com.jiujun.voice.modules.apps.user.invite.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.exception.CmdException;
import com.jiujun.voice.common.jdbc.annotation.Transacted;
import com.jiujun.voice.common.jdbc.entity.Pager;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.user.invite.cmd.vo.schema.InviteRecordSchema;
import com.jiujun.voice.modules.apps.user.invite.dao.InviteDao;
import com.jiujun.voice.modules.apps.user.invite.domain.InvitePrizedRecord;
import com.jiujun.voice.modules.apps.user.invite.domain.InviteRecord;
import com.jiujun.voice.modules.apps.user.invite.handle.InviteHandle;
import com.jiujun.voice.modules.apps.user.invite.service.InviteService;
import com.jiujun.voice.modules.apps.user.useraccount.domain.UserAccount;
import com.jiujun.voice.modules.apps.user.useraccount.service.BackPackService;
import com.jiujun.voice.modules.apps.user.useraccount.service.TradeService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;

@Service
public class InviteServiceImpl implements InviteService {

	@Resource
	InviteDao inviteDao;
	@Resource
	UserInfoService userInfoService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	InviteHandle inviteHandle;
	@Resource
	TradeService tradeService;
	@Resource
	BackPackService backPackService;

	@Override
	public InvitePrizedRecord getUserInvitePrize(String userId) {
		return inviteDao.getUserInvitePrize(userId);
	}

	@Override
	public Pager getInvitePager(String userId, Pager pager) {
		pager = inviteDao.getInvitePager(userId, pager);
		if (StringUtil.isNullOrEmpty(pager.getData())) {
			return pager;
		}
		List<InviteRecordSchema> schemas = PropertUtil.getNewList(pager.getData(), InviteRecordSchema.class);
		for (InviteRecordSchema schema : schemas) {
			UserInfo user = userInfoService.getUserInfo(schema.getRegistranter());
			if (user == null) {
				continue;
			}
			schema.setRegistranterName(user.getName());
		}
		pager.setData(schemas);
		return pager;
	}

	@Transacted
	@Override
	public Long inputInvite(String userId, String inviterCode) {
		String inviterUserId = inviteHandle.getUserIdByInvite(inviterCode);
		UserAccount inviteUserAccount=userAccountService.getUserAccountByUserId(inviterUserId);
		if(inviteUserAccount==null){
			throw new CmdException(ErrorCode.ERROR_1051);
		}
		// 查询是否是新用户
		UserAccount userAccount = userAccountService.getUserAccountByUserId(userId);
		if (System.currentTimeMillis() - userAccount.getCreateTime().getTime() > 1000 * 60 * 60 * 24 * 7) {
			return inviteDao.inputInvite(userId, inviterUserId, 0, "邀请无效，该用户注册时间已超七天");
		}
		// 查询设备是否已被注册
		List<UserAccount> accounts = userAccountService.getUserAccounts(userAccount.getClientId());
		if (accounts.size() > 1) {
			if (!accounts.get(0).getUserId().equals(userId)) {
				return inviteDao.inputInvite(userId, inviterUserId, 0, "邀请无效，该用户非新用户");
			}
		}
		Long code = inviteDao.inputInvite(userId, inviterUserId, 1, "邀请成功");
		if (code < 1) {
			throw new CmdException(ErrorCode.ERROR_1053);
		}
		if (code > 0) {
			
			/**
			 * 赠送被邀请者 礼物
			 */
			////礼物ID为6
			/*final String correId="6";
			code =backPackService.addBackPack(userId, correId, 1, 50, "填写邀请人赠送背包礼物");
			Integer inviteNum = inviteDao.getInviteNum(inviterUserId, DateUtils.getDayCode(), 1);
			*//**
			 * 从第6个开始不予赠送  需求：每天最多获得5个邀请奖励
			 *//*
			if (inviteNum > 5) {
				return code;
			}
			*//**
			 * 赠送邀请者礼物
			 *//*
			backPackService.addBackPack(inviterUserId, correId, 1, 50, "邀请好友赠送背包礼物");*/
			
			// 赠送被邀请者元宝
			tradeService.changeIngot(userId, 50, "被邀请好友赠送元宝");
			Integer inviteNum = inviteDao.getInviteNum(inviterUserId, DateUtils.getDayCode(), 1);
			if (inviteNum >= 5) {
				return code;
			}
			// 赠送邀请者元宝
			tradeService.changeIngot(inviterUserId, 50, "邀请好友赠送元宝");
		}
		return code;
	}

	@Override
	public InviteRecord getInviteRecordByUserId(String registranter) {
		return inviteDao.getInviteRecordByUserId(registranter);
	}

	@Override
	public Long pushUserInvitePrize(String userId, Integer ingot, Integer gold) {
		return inviteDao.pushUserInvitePrize(userId, ingot, gold);
	}

}
