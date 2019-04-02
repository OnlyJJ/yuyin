package com.jiujun.voice.modules.apps.user.userinfo.cmd;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.jiujun.voice.common.cmd.RootCmd;
import com.jiujun.voice.common.cmd.anntation.CmdAction;
import com.jiujun.voice.common.cmd.anntation.CmdOpen;
import com.jiujun.voice.common.cmd.vo.BaseRespVO;
import com.jiujun.voice.common.doc.annotation.DocFlag;
import com.jiujun.voice.common.enums.ErrorCode;
import com.jiujun.voice.common.utils.DateUtils;
import com.jiujun.voice.common.utils.StringUtil;
import com.jiujun.voice.common.utils.property.PropertUtil;
import com.jiujun.voice.modules.apps.room.domain.RoomInfo;
import com.jiujun.voice.modules.apps.room.service.RoomInfoService;
import com.jiujun.voice.modules.apps.user.invite.service.InviteService;
import com.jiujun.voice.modules.apps.user.relation.domain.RelationRecord;
import com.jiujun.voice.modules.apps.user.relation.service.RelationService;
import com.jiujun.voice.modules.apps.user.useraccount.cmd.vo.schema.UserSchema;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle;
import com.jiujun.voice.modules.apps.user.useraccount.handle.UserAuthHandle.TokenEntity;
import com.jiujun.voice.modules.apps.user.useraccount.service.LevelInfoService;
import com.jiujun.voice.modules.apps.user.useraccount.service.UserAccountService;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.BatchLoadUserInfoReqVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.BatchLoadUserInfoRespVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.LoadUserInfoReqVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.LoadUserInfoRespVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.ModifyUserInfoReqVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.SearchUserReqVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.SearchUserRespVO;
import com.jiujun.voice.modules.apps.user.userinfo.cmd.vo.schema.UserGeneralSchema;
import com.jiujun.voice.modules.apps.user.userinfo.domain.UserInfo;
import com.jiujun.voice.modules.apps.user.userinfo.service.UserInfoService;

/**
 * 
 * @author Coody
 * @date 2018年11月13日
 */
@CmdOpen("user")
@DocFlag("用户资料中心")
public class UserInfoCmd extends RootCmd {

	@Resource
	UserInfoService userInfoService;
	@Resource
	UserAccountService userAccountService;
	@Resource
	UserAuthHandle userAuthHandle;
	@Resource
	RoomInfoService roomInfoService;
	@Resource
	LevelInfoService levelInfoService;
	@Resource
	RelationService relationService;
	@Resource
	InviteService inviteService;

	@CmdAction
	@DocFlag("修改用户资料接口")
	public BaseRespVO modifyUserInfo(ModifyUserInfoReqVO req) {
		Date birthDate = DateUtils.toDate(req.getBirth());
		if (birthDate != null) {
			if(birthDate.getTime()>System.currentTimeMillis()){
				return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1025);
			}
		}
		UserInfo userInfo = userInfoService.getUserInfo(req.getHeader().getUserId());
		if (userInfo == null) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1022);
		}
		UserInfo upUser=new UserInfo();
		BeanUtils.copyProperties(req, upUser);
		upUser.setUserId(req.getHeader().getUserId());
		List<UserInfo> nickNameFuzzers=userInfoService.getUserInfoByNickName(upUser.getName());
		if(!StringUtil.isNullOrEmpty(nickNameFuzzers)){
			if(nickNameFuzzers.size()>1){
				return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1035);
			}
			if(!nickNameFuzzers.get(0).getUserId().equals(upUser.getUserId())){
				return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1035);
			}
		}
		Long code = userInfoService.saveUserInfo(upUser);
		if (code == -1) {
			return new BaseRespVO().pushErrorCode(ErrorCode.ERROR_1004);
		}
		//处理用户邀请逻辑
		if(!StringUtil.isNullOrEmpty(req.getInviteCode())){
			inviteService.inputInvite(req.getHeader().getUserId(), req.getInviteCode());
		}
		return new BaseRespVO();
	}

	@CmdAction
	@DocFlag("加载用户资料接口")
	public LoadUserInfoRespVO loadUserInfo(LoadUserInfoReqVO req) {
		String userId = req.getTargeUserId();
		UserSchema schema = userInfoService.getUserSchema(userId);
		LoadUserInfoRespVO resp = new LoadUserInfoRespVO();
		BeanUtils.copyProperties(schema, resp);
		resp.setRelation(-1);
		if (!schema.getUserId().equals(req.getHeader().getUserId())) {
			// 查看他人资料，屏蔽金币余额与钻石余额
			resp.setJewel(null);
			resp.setGold(null);
			resp.setIngot(null);
			//好友关系
			resp.setRelation(0);
			RelationRecord relation=relationService.getRelation(req.getHeader().getUserId(), req.getTargeUserId());
			if(relation!=null){
				resp.setRelation(1);
			}
		}
		RoomInfo room = roomInfoService.getRoomInfoByUserId(userId);
		if(null != room ) {
			resp.setRoomId(room.getRoomId());
			resp.setLockFlag(room.getLockFlag());
		}
		//加载等级信息
		resp.setNextLevelCharm(levelInfoService.getNextCharmExp(resp.getCharmLevel().intValue()));
		resp.setNextLevelExp(levelInfoService.getNextRichExp(resp.getExpLevel().intValue()));
		return resp;
	}

	@CmdAction
	@DocFlag("批量加载用户资料")
	public BatchLoadUserInfoRespVO batchLoadUserInfo(BatchLoadUserInfoReqVO req) {
		List<UserInfo> users = userInfoService.getUserInfos(req.getUserIds().toArray(new String[] {}));
		if (StringUtil.isNullOrEmpty(users)) {
			return new BatchLoadUserInfoRespVO().pushErrorCode(ErrorCode.ERROR_1022);
		}
		BatchLoadUserInfoRespVO resp = new BatchLoadUserInfoRespVO();

		List<UserGeneralSchema> schemas = PropertUtil.getNewList(users, UserGeneralSchema.class);
		resp.setUserInfos(schemas);
		List<String> userIds = PropertUtil.getFieldValues(schemas, "userId");
		Map<String, TokenEntity> userTokens = userAuthHandle.getUserTokens(userIds.toArray(new String[] {}));
		if (StringUtil.isNullOrEmpty(userTokens)) {
			return resp;
		}
		for(UserGeneralSchema schema:schemas){
			TokenEntity token=userTokens.get(schema.getUserId());
			if(StringUtil.isNullOrEmpty(token)){
				continue;
			}
			schema.setActiveTime(token.getAcviteTime());
		}
		return resp;
	}
	
	@CmdAction
	@DocFlag("用户搜索接口")
	public SearchUserRespVO searchUser(SearchUserReqVO req){
		List<UserSchema> users=userInfoService.searchByKeyWord(req.getKeyWord());
		if(StringUtil.isNullOrEmpty(users)){
			return new SearchUserRespVO();
		}
		List<UserGeneralSchema> schemas = PropertUtil.getNewList(users, UserGeneralSchema.class);
		SearchUserRespVO resp=new SearchUserRespVO();
		resp.setUserInfos(schemas);
		List<String> userIds = PropertUtil.getFieldValues(schemas, "userId");
		Map<String, TokenEntity> userTokens = userAuthHandle.getUserTokens(userIds.toArray(new String[] {}));
		if (StringUtil.isNullOrEmpty(userTokens)) {
			return resp;
		}
		for(UserGeneralSchema schema:schemas){
			TokenEntity token=userTokens.get(schema.getUserId());
			if(StringUtil.isNullOrEmpty(token)){
				continue;
			}
			schema.setActiveTime(token.getAcviteTime());
		}
		return resp;
	}
}
